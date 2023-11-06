package com.akeem.instructor.home.upload;

import android.icu.util.Calendar;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class DocViewModel extends ViewModel {
    private OnFailureListener failureListener;
    private OnSuccessListener<UploadTask.TaskSnapshot> successListener;

    public DocViewModel() {
        failureListener = e -> {

        };
        successListener= taskSnapshot -> {

        };
    }

    private final StorageReference reference = FirebaseStorage.getInstance().getReference("Materials");
    private final Upload upload = new Upload();
    public void uploadToFirebase(InputStream inputStream){
        StorageReference child = reference.child(Calendar.getInstance().getTime().toString());
        UploadTask task = child.putStream(inputStream);
        task.addOnFailureListener(failureListener).addOnSuccessListener(successListener);
    }

    public Upload getUpload() {
        return upload;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        successListener = null;
        failureListener = null;
    }
}
