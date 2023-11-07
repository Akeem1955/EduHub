package com.akeem.retrofit.interfaces;

import com.akeem.retrofit.parserclass.Instructor;
import com.akeem.retrofit.parserclass.NotificationPayload;
import com.akeem.retrofit.parserclass.Student;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface VerifyLecturer {
    @POST("/verify_instructor")
    Call<Instructor> verifyLecturer(@Body Instructor instructor);
}
