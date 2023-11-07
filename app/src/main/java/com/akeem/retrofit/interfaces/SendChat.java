package com.akeem.retrofit.interfaces;

import com.akeem.retrofit.parserclass.GptInput;
import com.akeem.retrofit.parserclass.GptMessage;
import com.akeem.retrofit.parserclass.GptResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;


public interface SendChat {
    @Headers({
            "User-Agent: Retrofit-Sample-App",
            "Content-type: application/json",
            "Authorization: Bearer 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"
    })
    @POST("chat/completions")
    Call<GptResponse> getchat(@Body GptInput input);
}
