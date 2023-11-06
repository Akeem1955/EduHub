package com.akeem.instructor.home.test;

import static com.akeem.background.EduHubHandler.ASSIGNMENT_UPDATE;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.akeem.background.EduHubProcessor;
import com.akeem.instructor.home.assignment.Assign;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestViewModel extends ViewModel {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tests");
    private Test test = new Test();
    private MutableLiveData<String> liveData;
    private OnSuccessListener<Void> successListener;
    private OnFailureListener failureListener;


    public TestViewModel() {
        successListener = unused -> {
            Message message = Message.obtain(EduHubProcessor.getInstance().getHandler());
            Bundle bundle = new Bundle();
            bundle.putString("title",test.getTopic());
            bundle.putString("msg","New Test From Instructor");
            message.what=ASSIGNMENT_UPDATE;
            message.setData(bundle);
            message.sendToTarget();
            liveData.postValue("sucess");
        };
        failureListener = e -> liveData.postValue("failed");
    }

    public MutableLiveData<String> setAssignment()
    {
        DatabaseReference child = reference.push();
        child.setValue(test).addOnSuccessListener(successListener).addOnFailureListener(failureListener);
        return liveData;
    }
    public Test getTest() {
        return test;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        EduHubProcessor.getInstance().getHandler().removeCallbacksAndMessages(null);
        successListener = null;
        failureListener = null;
    }
}
