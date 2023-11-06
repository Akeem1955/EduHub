package com.akeem.instructor.home.schedule_class;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClassViewModel extends ViewModel {
    private final DatabaseReference reference   = FirebaseDatabase.getInstance().getReference("Schedules");
    private DatabaseReference child;

    private  ValueEventListener listener;
    private List<String> schedules = new ArrayList<>();
    private final MutableLiveData<List<String>> schedule_live = new MutableLiveData<>(schedules);
    ClassViewModel(){
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<String>> indicator = new GenericTypeIndicator<List<String>>() {};
                schedules = snapshot.getValue(indicator);
                schedule_live.postValue(schedules);
                System.out.println(schedules.isEmpty() + " ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }
    public MutableLiveData<List<String>> getClassSchedule(String location){

        child = reference   .child(location);
        child.addValueEventListener(listener);
        System.out.println(schedules.isEmpty() + " is Empty" );
        return schedule_live;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        child.removeEventListener(listener);
        listener=null;

    }
}
