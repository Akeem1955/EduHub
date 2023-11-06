package com.akeem.eduhub;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.akeem.instructor.InsrtuctorHome;

public class MainActivity extends AppCompatActivity {
    static AppViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new AppViewModel(getApplication());
        Intent intent = new Intent(this, InsrtuctorHome.class);
        startActivity(intent);

    }
}