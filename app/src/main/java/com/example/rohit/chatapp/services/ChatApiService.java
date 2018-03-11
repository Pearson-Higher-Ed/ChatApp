package com.example.rohit.chatapp.services;

import com.example.rohit.chatapp.services.model.MessageList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by rohit on 8/10/17.
 */
public interface ChatApiService {
    @GET("/chatTranscripts/source/{source}/learningContext/{contextId}/userId/{userId}/dialogInteractionId/{dialogId}")
    Observable<List<MessageList>> fetchPreviousMessages(
            @Path("source") String source,
            @Path("contextId") String contextId,
            @Path("userId") String userId,
            @Path("dialogId") String dialogId
    );

}
