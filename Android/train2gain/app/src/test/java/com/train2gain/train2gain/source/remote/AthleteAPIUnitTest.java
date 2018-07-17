package com.train2gain.train2gain.source.remote;

import android.arch.lifecycle.LiveData;

import com.train2gain.train2gain.model.entity.Athlete;
import com.train2gain.train2gain.source.remote.endpoint.AthleteAPI;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;
import com.train2gain.train2gain.source.remote.util.APIUtils;

import org.junit.Test;

import java.util.Date;

import retrofit2.Call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AthleteAPIUnitTest extends BaseAPIUnitTest {

    // Variable misc
    private static final AthleteAPI athleteAPIImpl = APIClient.getImplementation(AthleteAPI.class);

    @Test
    public void getAthlete() throws Exception {

        // GET Data
        Call<APIData<Athlete>> apiCall = athleteAPIImpl.getAthlete(1);
        final LiveData<APIResponse<Athlete>> apiResponseLiveData = APIUtils.callToLiveData(apiCall);

        // GET response object
        APIResponse<Athlete> athleteAPIResponse = BaseAPIUnitTest.waitForLiveData(apiResponseLiveData);

        // Check most of API data is correct
        assertNotNull(athleteAPIResponse);
        assertEquals(athleteAPIResponse.getStatus(), APIResponse.Status.SUCCESS);
        assertNotNull(athleteAPIResponse.getData());
        assertEquals(athleteAPIResponse.getData().getClass(), APIData.class);
        assertEquals(athleteAPIResponse.getData().getServerTimestamp().getClass(), Date.class);
        assertEquals(athleteAPIResponse.getData().getContent().getClass(), Athlete.class);

    }

    /**
     * TODO: implement the uploadAthlete() Test
     * NOTE: this will be implemented when DB layer is done
     */

}
