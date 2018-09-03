package com.train2gain.train2gain.source.remote;

import android.arch.lifecycle.LiveData;

import com.train2gain.train2gain.model.entity.Exercise;
import com.train2gain.train2gain.source.remote.endpoint.ExerciseAPI;
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

public class ExerciseAPIUnitTest extends BaseAPIUnitTest {

    // Variable misc
    private static final ExerciseAPI exerciseAPIImpl = APIClient.getImplementation(ExerciseAPI.class);

    @Test
    public void getExercises() throws Exception {

        // GET Data
        Call<APIData<List<Exercise>>> apiCall = exerciseAPIImpl.getExercises(0);
        final LiveData<APIResponse<List<Exercise>>> apiResponseLiveData = APIUtils.callToLiveData(apiCall);

        // GET response object
        APIResponse<List<Exercise>> exercisesAPIResponse = BaseAPIUnitTest.waitForLiveData(apiResponseLiveData);

        // Check most of API data is correct
        assertNotNull(exercisesAPIResponse);
        assertEquals(exercisesAPIResponse.getStatus(), APIResponse.Status.SUCCESS);
        assertNotNull(exercisesAPIResponse.getData());
        assertEquals(exercisesAPIResponse.getData().getClass(), APIData.class);
        assertEquals(exercisesAPIResponse.getData().getServerTimestamp().getClass(), Date.class);
        assertTrue(exercisesAPIResponse.getData().getContent() != null);
        for(Exercise exercise: exercisesAPIResponse.getData().getContent()){
            assertEquals(exercise.getClass(), Exercise.class);
        }

    }

}
