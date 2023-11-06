package com.akeem.instructor.home.test;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.airbnb.lottie.LottieAnimationView;
import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityTestStudentsBinding;

public class TestHandler {
    private final Dialog sucess;
    private final Dialog failed;
    private final Dialog loading;
    private Context context;
    private ActivityTestStudentsBinding binding;
    private Test test;
    private TestViewModel tvm;
    private LifecycleOwner owner;

    public TestHandler(Context context, ActivityTestStudentsBinding binding, Test t, TestViewModel tvm, LifecycleOwner owner) {
        this.context = context;
        this.binding = binding;
        this.test = t;
        this.tvm = tvm;
        sucess = new Dialog(context);
        failed = new Dialog(context);
        loading= new Dialog(context);
        this.owner = owner;
    }



    public void onTested(View view){
        View v = LayoutInflater.from(context).inflate(R.layout.load_lay,null);
        loading.setContentView(v);
        loading.setCancelable(false);
        if(test.getConcentrate() == null
                || test.getDuration() == null
                ||test.getTopic() == null
                ||test.getNo_of_question() == null
                ||test.getScore_per_question() == null){
            Toast.makeText(context,"Required Field Are Empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(test.getConcentrate().isEmpty()
                || test.getDuration().isEmpty()
                ||test.getTopic().isEmpty()
                ||test.getNo_of_question().isEmpty()
                ||test.getScore_per_question().isEmpty()){
            Toast.makeText(context,"Required Field Are Empty",Toast.LENGTH_SHORT).show();
            return;
        }
        loading.show();
        tvm.setAssignment().observe(owner, s -> {
            if(s != null){
                //load stop
                loading.dismiss();
                if(s.equals("failed")){
                    //fail start
                    View fail = LayoutInflater.from(context).inflate(R.layout.failed_lay,null,false);
                    TextView tv = fail.findViewById(R.id.failed_msg);
                    tv.setText(R.string.unable_to_set_assignment_check_your_internet_connection_and_try_again);
                    failed.setContentView(fail);
                    failed.show();
                } else if (s.equals("sucess")) {
                    View sucessD = LayoutInflater.from(context).inflate(R.layout.sucess_lay,null,false);
                    LottieAnimationView lav =sucessD.findViewById(R.id.sucess);
                    lav.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(@NonNull Animator animation) {
                            sucess.dismiss();
                        }

                        @Override
                        public void onAnimationEnd(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(@NonNull Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(@NonNull Animator animation) {

                        }
                    });
                    sucess.setContentView(sucessD);
                    sucess.show();
                }
            }
        });

    }
}
