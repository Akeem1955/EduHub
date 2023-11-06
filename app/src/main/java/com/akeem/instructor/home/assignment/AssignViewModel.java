package com.akeem.instructor.home.assignment;

import static com.akeem.background.EduHubHandler.ASSIGNMENT_UPDATE;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.akeem.background.EduHubProcessor;
import com.akeem.retrofit.interfaces.NotifyStudent;
import com.akeem.retrofit.parserclass.NotificationPayload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssignViewModel extends ViewModel {
    private final Assign assign = new Assign();
    private OnSuccessListener<Void> sucess;
    private OnFailureListener failureListener;
    private final MutableLiveData<String> liveData = new MutableLiveData<>(null);

    public AssignViewModel() {
        sucess = unused -> {
            Message message = Message.obtain(EduHubProcessor.getInstance().getHandler());
            Bundle bundle = new Bundle();
            bundle.putString("title",assign.getTopic());
            bundle.putString("msg","New Asssignment From Instructor");
            message.what=ASSIGNMENT_UPDATE;
            message.setData(bundle);
            message.sendToTarget();
            liveData.postValue("sucess");
        };
        failureListener = e -> liveData.postValue("failed");
    }

    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Assignments");
    public MutableLiveData<String> setAssignment(Assign data)
    {
        DatabaseReference child = reference.push();
        child.setValue(data).addOnSuccessListener(sucess).addOnFailureListener(failureListener);
        return liveData;
    }

    public Assign getAssign() {
        return assign;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        sucess = null;
        failureListener = null;
        EduHubProcessor.getInstance().getHandler().removeCallbacksAndMessages(null);
    }

}
