package com.akeem.retrofit.interfaces;

import com.akeem.retrofit.parserclass.GptMessage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;


public interface SendChat {
    @Headers({
            "User-Agent: Retrofit-Sample-App",
            "Content-type"
    })
    @POST("chat/completions")
    Call<GptMessage> getchat();
}
