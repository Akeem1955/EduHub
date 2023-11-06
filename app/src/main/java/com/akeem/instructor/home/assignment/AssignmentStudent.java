package com.akeem.instructor.home.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityAssignmentStudentBinding;

public class AssignmentStudent extends AppCompatActivity {
    private ActivityAssignmentStudentBinding binding;
    private AssignmentHandler handler;
    private AssignViewModel avm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_student);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_assignment_student);
        avm = new ViewModelProvider(this).get(AssignViewModel.class);
        binding.setAssign(avm.getAssign());
        handler = new AssignmentHandler(avm.getAssign(),avm,this,this,binding);
        binding.setHandler(handler);

    }
}