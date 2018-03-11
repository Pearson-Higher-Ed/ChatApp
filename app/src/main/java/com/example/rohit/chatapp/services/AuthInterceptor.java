package com.example.rohit.chatapp.services;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rohit on 8/10/17.
 */

public class AuthInterceptor implements Interceptor {
    private String token = "";
    public AuthInterceptor(String token) {
        this.token = token;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder().addHeader("Authorization", token).build();
        return chain.proceed(request);
    }
}
