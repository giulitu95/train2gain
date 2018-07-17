package com.train2gain.train2gain.source.remote;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.train2gain.train2gain.model.enums.MuscleGroup;
import com.train2gain.train2gain.model.enums.ScheduleStepType;
import com.train2gain.train2gain.model.enums.UserType;
import com.train2gain.train2gain.source.remote.converter.DateConverter;
import com.train2gain.train2gain.source.remote.converter.MuscleGroupConverter;
import com.train2gain.train2gain.source.remote.converter.ScheduleStepTypeConverter;
import com.train2gain.train2gain.source.remote.converter.UserTypeConverter;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String ROOT_URL = "http://train2gainrest.herokuapp.com/api/";
    private static Retrofit retrofit = null;

    /**
     * Creates a custom Retrofit instance (if not exists already) and returns it
     * @return a custom retrofit instance
     */
    public static Retrofit getInstance(){
        if(retrofit == null){
            synchronized (APIClient.class){
                if(retrofit == null){
                    retrofit = getCustomRetrofit(getCustomGson());
                }
            }
        }
        return retrofit;
    }

    /**
     * Creates and returns an implementation for a specific API interface
     * @param APIInterface the interface containing one or more API request methods
     * @param <T> the API Interface class type (.class)
     * @return the implementation for the given API interface, ready to use
     */
    public static <T> T getImplementation(@NonNull Class<T> APIInterface){
        return getInstance().create(APIInterface);
    }

    /**
     * Creates a Gson object with some custom properties
     * @return a custom Gson object ready to use with a Retrofit instance
     */
    private static Gson getCustomGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateConverter());
        gsonBuilder.registerTypeAdapter(ScheduleStepType.class, new ScheduleStepTypeConverter());
        gsonBuilder.registerTypeAdapter(UserType.class, new UserTypeConverter());
        gsonBuilder.registerTypeAdapter(MuscleGroup.class, new MuscleGroupConverter());
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        return gsonBuilder.create();
    }

    /**
     * Creates a Retrofit instance with some custom properties like URL, Gson object, ecc..
     * @param customGson a custom Gson object which will be used by Retrofit to read / convert API responses
     * @return a custom Retrofit instance ready to be used to call and handle API requests
     */
    private static Retrofit getCustomRetrofit(@NonNull Gson customGson){
        Retrofit.Builder builder = new retrofit2.Retrofit.Builder();
        builder = builder.baseUrl(ROOT_URL);
        builder = builder.addConverterFactory(GsonConverterFactory.create(customGson));
        return builder.build();
    }

}
