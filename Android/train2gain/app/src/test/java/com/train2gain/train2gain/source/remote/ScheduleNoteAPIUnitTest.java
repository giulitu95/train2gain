package com.train2gain.train2gain.source.remote;

import android.arch.lifecycle.LiveData;

import com.train2gain.train2gain.model.entity.ScheduleNote;
import com.train2gain.train2gain.source.remote.endpoint.ScheduleNoteAPI;
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

public class ScheduleNoteAPIUnitTest extends BaseAPIUnitTest {

    // Variable misc
    private static final ScheduleNoteAPI scheduleNoteAPIImpl = APIClient.getImplementation(ScheduleNoteAPI.class);

    @Test
    public void getScheduleNotes() throws Exception {

        // GET Data
        Call<APIData<List<ScheduleNote>>> apiCall = scheduleNoteAPIImpl.getScheduleNotes(1,1, 0);
        final LiveData<APIResponse<List<ScheduleNote>>> apiResponseLiveData = APIUtils.callToLiveData(apiCall);

        // GET response object
        APIResponse<List<ScheduleNote>> scheduleNotesAPIResponse = BaseAPIUnitTest.waitForLiveData(apiResponseLiveData);

        // Check most of API data is correct
        assertNotNull(scheduleNotesAPIResponse);
        assertEquals(scheduleNotesAPIResponse.getStatus(), APIResponse.Status.SUCCESS);
        assertNotNull(scheduleNotesAPIResponse.getData());
        assertEquals(scheduleNotesAPIResponse.getData().getClass(), APIData.class);
        assertEquals(scheduleNotesAPIResponse.getData().getServerTimestamp().getClass(), Date.class);
        assertTrue(scheduleNotesAPIResponse.getData().getContent() != null);
        for(ScheduleNote scheduleLoad: scheduleNotesAPIResponse.getData().getContent()){
            assertEquals(scheduleLoad.getClass(), ScheduleNote.class);
        }

    }

    /**
     * TODO: implement the uploadScheduleNotes() Test
     * NOTE: this will be implemented when DB layer is done
     */

}
