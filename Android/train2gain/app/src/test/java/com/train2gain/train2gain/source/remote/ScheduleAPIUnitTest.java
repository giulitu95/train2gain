package com.train2gain.train2gain.source.remote;

import android.arch.lifecycle.LiveData;

import com.train2gain.train2gain.model.entity.Schedule;
import com.train2gain.train2gain.source.remote.endpoint.ScheduleAPI;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;
import com.train2gain.train2gain.source.remote.util.APIUtils;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import retrofit2.Call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ScheduleAPIUnitTest extends BaseAPIUnitTest {

    // Variable misc
    private static final ScheduleAPI scheduleAPIImpl = APIClient.getImplementation(ScheduleAPI.class);

    @Test
    public void getCurrentSchedule() throws Exception {

        // GET Data
        Call<APIData<Schedule>> apiCall = scheduleAPIImpl.getCurrentSchedule(1);
        final LiveData<APIResponse<Schedule>> apiResponseLiveData = APIUtils.callToLiveData(apiCall);

        // GET response object
        APIResponse<Schedule> scheduleAPIResponse = BaseAPIUnitTest.waitForLiveData(apiResponseLiveData);

        // Check most of API data is correct
        assertNotNull(scheduleAPIResponse);
        assertEquals(scheduleAPIResponse.getStatus(), APIResponse.Status.SUCCESS);
        assertNotNull(scheduleAPIResponse.getData());
        assertEquals(scheduleAPIResponse.getData().getClass(), APIData.class);
        assertEquals(scheduleAPIResponse.getData().getServerTimestamp().getClass(), Date.class);
        assertEquals(scheduleAPIResponse.getData().getContent().getClass(), Schedule.class);

    }

    @Test
    public void getNewSchedule() throws Exception {

        // GET Data
        Call<APIData<Schedule>> apiCall = scheduleAPIImpl.getNewSchedule(1,2);
        final LiveData<APIResponse<Schedule>> apiResponseLiveData = APIUtils.callToLiveData(apiCall);

        // GET response object
        APIResponse<Schedule> scheduleAPIResponse = BaseAPIUnitTest.waitForLiveData(apiResponseLiveData);

        // Check most of API data is correct
        assertNotNull(scheduleAPIResponse);
        assertEquals(scheduleAPIResponse.getStatus(), APIResponse.Status.SUCCESS);
        assertNotNull(scheduleAPIResponse.getData());
        assertEquals(scheduleAPIResponse.getData().getClass(), APIData.class);
        assertEquals(scheduleAPIResponse.getData().getServerTimestamp().getClass(), Date.class);
        assertEquals(scheduleAPIResponse.getData().getContent().getClass(), Schedule.class);

    }

    @Test
    public void getScheduleListMinimal() throws Exception {

        // GET Data
        Call<APIData<List<Schedule>>> apiCall = scheduleAPIImpl.getScheduleListMinimal(1, 0);
        final LiveData<APIResponse<List<Schedule>>> apiResponseLiveData = APIUtils.callToLiveData(apiCall);

        // GET response object
        APIResponse<List<Schedule>> scheduleListAPIResponse = BaseAPIUnitTest.waitForLiveData(apiResponseLiveData);

        // Check most of API data is correct
        assertNotNull(scheduleListAPIResponse);
        assertEquals(scheduleListAPIResponse.getStatus(), APIResponse.Status.SUCCESS);
        assertNotNull(scheduleListAPIResponse.getData());
        assertEquals(scheduleListAPIResponse.getData().getClass(), APIData.class);
        assertEquals(scheduleListAPIResponse.getData().getServerTimestamp().getClass(), Date.class);
        assertTrue(scheduleListAPIResponse.getData().getContent() != null);
        for(Schedule schedule: scheduleListAPIResponse.getData().getContent()){
            assertEquals(schedule.getClass(), Schedule.class);
        }

    }

    /**
     * TODO: implement the uploadNewSchedule() Test
     * NOTE: this will be implemented when DB layer is done
     */

}
