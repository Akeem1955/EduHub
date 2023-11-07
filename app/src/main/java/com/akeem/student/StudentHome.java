package com.akeem.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityStudentHomeBinding;
import com.akeem.student.analytic.StudentAnalytic;
import com.akeem.student.home.HomeFragment;
import com.akeem.student.parser.Student;
import com.akeem.student.resources.ResourcesFragment;
import com.akeem.student.result.StudentResult;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

public class StudentHome extends AppCompatActivity {
    private ActivityStudentHomeBinding binding;
    private FragmentManager manager;
    private List<Fragment> fragments;
    private StudentViewModel model;
    private HomeHandler handler;
    public static Student Istudent = new Student();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_home);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_student_home);
        manager = getSupportFragmentManager();
        model = new ViewModelProvider(this).get(StudentViewModel.class);
        fragments = Arrays.asList(new HomeFragment(model,this),
                new ResourcesFragment(model, this),
                new StudentResult(model,getIntent().getStringExtra("id")),
                new StudentAnalytic(model,getIntent().getStringExtra("id")));
        manager.beginTransaction().replace(R.id.frag_view,fragments.get(0)).commitNow();
        handler = new HomeHandler(this,manager,fragments,binding);
        binding.setHandler(handler);
        model.getStudent(getIntent().getStringExtra("id")).observe(this, student -> {
            if (student != null){
                Istudent = new Student();
            }else {
                Snackbar.make(binding.getRoot(),"Network Error Unable to Connect the internet..",Snackbar.LENGTH_LONG).show();
            }
        });
    }
}