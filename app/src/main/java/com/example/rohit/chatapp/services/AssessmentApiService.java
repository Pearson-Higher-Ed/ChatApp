package com.example.rohit.chatapp.services;
import com.example.rohit.chatapp.services.model.AssessmentObject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
/**
 * Created by rohit on 8/23/17.
 */

public interface AssessmentApiService {
    @GET("/assessments/educationalgoal/{educationalgoal}")
    Observable<AssessmentObject> fetchAssessmentInfo(
            @Path("educationalgoal") String educationalgoal
    );
}
