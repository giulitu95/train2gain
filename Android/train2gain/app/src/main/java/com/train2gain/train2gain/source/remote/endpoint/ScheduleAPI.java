package com.train2gain.train2gain.source.remote.endpoint;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.Schedule;
import com.train2gain.train2gain.source.remote.response.APIData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*
 * Defines all the possible API endpoints for Schedule related entities
 */
public interface ScheduleAPI {

    /**
     * Retrieves the current user's schedule (current active schedule, not last)
     * @param athleteUserId for which user we want to retrieve the schedule
     * @return an APIData Call object that we use to make the request
     */
    @GET(value = "newSchedule/userId/{athlete_user_id}/lastScheduleId/null")
    Call<APIData<Schedule>> getCurrentSchedule (
        @Path(value = "athlete_user_id") long athleteUserId
    );

    /**
     * Retrieves the new schedule (last schedule) for the user, if it exists
     * @param athleteUserId for which user we want to retrieve the schedule
     * @param currentScheduleId the current active schedule for user id given as parameter
     * @return an APIData Call object that we use to make the request
     */
    @GET(value = "newSchedule/userId/{athlete_user_id}/lastScheduleId/{current_schedule_id}")
    Call<APIData<Schedule>> getNewSchedule (
        @Path(value = "athlete_user_id") long athleteUserId,
        @Path(value = "current_schedule_id") long currentScheduleId
    );

    /**
     * Retrieves the list of schedules for a specific user (list minimal, only Schedule attributes)
     * Retrieves results only from a specific date (given as parameter) until now. If the given
     * lastUpdateDate is 0, the API retrieves all the schedules for that particular user
     * @param athleteUserId for which user we want to retrieve the list of schedules
     * @param lastUpdateDate last time we called this API (last time we updated the user's schedule list)
     * @return an APIData Call object that we use to make the request
     */
    @GET(value = "scheduleList/userId/{athlete_user_id}/lastUpdate/{last_update_date}")
    Call<APIData<List<Schedule>>> getScheduleListMinimal (
        @Path(value = "athlete_user_id") long athleteUserId,
        @Path(value = "last_update_date") long lastUpdateDate
    );

    /**
     * Uploads to the API server the schedule given as parameter
     * NOTE: the API server returns, if the upload will be successful, a response with the remote id
     * of the schedule and all the remote ids of Schedule's nested objects (see Schedule related entities)
     * @param schedule the schedule that we want to upload to the API server (the one we created locally)
     * @return an APIData Call object that we use to make the request
     */
    @Headers({
        "Content-type: application/json"
    })
    @POST(value = "schedule")
    Call<APIData<Schedule>> uploadNewSchedule (
        @Body @NonNull Schedule schedule
    );

}
