package com.akeem.instructor.home.upload;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityDocumentsUploadBinding;

public class DocumentsUpload extends AppCompatActivity {
    private ActivityDocumentsUploadBinding binding;
    private DocumentsUploadHandler handler;
    private DocViewModel dvm;
    private ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), o -> {
        if(o == null){return;}
        if(handler.uriExist(o.toString())){
            new AlertDialog.Builder(this)
                    .setMessage("You Already Upload This File")
                    .setPositiveButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    }).show();
            return;
        }
        handler.setUri(o);
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_upload);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_documents_upload);
        dvm = new ViewModelProvider(this).get(DocViewModel.class);
        binding.setUpload(dvm.getUpload());
        handler = new DocumentsUploadHandler(dvm,this,binding);
        binding.setHandler(handler);


        binding.backBtn.setOnClickListener(v -> finish());
        binding.docPick.setOnClickListener(v -> launcher.launch("application/pdf"));

    }
}