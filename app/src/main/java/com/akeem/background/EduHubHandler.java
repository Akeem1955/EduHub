package com.akeem.background;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.akeem.retrofit.interfaces.NotifyStudent;
import com.akeem.retrofit.parserclass.NotificationPayload;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EduHubHandler extends Handler {

    public final static int ASSIGNMENT_UPDATE=1;
    public final static int TASK_B=2;
    public final static int TASK_C=3;
    public final static int TASK_D=4;

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case ASSIGNMENT_UPDATE:
                assignmentUpdate(msg.getData().getString("title"),msg.getData().getString("msg"));
                break;
        }
    }

    private void assignmentUpdate(String title, String msg){
        NotificationPayload payload = new NotificationPayload();
        payload.setMsg(msg);
        payload.setTitle(title);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ca8e–102–91–4–174.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        NotifyStudent student =retrofit.create(NotifyStudent.class);
        Call<String> call = student.notifyStudent(payload);
        try {
            Response<String> r = call.execute();
            if (r.isSuccessful()){
                Log.d(r.body(),"Sucess");
            }
            else {
                System.out.println(r.code() + " Err Happened");
            }
        } catch (IOException e) {
            Log.d("Student Notify Failed",e.toString());
        }
    }

    public EduHubHandler(@NonNull Looper looper) {
        super(looper);
    }
}
