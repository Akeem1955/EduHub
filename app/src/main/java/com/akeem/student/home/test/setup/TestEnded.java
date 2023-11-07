package com.akeem.student.home.test.setup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityTestEndedBinding;
import com.akeem.student.StudentHome;

public class TestEnded extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ended);
        ActivityTestEndedBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_test_ended);

        binding.gotScore.setText(getIntent().getIntExtra("Score",0));
        binding.goHome.setOnClickListener(v -> {
            Intent intent=new Intent(this, StudentHome.class);
            startActivity(intent);
            finish();
        });
    }
}