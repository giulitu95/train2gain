package com.train2gain.train2gain.source.remote;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.train2gain.train2gain.source.remote.response.APIResponse;

import org.junit.Before;
import org.junit.Rule;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class BaseAPIUnitTest {

    @Rule // Used to set background task executor (for example LiveData handler) to run synchronously
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before // Init Retrofit
    public void init(){
        APIClient.getInstance();
    }

    /**
     * Method used to wait until LiveData value changes
     * @param responseLiveData LiveData to observe
     * @throws Exception
     */
    public static <T> APIResponse<T> waitForLiveData(final LiveData<APIResponse<T>> responseLiveData)
            throws Exception {
        @SuppressWarnings("unchecked")
        final APIResponse<T>[] apiResponse = (APIResponse<T>[]) new APIResponse[1];

        // CountDownLatch is a sort of inverse counter. It starts with a 'value' and
        // each time countDown() is called on this object, the 'value' decreases
        final CountDownLatch latch = new CountDownLatch(1);

        // Observe LiveData for changes..
        Observer<APIResponse<T>> observer = new Observer<APIResponse<T>>() {
            @Override
            public void onChanged(@Nullable APIResponse<T> response) {
                apiResponse[0] = response;
                latch.countDown();
                responseLiveData.removeObserver(this);
            }
        };
        responseLiveData.observeForever(observer);

        // Wait until CountDownLatch go to 0 (= LiveData data changed)
        latch.await(1, TimeUnit.SECONDS);

        // Returns liveData value that has changed
        return apiResponse[0];
    }

}
