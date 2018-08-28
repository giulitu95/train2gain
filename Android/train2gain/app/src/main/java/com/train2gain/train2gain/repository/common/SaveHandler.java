package com.train2gain.train2gain.repository.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;

/**
 * Defines how to handle data uploading / saving to API / Database
 * @param <SaveType> the data type (Entity or List) provided by the caller
 * @param <ResponseType> the APIResponse data type returned from the API upload request
 */
public abstract class SaveHandler<SaveType, ResponseType> {

    // Logcat TAG key for debugging purpose
    private final static String LOGCAT_TAG = "SAVE_HANDLER";

    protected SaveHandler(SaveType object){
        saveDataToDatabase(object);
        if(shouldUploadToAPI()){
            uploadDataToNetwork(object);
        }
    }

    /**
     * Uploads the given object to the API server
     * It also saves the API response into the database if shouldSaveAPIResponse() returned true
     * and if no errors occurred during API data uploading
     * @param objectToUpload the data object we want to upload to the API server
     */
    private void uploadDataToNetwork(SaveType objectToUpload){
        final LiveData<APIResponse<ResponseType>> responseLiveData = uploadToAPI(objectToUpload);
        Observer<APIResponse<ResponseType>> observer = new Observer<APIResponse<ResponseType>>() {
            @Override
            public void onChanged(@Nullable APIResponse<ResponseType> responseData) {
                final Observer<APIResponse<ResponseType>> observer = this;
                new Handler(Looper.getMainLooper()).post(() -> {
                    responseLiveData.removeObserver(observer);
                    if(responseData != null && responseData.getStatus() == APIResponse.Status.SUCCESS){
                        if(shouldSaveAPIResponse()){
                            saveDataToDatabase(responseData);
                        }
                    }else{
                        onAPIUploadFailed();
                    }
                });
            }
        };
        responseLiveData.observeForever(observer);
    }

    /**
     * Saves the given object into the database
     * @param object the data object we want to save into the database
     */
    private void saveDataToDatabase(SaveType object){
        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(Void... voids) {
                try{
                    return saveDataObjectToDatabase(object);
                }catch(SQLiteConstraintException ex){
                    Log.e(LOGCAT_TAG, Log.getStackTraceString(ex));
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean insertedOrUpdated) {
                if(!insertedOrUpdated){
                    onDatabaseSaveFailed();
                }
            }
        }.execute();
    }

    /**
     * Saves the response data returned by the API service into the database.
     * @param response he response data object returned by the API service
     */
    private void saveDataToDatabase(@NonNull APIResponse<ResponseType> response){
        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(Void... voids) {
                if(response.getData().getContent() != null){
                    try{
                        return saveResponseDataToDatabase(response.getData());
                    }catch(SQLiteConstraintException ex){
                        Log.e(LOGCAT_TAG, Log.getStackTraceString(ex));
                        return false;
                    }
                }else{
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean insertedOrUpdated) {
                if(!insertedOrUpdated){
                    onDatabaseSaveFailed();
                }
            }
        }.execute();
    }

    /**
     * Called to decide if it is necessary to save the data to the API or not
     * @return true if data should be uploaded to API / network
     *         false otherwise
     */
    protected abstract boolean shouldUploadToAPI();

    /**
     * Called to decide if it is necessary to save the API response into the database or not
     * @return true if response data from the API should be saved into the database
     *         false otherwise
     */
    protected abstract boolean shouldSaveAPIResponse();

    /**
     * Called to save the given object into the database
     * It should define specific insert instruction (see helpers)
     * @param objectToSave the object we want to insert into the database
     * @return true if no errors occurred during insertion operations
     *         false otherwise
     */
    protected abstract boolean saveDataObjectToDatabase(@NonNull SaveType objectToSave);

    /**
     * Called to save the result of API response into the database
     * @param responseData data retrieved from network which is stored in the API response
     * @return true if no errors occurred during insertion operations
     *         false otherwise
     */
    protected abstract boolean saveResponseDataToDatabase(@NonNull APIData<ResponseType> responseData);

    /**
     * Called to upload to the API server (network) the given object.
     * It should define specific upload directives (see APIClient methods)
     * @param objectToUpload the object we want to upload to the API server (network)
     * @return the response data returned by the API upload request
     */
    @NonNull protected abstract LiveData<APIResponse<ResponseType>> uploadToAPI(@NonNull SaveType objectToUpload);

    /*
     * Called when the upload of data to the network (API) fails (errors or exceptions)
     * NOTE: this method can be use to reset some components like "loading" bars, ecc..
     */
    protected abstract void onAPIUploadFailed();

    /*
     * Called when some errors occurs during database insertion / saving operations
     * NOTE: this method can be use to reset some components like "loading" bars, ecc..
     */
    protected abstract void onDatabaseSaveFailed();

}
