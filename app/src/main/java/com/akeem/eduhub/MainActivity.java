package com.akeem.eduhub;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.akeem.background.EduHubProcessor;
import com.akeem.eduhub.databinding.ActivityMainBinding;
import com.akeem.instructor.InsrtuctorHome;
import com.akeem.retrofit.interfaces.VerifyLecturer;
import com.akeem.retrofit.interfaces.VerifyStudent;
import com.akeem.retrofit.parserclass.Instructor;
import com.akeem.retrofit.parserclass.Student;
import com.akeem.student.StudentHome;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    static AppViewModel model;
    Dialog failed;
    Dialog loading;
    Dialog sucess;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        failed=new Dialog(this);
        TextView err = failed.findViewById(R.id.failed_msg);
        err.setText("Unable to establish Network Connection");
        loading=new Dialog(this);
        sucess=new Dialog(this);
        model = new AppViewModel(getApplication());
        ActivityMainBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.login.setOnClickListener(v -> {
            if(binding.id.getText().toString().equals("") || binding.password.getText().toString().equals("")){
                Snackbar.make(binding.getRoot(),"All Field Are Required...",Snackbar.LENGTH_SHORT).show();
                return;
            }

            if(binding.who.getCheckedRadioButtonId() == R.id.lecturer){
                Instructor instructor = new Instructor();
                instructor.setId(binding.id.getText().toString());
                instructor.setPassword(binding.password.getText().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(getResources().getString(R.string.edu_hub_url))
                        .build();
                VerifyLecturer verify = retrofit.create(VerifyLecturer.class);
                Call<Instructor> call = verify.verifyLecturer(instructor);
                loading.show();
                EduHubProcessor.handler.post(() -> {
                    Handler handler = new Handler(Looper.getMainLooper());
                    try {
                        Response<Instructor> response = call.execute();
                        handler.post(() -> {
                            if(response.isSuccessful() && response.body() != null){
                                loading.dismiss();
                                Intent intent = new Intent(this, InsrtuctorHome.class);
                                intent.putExtra("id",response.body().getId());
                                startActivity(intent);
                                finish();
                            }else {
                                handler.post(() -> failed.show());
                            }
                        });
                    } catch (Exception e) {
                        handler.post(() -> failed.show());
                    }
                });
            }
            else if (binding.who.getCheckedRadioButtonId() == R.id.student) {
                Student student = new Student();
                student.setMatric_no(binding.id.getText().toString());
                student.setPassword(binding.password.getText().toString());
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(getResources().getString(R.string.edu_hub_url))
                        .build();
                VerifyStudent verify = retrofit.create(VerifyStudent.class);
                Call<Student> call = verify.notifyStudent(student);
                loading.show();
                EduHubProcessor.handler.post(() -> {
                    Handler handler = new Handler(Looper.getMainLooper());
                   try {
                       Response<Student> response = call.execute();
                       handler.post(() -> {
                           if(response.isSuccessful() && response.body() != null){
                               loading.dismiss();
                               Intent intent = new Intent(this, StudentHome.class);
                               intent.putExtra("id",response.body().getMatric_no());
                               startActivity(intent);
                               finish();
                           }else {
                               handler.post(() -> failed.show());
                           }
                       });
                   }catch (Exception e){
                       handler.post(() -> failed.show());
                   }
                });
            }

        });


//        Intent intent = new Intent(this, InsrtuctorHome.class);
//        startActivity(intent);
    }
}