package com.train2gain.train2gain.source.remote;

import android.arch.lifecycle.LiveData;

import com.train2gain.train2gain.model.entity.Trainer;
import com.train2gain.train2gain.source.remote.endpoint.TrainerAPI;
import com.train2gain.train2gain.source.remote.response.APIData;
import com.train2gain.train2gain.source.remote.response.APIResponse;
import com.train2gain.train2gain.source.remote.util.APIUtils;

import org.junit.Test;

import java.util.Date;

import retrofit2.Call;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrainerAPIUnitTest extends BaseAPIUnitTest {

    // Variable misc
    private static final TrainerAPI trainerAPIImpl = APIClient.getImplementation(TrainerAPI.class);

    @Test
    public void getTrainer() throws Exception {

        // GET Data
        Call<APIData<Trainer>> apiCall = trainerAPIImpl.getTrainer(2);
        final LiveData<APIResponse<Trainer>> apiResponseLiveData = APIUtils.callToLiveData(apiCall);

        // GET response object
        APIResponse<Trainer> trainerAPIResponse = BaseAPIUnitTest.waitForLiveData(apiResponseLiveData);

        // Check most of API data is correct
        assertNotNull(trainerAPIResponse);
        assertEquals(trainerAPIResponse.getStatus(), APIResponse.Status.SUCCESS);
        assertNotNull(trainerAPIResponse.getData());
        assertEquals(trainerAPIResponse.getData().getClass(), APIData.class);
        assertEquals(trainerAPIResponse.getData().getServerTimestamp().getClass(), Date.class);
        assertEquals(trainerAPIResponse.getData().getContent().getClass(), Trainer.class);

    }

}
