package com.akeem.instructor.resources;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.akeem.instructor.parser.Comments;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class VideoViewModel extends ViewModel {
    long current_duration;
    public VideoViewModel() {
    }

    public long getCurrent_duration() {
        return current_duration;
    }

    public void setCurrent_duration(long current_duration) {
        this.current_duration = current_duration;
    }
}
