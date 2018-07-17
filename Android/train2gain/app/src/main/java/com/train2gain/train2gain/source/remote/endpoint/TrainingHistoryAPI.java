package com.train2gain.train2gain.source.remote.endpoint;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.TrainingHistory;
import com.train2gain.train2gain.source.remote.response.APIData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*
 * Defines all the possible API endpoints for the TrainingHistory entity
 */
public interface TrainingHistoryAPI {

    /**
     * Retrieves the training history related to a particular athlete user.
     * Retrieves results only from a specific date (given as parameter) until now. If the given
     * lastUpdateDate is 0, the API retrieves all the training history (from user registration date)
     * NOTE: results are limited to the last 90 days (see API server specifications)
     * @param athleteUserId for which athlete we want to retrieve the training history
     * @param lastUpdateDate last time we called this API (last time we updated athlete's training history)
     * @return an APIData Call object that we use to make the request
     */
    @GET(value = "trainingFrequencesList/userId/{athlete_user_id}/lastUpdate/{last_update_date}")
    Call<APIData<List<TrainingHistory>>> getTrainingHistory (
        @Path(value = "athlete_user_id") long athleteUserId,
        @Path(value = "last_update_date") long lastUpdateDate
    );

    /**
     * Uploads to the API server the training history given as parameter.
     * NOTE: the API server returns, if the upload will be successful, a response with a list of
     * remote ids, one for each training history element that was uploaded successfully to the API server
     * @param trainingHistoryList the training history we want to upload to the API server
     * @return an APIData Call object that we use to make the request
     */
    @Headers({
        "Content-type: application/json"
    })
    @POST(value = "trainingFrequencesList")
    Call<APIData<List<TrainingHistory>>> uploadTrainingHistory (
        @Body @NonNull List<TrainingHistory> trainingHistoryList
    );

}
