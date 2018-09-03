package com.train2gain.train2gain.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.repository.common.Resource;
import com.train2gain.train2gain.repository.common.RetrieveHandler;
import com.train2gain.train2gain.repository.common.SaveHandler;
import com.train2gain.train2gain.source.local.LocalDatabase;
import com.train2gain.train2gain.source.local.helper.UserHelper;
import com.train2gain.train2gain.source.remote.APIClient;
import com.train2gain.train2gain.source.remote.endpoint.UserAPI;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;
import com.train2gain.train2gain.source.remote.util.APIUtils;
import com.train2gain.train2gain.util.NetworkUtil;

import java.util.List;

public class UserRepository {

    private final UserHelper userHelperInstance;
    private final UserAPI userAPIInstance;
    private static UserRepository instance = null;

    private UserRepository(@NonNull Context context){
        this.userHelperInstance = LocalDatabase.getInstance(context).getUserHelper();
        this.userAPIInstance = APIClient.getImplementation(UserAPI.class);
    }

    public static UserRepository getInstance(@NonNull Context context){
        if(instance == null){
            synchronized (UserRepository.class){
                if(instance == null){
                    instance = new UserRepository(context);
                }
            }
        }
        return instance;
    }

    public LiveData<Resource<User>> getUser(final long userId){
        return new RetrieveHandler<User, User>() {
            @NonNull @Override
            protected LiveData<User> loadFromDatabase() {
                MutableLiveData<User> userLiveData = new MutableLiveData<User>();
                AsyncTask.execute(() -> {
                    userLiveData.postValue(userHelperInstance.getById(userId));
                });
                return userLiveData;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                return NetworkUtil.isConnected();
            }

            @Override
            protected boolean saveAPIResponse(@NonNull APIData<User> responseData) {
                return userHelperInstance.insertOrUpdateIfExists(responseData.getContent());
            }

            @NonNull @Override
            protected LiveData<APIResponse<User>> loadFromAPI() {
                return APIUtils.callToLiveData(userAPIInstance.getUser(userId));
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        }.getAsLiveData();
    }
    public LiveData<Resource<List<User>>> getTrainerUsers(final long trainerUserId, final long lastUpdate){
        return new RetrieveHandler<List<User>, List<User>>() {
            @NonNull @Override
            protected LiveData<List<User>> loadFromDatabase() {
                MutableLiveData<List<User>> userLiveData = new MutableLiveData<List<User>>();
                AsyncTask.execute(() -> {
                    userLiveData.postValue(userHelperInstance.getByTrainerUserId(trainerUserId));
                });
                return userLiveData;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                return NetworkUtil.isConnected();
            }

            @Override
            protected boolean saveAPIResponse(@NonNull APIData<List<User>> responseData) {
                return userHelperInstance.insertOrUpdateIfExists(responseData.getContent());
            }

            @NonNull @Override
            protected LiveData<APIResponse<List<User>>> loadFromAPI() {
                return APIUtils.callToLiveData(userAPIInstance.getUserList(trainerUserId, lastUpdate));
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // TODO what to do here ? Will we show a message ?
                Log.d("sdaf", "asdf");
            }
        }.getAsLiveData();
    }

    public void uploadUser(final User user){
        new SaveHandler<User, User>(user) {
            @Override
            protected boolean shouldUploadToAPI() {
                return NetworkUtil.isConnected();
            }

            @Override
            protected boolean shouldSaveAPIResponse() {
                return false;
            }

            @Override
            protected boolean saveDataObjectToDatabase(@NonNull User objectToSave) {
                return userHelperInstance.insertOrUpdateIfExists(objectToSave);
            }

            @Override
            protected boolean saveResponseDataToDatabase(@NonNull APIData<User> responseData) {
                return true;
            }

            @Override
            protected LiveData<APIResponse<User>> uploadToAPI(@NonNull User dataToUpload) {
                return APIUtils.callToLiveData(userAPIInstance.uploadUser(dataToUpload));
            }

            @Override
            protected void onAPIUploadFailed() {
                // TODO what to do here ? Will we show a message ?
            }

            @Override
            protected void onDatabaseSaveFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        };
    }

    public void localSaveUser(final User user){
        new SaveHandler<User, User>(user) {
            @Override
            protected boolean shouldUploadToAPI() {
                return false;
            }

            @Override
            protected boolean shouldSaveAPIResponse() {
                return false;
            }

            @Override
            protected boolean saveDataObjectToDatabase(@NonNull User objectToSave) {
                return userHelperInstance.insertOrUpdateIfExists(objectToSave);
            }

            @Override
            protected boolean saveResponseDataToDatabase(@NonNull APIData<User> responseData) {
                return true;
            }

            @Override
            protected LiveData<APIResponse<User>> uploadToAPI(@NonNull User dataToUpload) {
                return new MutableLiveData<APIResponse<User>>();
            }

            @Override
            protected void onAPIUploadFailed() {
                // Nothing to do
            }

            @Override
            protected void onDatabaseSaveFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        };
    }

    public LiveData<Resource<User>> getUpdatedUser(final long userId){
        return new RetrieveHandler<User, User>() {
            @NonNull @Override
            protected LiveData<User> loadFromDatabase() {
                MutableLiveData<User> userLiveData = new MutableLiveData<User>();
                AsyncTask.execute(() -> {
                    userLiveData.postValue(userHelperInstance.getById(userId));
                });
                return userLiveData;
            }

            @Override
            protected boolean shouldFetchFromAPI() {
                return NetworkUtil.isConnected();
            }

            @Override
            protected boolean saveAPIResponse(@NonNull APIData<User> responseData) {
                return userHelperInstance.insertOrUpdateIfExists(responseData.getContent());
            }

            @NonNull @Override
            protected LiveData<APIResponse<User>> loadFromAPI() {
                return APIUtils.callToLiveData(userAPIInstance.getUser(userId));
            }

            @Override
            protected void onFetchFromAPIFailed() {
                // TODO what to do here ? Will we show a message ?
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<User>> updateUser(final long userId){
        return getUpdatedUser(userId);
    }

}