package com.train2gain.train2gain.source.remote;

import android.arch.lifecycle.LiveData;

import com.train2gain.train2gain.model.entity.User;
import com.train2gain.train2gain.source.remote.endpoint.UserAPI;
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

public class UserAPIUnitTest extends BaseAPIUnitTest {

    // Variable misc
    private static final UserAPI userAPIImpl = APIClient.getImplementation(UserAPI.class);

    @Test
    public void getUser() throws Exception {

        // GET Data
        Call<APIData<User>> apiCall = userAPIImpl.getUser(1);
        final LiveData<APIResponse<User>> apiResponseLiveData = APIUtils.callToLiveData(apiCall);

        // GET response object
        APIResponse<User> userAPIResponse = BaseAPIUnitTest.waitForLiveData(apiResponseLiveData);

        // Check most of API data is correct
        assertNotNull(userAPIResponse);
        assertEquals(userAPIResponse.getStatus(), APIResponse.Status.SUCCESS);
        assertNotNull(userAPIResponse.getData());
        assertEquals(userAPIResponse.getData().getClass(), APIData.class);
        assertEquals(userAPIResponse.getData().getServerTimestamp().getClass(), Date.class);
        assertEquals(userAPIResponse.getData().getContent().getClass(), User.class);

    }

    @Test
    public void getUserList() throws Exception {

        // GET Data
        Call<APIData<List<User>>> apiCall = userAPIImpl.getUserList(2, 0);
        final LiveData<APIResponse<List<User>>> apiResponseLiveData = APIUtils.callToLiveData(apiCall);

        // GET response object
        APIResponse<List<User>> userListAPIResponse = BaseAPIUnitTest.waitForLiveData(apiResponseLiveData);

        // Check most of API data is correct
        assertNotNull(userListAPIResponse);
        assertEquals(userListAPIResponse.getStatus(), APIResponse.Status.SUCCESS);
        assertNotNull(userListAPIResponse.getData());
        assertEquals(userListAPIResponse.getData().getClass(), APIData.class);
        assertEquals(userListAPIResponse.getData().getServerTimestamp().getClass(), Date.class);
        assertTrue(userListAPIResponse.getData().getContent() != null);
        for(User user: userListAPIResponse.getData().getContent()){
            assertEquals(user.getClass(), User.class);
        }

    }

    /**
     * TODO: implement the uploadUser() Test
     * NOTE: this will be implemented when DB layer is done
     */

}
