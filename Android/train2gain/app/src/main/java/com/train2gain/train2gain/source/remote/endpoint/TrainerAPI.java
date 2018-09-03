package com.train2gain.train2gain.source.remote.endpoint;

import com.train2gain.train2gain.model.entity.Trainer;
import com.train2gain.train2gain.source.remote.response.APIData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/*
 * Defines all the possible API endpoints for the Trainer entity
 */
public interface TrainerAPI {

    /**
     * Retrieves trainer information (like the gym where he works..) for a specific trainer user
     * @param trainerUserId for which trainer user we want to retrieve this kind of information
     * @return an APIData Call object that we use to make the request
     */
    @GET(value = "trainerInfo/userId/{trainer_user_id}")
    Call<APIData<Trainer>> getTrainer (
        @Path(value = "trainer_user_id") long trainerUserId
    );

}
