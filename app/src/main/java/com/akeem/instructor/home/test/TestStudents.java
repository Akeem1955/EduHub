package com.akeem.instructor.home.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityTestStudentsBinding;


public class TestStudents extends AppCompatActivity {
    private ActivityTestStudentsBinding binding;
    private TestHandler handler;
    private TestViewModel tvm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_students);
        tvm = new ViewModelProvider(this).get(TestViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_test_students);
        binding.setTest(tvm.getTest());
        handler = new TestHandler(this,binding,tvm.getTest(),tvm,this);
        binding.setHandler(handler);

    }
}