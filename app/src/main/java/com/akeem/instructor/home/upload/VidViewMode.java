package com.akeem.instructor.home.upload;

import android.content.Context;
import android.icu.util.Calendar;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;

public class VidViewMode extends ViewModel {
    private InputStream inputStream;
    private final Upload upload = new Upload();
    private final OnFailureListener failureListener;
    private final OnSuccessListener<UploadTask.TaskSnapshot> successListener;
    private final StorageReference reference = FirebaseStorage.getInstance().getReference("Materials");

    public VidViewMode() {
        failureListener = e -> {
            try {
                inputStream.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        };
        successListener= taskSnapshot -> {
            try {
                inputStream.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        };
    }

    public void uploadToFirebase(InputStream inputStream){
        this.inputStream = inputStream;
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCustomMetadata("description", upload.getTitle() +"\n" +upload.getMaterial_info())
                .setContentType("video/*")
                .build();

        StorageReference child = reference.child(Calendar.getInstance().getTime().toString());
        UploadTask task = child.putStream(inputStream,metadata);
        task.addOnFailureListener(failureListener).addOnSuccessListener(successListener);
    }


    public Upload getUpload() {
        return upload;
    }
}
