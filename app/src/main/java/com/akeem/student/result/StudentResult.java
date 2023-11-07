package com.akeem.student.result;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.FragmentStudentResultBinding;
import com.akeem.student.StudentHome;
import com.akeem.student.StudentViewModel;
import com.akeem.student.parser.Student;
import com.google.android.material.snackbar.Snackbar;

public class StudentResult extends Fragment {
    private StudentViewModel svm;
    private String student_id;
    private FragmentStudentResultBinding binding;
    private Dialog loading;
    private String[] dummy ={};
    public StudentResult(StudentViewModel svm, String student_id) {
        this.svm = svm;
        this.student_id = student_id;
        if(StudentHome.Istudent.getMatric_no() == null){
            getStudent(student_id);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_student_result, container,false);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(StudentHome.Istudent.getTotal_score() != null){
                }
                if(StudentHome.Istudent.getAssignment_score() != null){}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
    private void getStudent(String student_id){
        svm.getStudent(student_id).observe(this, student -> {
            if (student != null){
                StudentHome.Istudent=student;
            }
            else {
                if(loading != null){
                    loading.dismiss();
                    Snackbar.make(binding.getRoot(),"Network Error Unable to Connect the internet..",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loading.show();
        if(StudentHome.Istudent.getMatric_no() == null){
            getStudent(student_id);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loading = new Dialog(context);
        loading.setContentView(R.layout.load_lay);
        loading.setCancelable(false);
    }
}