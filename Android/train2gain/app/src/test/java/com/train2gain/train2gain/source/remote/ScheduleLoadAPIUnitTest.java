package com.train2gain.train2gain.source.remote;

import android.arch.lifecycle.LiveData;

import com.train2gain.train2gain.model.entity.ScheduleLoad;
import com.train2gain.train2gain.source.remote.endpoint.ScheduleLoadAPI;
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

public class ScheduleLoadAPIUnitTest extends BaseAPIUnitTest {

    // Variable misc
    private static final ScheduleLoadAPI scheduleLoadAPIImpl = APIClient.getImplementation(ScheduleLoadAPI.class);

    @Test
    public void getScheduleLoads() throws Exception {

        // GET Data
        Call<APIData<List<ScheduleLoad>>> apiCall = scheduleLoadAPIImpl.getScheduleLoads(1,1, 0);
        final LiveData<APIResponse<List<ScheduleLoad>>> apiResponseLiveData = APIUtils.callToLiveData(apiCall);

        // GET response object
        APIResponse<List<ScheduleLoad>> scheduleLoadsAPIResponse = BaseAPIUnitTest.waitForLiveData(apiResponseLiveData);

        // Check most of API data is correct
        assertNotNull(scheduleLoadsAPIResponse);
        assertEquals(scheduleLoadsAPIResponse.getStatus(), APIResponse.Status.SUCCESS);
        assertNotNull(scheduleLoadsAPIResponse.getData());
        assertEquals(scheduleLoadsAPIResponse.getData().getClass(), APIData.class);
        assertEquals(scheduleLoadsAPIResponse.getData().getServerTimestamp().getClass(), Date.class);
        assertTrue(scheduleLoadsAPIResponse.getData().getContent() != null);
        for(ScheduleLoad scheduleLoad: scheduleLoadsAPIResponse.getData().getContent()){
            assertEquals(scheduleLoad.getClass(), ScheduleLoad.class);
        }

    }

    /**
     * TODO: implement the uploadScheduleLoads() Test
     * NOTE: this will be implemented when DB layer is done
     */

}
