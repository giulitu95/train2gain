package com.train2gain.train2gain.repository.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;

/**
 * Defines how to handle data retrieving from API / Database
 * @param <ResultType> the Resource data type (Entity or List) returned to the caller
 * @param <ResponseType> the APIResponse data type returned from the API request
 */
public abstract class RetrieveHandler<ResultType, ResponseType> {

    // Logcat TAG key for debugging purpose
    private final static String LOGCAT_TAG = "RETRIEVE_HANDLER";

    // Result data from API / database
    private final MediatorLiveData<Resource<ResultType>> result;

    protected RetrieveHandler() {
        this.result = new MediatorLiveData<Resource<ResultType>>();
        this.result.postValue(Resource.loading(null));
        LiveData<ResultType> databaseSource = loadFromDatabase();
        result.addSource(databaseSource, data -> {
            new Handler(Looper.getMainLooper()).post(() -> {
                result.removeSource(databaseSource);
                if(shouldFetchFromAPI()){
                    fetchFromAPI(databaseSource);
                }else{
                    result.addSource(databaseSource, newData -> {
                        result.postValue(Resource.success(newData));
                    });
                }
            });
        });
    }

    /**
     * Fetches data from the API service and, if no error occurred, saves it into the database
     * @param databaseSource data source (database data) used while other data is being downloaded
     *                      from the API
     */
    private void fetchFromAPI(final LiveData<ResultType> databaseSource){
        LiveData<APIResponse<ResponseType>> APISource = loadFromAPI();
        result.addSource(databaseSource, data -> {
            result.postValue(Resource.loading(data));
        });
        result.addSource(APISource, response -> {
            new Handler(Looper.getMainLooper()).post(() -> {
                result.removeSource(APISource);
                result.removeSource(databaseSource);
                if(response.getStatus() == APIResponse.Status.SUCCESS){
                    saveAPIResponseAndUpdateDatabase(response);
                }else {
                    onFetchFromAPIFailed();
                    if(response.getStatus() == APIResponse.Status.ERROR){
                        result.addSource(databaseSource, data -> {
                            result.postValue(Resource.error(data, response.getError().getMessage()));
                        });
                    }else if(response.getStatus() == APIResponse.Status.FAILURE){
                        result.addSource(databaseSource, data -> {
                            result.postValue(Resource.error(data, response.getThrowable().getMessage()));
                        });
                    }
                }
            });
        });
    }

    /**
     * Saves the response data returned by the API service into the database.
     * It updates also the Resource LiveData object with the new data.
     * @param response the response data object returned by the API service
     */
    private void saveAPIResponseAndUpdateDatabase(@NonNull APIResponse<ResponseType> response) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                if(response.getData().getContent() != null){
                    try{
                        return saveAPIResponse(response.getData());
                    }catch(SQLiteConstraintException ex){
                        Log.e(LOGCAT_TAG, Log.getStackTraceString(ex));
                        return false;
                    }
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean inserted) {
                if(inserted == true){
                    result.addSource(loadFromDatabase(), newData -> {
                        result.postValue(Resource.success(newData));
                    });
                }else{
                    result.addSource(loadFromDatabase(), oldData -> {
                        result.postValue(Resource.error(oldData, "DATABASE INSERT ERROR"));
                    });
                }
            }
        }.execute();
    }

    /**
     * Called to retrieve (cached) data from the local database
     * @return (cached) data from the local database
     */
    @NonNull protected abstract LiveData<ResultType> loadFromDatabase();

    /**
     * Called to decide if it is necessary to fetch data from the API or not
     * @return true if data should be retrieved from API / network
     *         false otherwise
     */
    protected abstract boolean shouldFetchFromAPI();

    /**
     * Called to save the result of API response into the database
     * @param responseData data retrieved from network which is stored in the API response
     * @return true if no errors occurred during insertion operations
     *         false otherwise
     */
    protected abstract boolean saveAPIResponse(@NonNull APIData<ResponseType> responseData);

    /**
     * Called to retrieve / load data from an API endpoint
     * @return data returned from the API request; returned as LiveData object
     */
    @NonNull protected abstract LiveData<APIResponse<ResponseType>> loadFromAPI();

    /*
     * Called when the fetch of data from network (API) fails (errors or exceptions)
     * NOTE: this method can be use to reset some components like "loading" bars, ecc..
     */
    protected abstract void onFetchFromAPIFailed();

    /**
     * Returns a LiveData object that represents the Resource object retrieved from the API / database
     * @return a LiveData object containing the Resource retrieved from the API / database
     */
    @NonNull public final LiveData<Resource<ResultType>> getAsLiveData(){
        return result;
    }

}
