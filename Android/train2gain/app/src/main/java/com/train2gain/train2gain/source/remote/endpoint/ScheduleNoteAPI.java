package com.train2gain.train2gain.source.remote.endpoint;

import android.support.annotation.NonNull;

import com.train2gain.train2gain.model.entity.ScheduleNote;
import com.train2gain.train2gain.source.remote.response.APIData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/*
 * Defines all the possible API endpoints for the ScheduleNote entity
 */
public interface ScheduleNoteAPI {

    /**
     * Retrieves the notes related to a particular schedule
     * Retrieves results only from a specific date (given as parameter) until now. If the given
     * lastUpdateDate is 0, the API retrieves all the notes (from schedule start date)
     * @param athleteUserId the owner of the schedule for which we want the schedule's notes
     * @param scheduleId for which schedule we want to retrieve the schedule's notes
     * @param lastUpdateDate last time we called this API (last time we updated the schedule's notes)
     * @return an APIData Call object that we use to make the request
     */
    @GET(value = "notes/userId/{athlete_user_id}/scheduleId/{schedule_id}/lastUpdate/{last_update_date}")
    Call<APIData<List<ScheduleNote>>> getScheduleNotes (
        @Path(value = "athlete_user_id") long athleteUserId,
        @Path(value = "schedule_id") long scheduleId,
        @Path(value = "last_update_date") long lastUpdateDate
    );

    /**
     * Uploads to the API server the list of schedule's notes given as parameter
     * NOTE: the API server returns, if the upload will be successful, a response with a list of
     * remote ids, one for each schedule's note that was uploaded successfully to the API server
     * @param scheduleNoteList the list of schedule's notes we want to upload to the API server
     * @return an APIData Call object that we use to make the request
     */
    @Headers({
        "Content-type: application/json"
    })
    @POST(value = "notes")
    Call<APIData<List<ScheduleNote>>> uploadScheduleNotes (
        @Body @NonNull List<ScheduleNote> scheduleNoteList
    );

}
