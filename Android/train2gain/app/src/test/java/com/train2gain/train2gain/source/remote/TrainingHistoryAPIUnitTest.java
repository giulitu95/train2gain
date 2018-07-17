package com.train2gain.train2gain.source.remote;

import android.arch.lifecycle.LiveData;

import com.train2gain.train2gain.model.entity.TrainingHistory;
import com.train2gain.train2gain.source.remote.endpoint.TrainingHistoryAPI;
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

public class TrainingHistoryAPIUnitTest extends BaseAPIUnitTest {

    // Variable misc
    private final static TrainingHistoryAPI trainingHistoryAPIImpl = APIClient.getImplementation(TrainingHistoryAPI.class);

    @Test
    public void getTrainingHistory() throws Exception {

        // GET Data
        Call<APIData<List<TrainingHistory>>> apiCall = trainingHistoryAPIImpl.getTrainingHistory(1,0);
        final LiveData<APIResponse<List<TrainingHistory>>> apiResponseLiveData = APIUtils.callToLiveData(apiCall);

        // GET response object
        APIResponse<List<TrainingHistory>> trainingHistoryAPIResponse = BaseAPIUnitTest.waitForLiveData(apiResponseLiveData);

        // Check most of API data is correct
        assertNotNull(trainingHistoryAPIResponse);
        assertEquals(trainingHistoryAPIResponse.getStatus(), APIResponse.Status.SUCCESS);
        assertNotNull(trainingHistoryAPIResponse.getData());
        assertEquals(trainingHistoryAPIResponse.getData().getClass(), APIData.class);
        assertEquals(trainingHistoryAPIResponse.getData().getServerTimestamp().getClass(), Date.class);
        assertTrue(trainingHistoryAPIResponse.getData().getContent() != null);
        for(TrainingHistory trainingHistory: trainingHistoryAPIResponse.getData().getContent()){
            assertEquals(trainingHistory.getClass(), TrainingHistory.class);
        }

    }

    /**
     * TODO: implement the uploadTrainingHistory() Test
     * NOTE: this will be implemented when DB layer is done
     */

}
