package com.example.rohit.chatapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rohit.chatapp.services.ActivityApiService;
import com.example.rohit.chatapp.services.AssessmentApiService;
import com.example.rohit.chatapp.services.AuthInterceptor;
import com.example.rohit.chatapp.services.model.ActivityObject;
import com.example.rohit.chatapp.services.model.AssessmentObject;
import com.facebook.shimmer.ShimmerFrameLayout;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    ShimmerFrameLayout shimmer;
    ShimmerFrameLayout shimmer_two;
    private final String TAG = getClass().getSimpleName();
    private static final String BASE_URL_ASSESSMENT = "https://assessmentcomposite-qac.dev-prsn.com";
    private static final String BASE_URL_ACTIVITY = "https://activitycomposite-qac.dev-prsn.com";
    private static Retrofit assessmentRetrofit = null;
    private static Retrofit activityRetrofit = null;
    private static final String TOKEN = "eyJhbGciOiJSUzUxMiIsImtpZCI6ImsxMDY5NDgxOTAifQ.eyJleHAiOjE1MDM1NTEyMzEsInN1YiI6ImZmZmZmZmZmNTdhYTExZjFlNGIwYjk3ZmY0YTkwYzYzIiwic2Vzc2lkIjoiNzdlNTYyZmY2ZDliNDc5Mzk0MGExYTM0YjkzYTQ5NTciLCJoY2MiOiJVUyIsInR5cGUiOiJhdCIsImlhdCI6MTUwMzU0MDQzMH0.Dbo0HYai2wlNHawkC8VYchtu2l-zbhsvAdfRKQ2lTkHIYJ9Z0j3GwqZ9WR8S_lQtXH48lHaFmZQ7PbuXCpGDg4AdI0OAsfKNT0NdPW8_NmQdthgKON0fOF7ocuL9zM_ZOxvMjA0cwHyPQ7FAmynvBpM_RvJV4RFpBrTxW6vVr0M";
    private static final String EDUCATIONAL_GOAL = "2e66edf0-be10-43f9-9ecc-6a0de14a46bb";
    private static final String SOURCE = "revel-ilp-qac.pearson.com";
    private static final String LEARNING_CONTEXT_ID = "596f000ce4b0c2226c534d7d";
    private static final String LEARNING_RESOURCE_ID = EDUCATIONAL_GOAL;
    private static final String USER_ID = "ffffffff57aa11f1e4b0b97ff4a90c63";
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,ChatActivity.class);
        shimmer = (ShimmerFrameLayout) findViewById(R.id.shimmerlayout);
        shimmer_two = (ShimmerFrameLayout) findViewById(R.id.shimmerlayouttwo);
        shimmer.startShimmerAnimation();
        shimmer_two.startShimmerAnimation();
        getAssessmentAPIData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AssessmentObject>() {
                    @Override
                    public void accept(AssessmentObject assessmentObject) throws Exception {
                        Log.i(TAG , assessmentObject.toString());
                        updateText(assessmentObject.getDescription());
//
                    }
                });
        getActivityAPIData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ActivityObject>() {
                    @Override
                    public void accept(ActivityObject activityObject) throws Exception {
                        Log.i(TAG , activityObject.toString());
                        updateButtonText(activityObject.getMode());
//
                    }
                });


    }
    public void updateText(String text) {
        shimmer.setVisibility(View.GONE);
        shimmer_two.setVisibility(View.GONE);
        TextView textView = (TextView) findViewById(R.id.description_view);
        textView.setText(text);
    }
    public void updateButtonText(String text) {
        Button button = (Button) findViewById(R.id.start_button);
        if (text.equals("IN_PROGRESS")) {
            button.setText(R.string.cont);
        } else if (text.equals("START")){
            button.setText(R.string.begin);
        }
    }

    public void startChat(View view) {
        startActivity(intent);
    }
    public Observable<AssessmentObject> getAssessmentAPIData() {
        if (assessmentRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient
                    .addInterceptor(logging)
                    .addInterceptor(new AuthInterceptor(TOKEN));
            assessmentRetrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL_ASSESSMENT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        AssessmentApiService assessmentApiService = assessmentRetrofit.create(AssessmentApiService.class);
        return assessmentApiService.fetchAssessmentInfo(EDUCATIONAL_GOAL);
    }

    public Observable<ActivityObject> getActivityAPIData() {
        if (activityRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient
                    .addInterceptor(logging)
                    .addInterceptor(new AuthInterceptor(TOKEN));
            activityRetrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL_ACTIVITY)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ActivityApiService activityApiService = activityRetrofit.create(ActivityApiService.class);
        return activityApiService.fetchActivityInfo(SOURCE, LEARNING_CONTEXT_ID, LEARNING_RESOURCE_ID, USER_ID);
    }
}
