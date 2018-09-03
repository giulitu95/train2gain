package com.train2gain.train2gain.source.remote.endpoint;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleLoad;
import com.train2gain.train2gain.source.remote.response.APIData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*
 * Defines all the possible API endpoints for the ScheduleLoad entity
 */
public interface ScheduleLoadAPI {

    /**
     * Retrieves the loads related to a particular schedule
     * Retrieves results only from a specific date (given as parameter) until now. If the given
     * lastUpdateDate is 0, the API retrieves all the loads (from schedule start date)
     * @param athleteUserId the owner of the schedule for which we want the schedule's loads
     * @param scheduleId for which schedule we want to retrieve the schedule's loads
     * @param lastUpdateDate last time we called this API (last time we updated the schedule's loads)
     * @return an APIData Call object that we use to make the request
     */
    @GET(value = "loads/userId/{athlete_user_id}/scheduleId/{schedule_id}/lastUpdate/{last_update_date}")
    Call<APIData<List<ScheduleLoad>>> getScheduleLoads (
        @Path(value = "athlete_user_id") long athleteUserId,
        @Path(value = "schedule_id") long scheduleId,
        @Path(value = "last_update_date") long lastUpdateDate
    );

    /**
     * Uploads to the API server the list of schedule's loads given as parameter
     * NOTE: the API server returns, if the upload will be successful, a response with a list of
     * remote ids, one for each schedule's load that was uploaded successfully to the API server
     * @param scheduleLoadList the list of schedule's loads we want to upload to the API server
     * @return an APIData Call object that we use to make the request
     */
    @Headers({
        "Content-type: application/json"
    })
    @POST(value = "loads")
    Call<APIData<List<ScheduleLoad>>> uploadScheduleLoads (
        @Body @NonNull List<ScheduleLoad> scheduleLoadList
    );

}
