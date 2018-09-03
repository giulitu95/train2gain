package com.train2gain.train2gain.source.remote.callback;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.train2gain.train2gain.source.remote.APIClient;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIError;
import com.train2gain.train2gain.source.remote.response.APIResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * A generic API callback handler
 * @param <T> it should be an Entity class type or a List
 */
public abstract class APICallback<T> implements Callback<APIData<T>> {

    // Logcat TAG key for debugging purpose
    private final static String LOGCAT_TAG = "REMOTE_API_CALLBACK";

    /**
     * Called when the API request finishes without critical errors (conversion or network errors).
     * The method handles the conversion of the response to an APIResponse object
     * @param call the original API request call object (see Call<> in API endpoints)
     * @param response the response data (body and header) returned by the API server
     */
    @Override
    public void onResponse(Call<APIData<T>> call, Response<APIData<T>> response) {
        if(response != null){
            if(response.isSuccessful()){
                handleAPIResponse(APIResponse.success(response.body()));
            }else{
                onError(response);
            }
        }else{
            onFailure(call, new NullPointerException("API response : empty / null response."));
        }
    }

    /**
     * Called when a critical error has occurred (network, conversion error, ..)
     * If the call was not cancelled, the method encapsulates the throwable error in an APIResponse
     * object and returns it, otherwise, it returns a NULL APIResponse object
     * @param call the original API request call object (see Call<> in API endpoints)
     * @param t the exception (throwable) representing the error that has occurred
     */
    @Override
    public void onFailure(Call<APIData<T>> call, Throwable t) {
        APIResponse apiResponse = null;
        if(call.isCanceled()){
            Log.d(LOGCAT_TAG, "The API request was cancelled.");
            apiResponse = null;
        }else{
            if(t instanceof IOException){
                Log.e(LOGCAT_TAG, "Network error / failure. " + t.getMessage());
            }else if(t instanceof JsonParseException || t instanceof IllegalArgumentException) {
                Log.e(LOGCAT_TAG, "Json parsing / conversion error. " + t.getMessage());
            }else if(t instanceof NullPointerException){
                Log.e(LOGCAT_TAG, "Something wrong with response. " + t.getMessage());
            }else{
                Log.e(LOGCAT_TAG, "Some exception occurred. " + t.getMessage());
            }
            apiResponse = APIResponse.failure(t);
        }
        handleAPIResponse(apiResponse);
    }

    /**
     * Called when some error has returned from the API server (no critical errors)
     * The method convert the response's error body into an APIError if no other errors occurred,
     * otherwise it creates a throwable and encapsulated it in an APIResponse.failure
     * @param response a not null generic API response (should have not null error body)
     */
    private void onError(@NonNull Response response){
        // Get a response converter that will be used to convert the generic response into an APIError
        Converter<ResponseBody, APIError> responseConverter = null;
        responseConverter = APIClient.getInstance().responseBodyConverter(APIError.class, APIError.class.getAnnotations());

        APIResponse apiResponse = null;
        try {
            APIError apiError = responseConverter.convert(response.errorBody());
            Log.w(LOGCAT_TAG, "API server returned an error message: " + apiError.getMessage());
            apiResponse = APIResponse.error(apiError);
        }catch (IOException ex) {
            apiResponse = APIResponse.failure(ex);
            Log.e(LOGCAT_TAG, "Error during errorBody conversion " + ex.getMessage());
        }
        handleAPIResponse(apiResponse);
    }

    /**
     * Defines what to do with / how to manage the APIResponse object
     * @param data the APIResponse object containing an API response or an API failure (it can also be NULL)
     */
    abstract public void handleAPIResponse(APIResponse<T> data);

}
