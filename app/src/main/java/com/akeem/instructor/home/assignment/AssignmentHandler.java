package com.akeem.instructor.home.assignment;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.airbnb.lottie.LottieAnimationView;
import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityAssignmentStudentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AssignmentHandler {
    private final Dialog sucess;
    private final Dialog failed;
    private final Dialog loading;
    private final Assign assign;
    private final AssignViewModel asm;
    private final Context context;
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Assignments");
    private final LifecycleOwner owner;
    private final ActivityAssignmentStudentBinding binding;

    public AssignmentHandler(Assign assign, AssignViewModel asm, Context context, LifecycleOwner owner, ActivityAssignmentStudentBinding binding) {
        this.assign = assign;
        this.asm = asm;
        this.context = context;
        this.owner = owner;
        this.binding = binding;
        sucess = new Dialog(context);
        failed = new Dialog(context);
        loading= new Dialog(context);

    }

    public void onAssign(View view){
        View v = LayoutInflater.from(context).inflate(R.layout.load_lay,null);
        loading.setContentView(v);
        loading.setCancelable(false);
        if(assign.getConcentrate() == null
                || assign.getInstruction() == null
                || assign.getQuestion_a() == null
                || assign.getTopic() == null)
        {
            Toast.makeText(context,"Required Field Must Not be Empty...",Toast.LENGTH_SHORT).show();
            return;
        }
        if(assign.getConcentrate().isEmpty()
                || assign.getInstruction().isEmpty()
                || assign.getQuestion_a().isEmpty()
                || assign.getTopic().isEmpty())
        {
            Toast.makeText(context,"Required Field Must Not be Empty...",Toast.LENGTH_SHORT).show();
            return;
        }
        loading.show();
        asm.setAssignment(assign).observe(owner, s -> {
            //Stoploading and send msg
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
