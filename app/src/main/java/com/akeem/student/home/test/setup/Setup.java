package com.akeem.student.home.test.setup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.akeem.background.EduHubProcessor;
import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivitySetupBinding;
import com.akeem.retrofit.interfaces.SendChat;
import com.akeem.retrofit.parserclass.GptInput;
import com.akeem.retrofit.parserclass.GptMessage;
import com.akeem.retrofit.parserclass.GptResponse;
import com.akeem.retrofit.parserclass.Question;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Setup extends AppCompatActivity {
    private Dialog loading;
    private Dialog failed;
    public static Question questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        loading = new Dialog(this);
        loading.setContentView(R.layout.load_lay);
        loading.setCancelable(false);
        failed = new Dialog(this);
        failed.setContentView(R.layout.failed_lay);
        TextView failure = failed.findViewById(R.id.failed_msg);
        failure.setText(R.string.network_error_please_check_your_internet_connection_and_try_again);
        ActivitySetupBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_setup);
        binding.instructionGoes.setText("");
        GptMessage system = new GptMessage();
        system.setRole("system");
        system.setContent("you are a examiner that set high end objective question and answer in form of json format that my app will consume i don't want any other comment only the json");
        GptMessage user = new GptMessage();
        user.setRole("user");
        StringBuilder builder = new StringBuilder();
        //set 10 question and anwser on thermodynamics and area of concentrate is isothermal
        //obj
        builder.append("set ");
        builder.append(getIntent().getStringExtra("no_of_question"));
        builder.append(" objective question and answer on ");
        builder.append(getIntent().getStringExtra("topic"));
        builder.append(" and area of concentrate is ");
        builder.append(getIntent().getStringExtra("concentrate"));
        user.setContent(builder.toString());
        GptInput input = new GptInput();
        input.setModel("gpt-3.5-turbo");
        input.getMessages().add(system);
        input.getMessages().add(user);

        binding.button12.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.chat_url))
                    .addConverterFactory(GsonConverterFactory.create()).build();
            SendChat testGenerator = retrofit.create(SendChat.class);
            Call<GptResponse> call = testGenerator.getchat(input);
            loading.show();
            EduHubProcessor.handler.post(() -> {
                Handler handler = new Handler(Looper.getMainLooper());
                try {

                    Response<GptResponse> response = call.execute();
                    if(response.isSuccessful() && response.body() != null){
                        Gson gson = new Gson();
                        questions = gson.fromJson(response.body().getChoices().get(0).getMessage().getContent(),Question.class);
                        handler.post(() -> {
                            loading.dismiss();
                            Intent intent = new Intent(this, TestScreen.class);
                            intent.putExtra("duration",getIntent().getStringExtra("duration"));
                            intent.putExtra("score_per_question",getIntent().getStringExtra("score_per_question"));
                            if(questions != null){
                                startActivity(intent);
                            }else{
                                Snackbar.make(binding.getRoot(),"Unable to Load Test Try Again Later...",Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                    else {
                        handler.post(() -> {
                            loading.dismiss();
                            failed.show();
                        });
                    }
                } catch (Exception e) {
                    handler.post(() -> {
                        loading.dismiss();
                        failed.show();
                    });
                }
            });
        });
        //duration
        //topic
        //concentrate
        //no of question
        //score_per_question
        //instruction
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EduHubProcessor.handler.removeCallbacksAndMessages(null);
    }
}