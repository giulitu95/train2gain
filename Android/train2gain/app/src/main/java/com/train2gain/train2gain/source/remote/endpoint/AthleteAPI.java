package com.train2gain.train2gain.source.remote.endpoint;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.source.remote.response.APIData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*
 * Defines all the possible API endpoints for Athlete entity
 */
public interface AthleteAPI {

    /**
     * Retrieves athlete information (like weight, height, ecc..) for a specific athlete user
     * @param athleteUserId for which athlete user we want to retrieve this kind of information
     * @return an APIData Call object that we use to make the request
     */
    @GET(value = "athleteInfo/userId/{athlete_user_id}")
    Call<APIData<Athlete>> getAthlete (
        @Path(value = "athlete_user_id") long athleteUserId
    );

    /**
     * Uploads to the API server the athlete user given as parameter.
     * NOTE: If the athlete user exists already, the API server will only update the athlete data,
     * otherwise it will create a new athlete user with the one given in the body.
     * NOTE: the API server returns, if the upload will be successful, a response with the remote
     * id of the athlete user we have uploaded
     * @param athlete the athlete user we want to upload to the API server
     * @return an APIData Call object that we use to make the request
     */
    @Headers({
        "Content-type: application/json"
    })
    @POST(value = "athleteInfo")
    Call<APIData<Athlete>> uploadAthlete (
        @Body @NonNull Athlete athlete
    );

}
