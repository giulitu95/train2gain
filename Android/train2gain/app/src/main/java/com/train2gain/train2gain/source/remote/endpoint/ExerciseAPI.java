package com.train2gain.train2gain.source.remote.endpoint;

import com.train2gain.train2gain.model.entity.Exercise;
import com.train2gain.train2gain.source.remote.response.APIData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/*
 * Defines all the possible API endpoints for Exercise entity
 */
public interface ExerciseAPI {

    /**
     * Retrieves the list of available exercises
     * Retrieves results only from a specific date (given as parameter) until now. If the given
     * lastUpdateDate is 0, the API retrieves all the exercises
     * @param lastUpdateDate last time we called this API (last time we updated the exercise table)
     * @return an APIData Call object that we use to make the request
     */
    @GET(value = "exerciseList/lastUpdate/{last_update_date}")
    Call<APIData<List<Exercise>>> getExercises (
        @Path(value = "last_update_date") long lastUpdateDate
    );

}
