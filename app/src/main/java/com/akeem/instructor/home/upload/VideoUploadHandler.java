package com.akeem.instructor.home.upload;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.akeem.eduhub.databinding.ActivityDocumentsUploadBinding;
import com.akeem.eduhub.databinding.ActivityVideoUploadBinding;
import com.ashiri.upload_uri.UploadRepo;
import com.ashiri.upload_uri.Uris;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class VideoUploadHandler {
    private VidViewMode viewModel;
    private Context context;
    public Uri uri;
    private ActivityVideoUploadBinding binding;
    private final UploadRepo repo ;


    public VideoUploadHandler(VidViewMode viewModel, Context context, ActivityVideoUploadBinding binding) {
        this.viewModel = viewModel;
        this.context = context;
        this.binding = binding;
        this.repo = new UploadRepo(context.getApplicationContext());
    }

    public void onUpload(View view){
        if(binding.getUpload().getMaterial_info() == null || binding.getUpload().getTitle() == null){
            Snackbar.make(binding.getRoot(),"All Field Are Required ", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (binding.getUpload().getTitle().isEmpty()||binding.getUpload().getMaterial_info().isEmpty()) {
            Snackbar.make(binding.getRoot(),"All Field Are Required ", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(uri == null){
            Snackbar.make(binding.getRoot(),"Unable to Pick Documents Try Picking Again ",Snackbar.LENGTH_LONG).show();
            return;
        }
        InputStream inputStream;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            Snackbar.make(binding.getRoot(),"Unable to Pick Documents Try Picking Again ",Snackbar.LENGTH_LONG).show();
            return;
        }
        viewModel.uploadToFirebase(inputStream);
    }

    public void addUri(String uri){
        Uris uris = new Uris();
        uris.setUri(uri);
        repo.insert(uris);
    }
    public boolean uriExist(String uri){
        return repo.exit(uri);
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
