package com.akeem.retrofit.interfaces;

import com.akeem.retrofit.parserclass.NotificationPayload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NotifyStudent {
    @POST("/notify_student")
    Call<String> notifyStudent(@Body NotificationPayload payload);
}
