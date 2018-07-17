package com.train2gain.train2gain.source.remote.util;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.train2gain.train2gain.source.remote.callback.APICallback;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;

import retrofit2.Call;

/**
 * Class with some (misc) utility methods for API managing
 * Es. Call<APIData<?>> to LiveData<APIResponse<?>> conversion
 */
public class APIUtils {

    /**
     * Handles the conversion of an API service Call<> result into a LiveData<> object
     * @param <T> it should be an Entity class type or a List
     * @param call the Call object returned by an API endpoint method; it is used to make the request
     * @return the result of the Call<> request converted into a LiveData<> object
     */
    public static <T> LiveData<APIResponse<T>> callToLiveData(@NonNull Call<APIData<T>> call){
        final MutableLiveData<APIResponse<T>> responseLiveData = new MutableLiveData<>();
        call.enqueue(new APICallback<T>() {
            @Override
            public void handleAPIResponse(APIResponse data) {
                if(data != null) {
                    responseLiveData.postValue(data);
                }
            }
        });
        return responseLiveData;
    }

}
