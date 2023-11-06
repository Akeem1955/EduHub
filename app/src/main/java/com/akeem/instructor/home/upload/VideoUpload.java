package com.akeem.instructor.home.upload;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import com.akeem.background.EduHubProcessor;
import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityDocumentsUploadBinding;
import com.akeem.eduhub.databinding.ActivityVideoUploadBinding;

public class VideoUpload extends AppCompatActivity {
    private ActivityVideoUploadBinding binding;
    private VideoUploadHandler handler;
    private VidViewMode dvm;
    private Runnable runnable;
    private ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), o -> {
        if(o == null){return;}
        runnable = () -> {
            if(handler.uriExist(o.toString())){
                new AlertDialog.Builder(this)
                        .setMessage("You Already Upload This Video")
                        .setPositiveButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        }).show();
                return;
            }
            handler.setUri(o);
        };
        EduHubProcessor.getInstance().getHandler().post(runnable);
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_upload);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_upload);
        dvm = new ViewModelProvider(this).get(VidViewMode.class);
        binding.setUpload(dvm.getUpload());
        handler = new VideoUploadHandler(dvm,this,binding);
        binding.setHandler(handler);


        binding.backBtn.setOnClickListener(v -> finish());
        binding.vidPick.setOnClickListener(v -> launcher.launch("video/*"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EduHubProcessor.getInstance().getHandler().removeCallbacksAndMessages(null);
    }
}