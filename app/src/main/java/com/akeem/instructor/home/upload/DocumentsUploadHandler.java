package com.akeem.instructor.home.upload;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.akeem.eduhub.databinding.ActivityDocumentsUploadBinding;
import com.ashiri.upload_uri.UploadRepo;
import com.ashiri.upload_uri.Uris;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DocumentsUploadHandler {
    public Uri uri;
    private DocViewModel viewModel;
    private Context context;
    private ActivityDocumentsUploadBinding binding;
    private UploadRepo repo;

    public DocumentsUploadHandler(DocViewModel viewModel, Context context, ActivityDocumentsUploadBinding binding) {
        this.viewModel = viewModel;
        this.context = context;
        this.binding = binding;
        repo = new UploadRepo(context);
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

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void addUri(String uri){
        Uris uris = new Uris();
        uris.setUri(uri);
        repo.insert(uris);
    }
    public boolean uriExist(String uri){
        return repo.exit(uri);
    }





}
