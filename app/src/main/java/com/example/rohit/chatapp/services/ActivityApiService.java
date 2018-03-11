package com.example.rohit.chatapp.services;

import com.example.rohit.chatapp.services.model.ActivityObject;
import com.example.rohit.chatapp.services.model.MessageList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rohit on 8/23/17.
 */

public interface ActivityApiService {
    @GET("/activities/mode/source/{source}/" +
            "learningContextId/{learningContextId}/" +
            "learningResourceId/{learningResourceId}/" +
            "userId/{userId}")
    Observable<ActivityObject> fetchActivityInfo(
            @Path("source") String source,
            @Path("learningContextId") String learningContextId,
            @Path("learningResourceId") String learningResourceId,
            @Path("userId") String userId
    );
}
