package com.train2gain.train2gain.source.remote.endpoint;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.source.remote.response.APIData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*
 * Defines all the possible API endpoints for User entity
 */
public interface UserAPI {

    /**
     * Retrieves base information (like email, name, ecc..) for a particular user
     * @param userId for which user we want to retrieve this kind of information
     * @return an APIData Call object that we use to make the request
     */
    @GET(value = "userProfile/userId/{user_id}")
    Call<APIData<User>> getUser (
        @Path(value = "user_id") long userId
    );

    /**
     * Retrieves the users (athlete) whose trainer is the one given as parameter
     * Retrieves results only from a specific date (given as parameter) until now. If the given
     * lastUpdateDate is 0, the API retrieves all the users..
     * @param trainerUserId for which trainer we want to get the users
     * @param lastUpdateDate last time we called this API (last time we search for trainer's users)
     * @return an APIData Call object that we use to make the request
     */
    @GET(value = "userProfileList/trainerId/{trainer_user_id}/lastUpdate/{last_update_date}")
    Call<APIData<List<User>>> getUserList (
        @Path(value = "trainer_user_id") long trainerUserId,
        @Path(value = "last_update_date") long lastUpdateDate
    );

    /**
     * Uploads to the API server the user given as parameter.
     * NOTE: If the user exists already, the API server will only update the user data, otherwise
     * it will create a new user with the one given in the body.
     * NOTE: the API server returns, if the upload will be successful, a response with the remote
     * id of the user we have uploaded
     * @param user the user we want to upload to the API server
     * @return an APIData Call object that we use to make the request
     */
    @Headers({
        "Content-type: application/json"
    })
    @POST(value = "userProfile")
    Call<APIData<User>> uploadUser (
        @Body @NonNull User user
    );

}
