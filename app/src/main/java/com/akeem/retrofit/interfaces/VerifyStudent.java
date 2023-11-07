package com.akeem.retrofit.interfaces;

import com.akeem.retrofit.parserclass.Student;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface VerifyStudent {
    @POST("/verify_student")
    Call<Student> notifyStudent(@Body Student student);
}
