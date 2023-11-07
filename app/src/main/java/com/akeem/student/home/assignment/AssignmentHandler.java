package com.akeem.student.home.assignment;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.akeem.background.EduHubProcessor;
import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityAssignmentScreenBinding;
import com.akeem.instructor.home.assignment.Assign;
import com.akeem.retrofit.interfaces.SendChat;
import com.akeem.retrofit.parserclass.GptInput;
import com.akeem.retrofit.parserclass.GptMessage;
import com.akeem.retrofit.parserclass.GptResponse;
import com.akeem.student.StudentHome;
import com.akeem.student.StudentViewModel;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AssignmentHandler {
    private Dialog loading;
    private Dialog failed;
    private Context context;
    private AlertDialog instruction;
    private Assign assign;
    private ActivityAssignmentScreenBinding binding;
    private StudentViewModel svm;
    private String lecturer_id;

    public AssignmentHandler(Context context, Assign assign, ActivityAssignmentScreenBinding binding, StudentViewModel svm,String lecturer_id) {
        this.context = context;
        this.assign = assign;
        instruction = new AlertDialog.Builder(context)
                .setMessage(assign.getInstruction() + "\n" + "Note If You Minimize or Exist the App It will be Submit Automatically")
                .create();
        loading =new Dialog(context);
        loading.setContentView(R.layout.load_lay);
        loading.setCancelable(false);
        failed = new Dialog(context);
        failed.setContentView(R.layout.failed_lay);
        TextView failed_msg = failed.findViewById(R.id.failed_msg);
        failed_msg.setText(R.string.check_your_internet_connection_and_try_again);
        this.svm = svm;
        this.lecturer_id = lecturer_id;
    }

    public void onSubmit(View view){
        String[] ansque = new String[0];
        if(assign.getQuestion_c() == null && assign.getQuestion_b() == null){
            ansque = new String[]{"Question: " + assign.getQuestion_a() + ", Answer: " + binding.questionAEdit.getText().toString()};
        } else if (assign.getQuestion_b() != null && assign.getQuestion_c() != null) {
            ansque = new String[]{
                    "QuestionA: " + assign.getQuestion_a() + ", AnswerA: " + binding.questionAEdit.getText().toString(),
                    "QuestionB: " + assign.getQuestion_b() + ", AnswerB: " + binding.questionBEdit.getText().toString(),
                    "QuestionC: " + assign.getQuestion_c() + ", AnswerC: " + binding.questionCEdit.getText().toString(),
            };
        } else if (assign.getQuestion_c() != null) {
            ansque = new String[]{
                    "QuestionA: " + assign.getQuestion_a() + ", AnswerA: " + binding.questionAEdit.getText().toString(),
                    "QuestionB: " + assign.getQuestion_c() + ", AnswerB: " + binding.questionCEdit.getText().toString(),
            };
        } else if (assign.getQuestion_b() != null) {
            ansque = new String[]{
                    "QuestionA: " + assign.getQuestion_a() + ", AnswerA: " + binding.questionAEdit.getText().toString(),
                    "QuestionB: " + assign.getQuestion_b() + ", AnswerB: " + binding.questionBEdit.getText().toString()
            };
        }

        GptMessage sysMessage = new GptMessage();
        sysMessage.setContent("You are A Assignment Grader You Check the Question with the Answer reply And mark over 10\n" +
                "no comment only score,For example: 5 finish");
        sysMessage.setRole("system");
        GptMessage student_script = new GptMessage();
        student_script.setRole("user");
        StringBuilder builder = new StringBuilder();
        for (String s : ansque) {
            builder.append(s).append("\n");
        }
        student_script.setContent(builder.toString());

        GptInput input = new GptInput();
        input.getMessages().add(sysMessage);
        input.getMessages().add(student_script);
        input.setModel("gpt-3.5-turbo");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.chat_url))
                .addConverterFactory(GsonConverterFactory.create()).build();
        SendChat sendChat = retrofit.create(SendChat.class);
        Call<GptResponse> call = sendChat.getchat(input);
        EduHubProcessor.handler.post(() -> {
            Handler handler = new Handler(Looper.getMainLooper());
            try {
                Response<GptResponse> response =call.execute();
                if(response.isSuccessful()){
                    handler.post(() -> {
                        loading.dismiss();
                    });
                    if(response.body() != null){
                        Integer mark =Integer.parseInt(response.body().getChoices().get(0).getMessage().getContent());
                        HashMap<String, Integer> score = StudentHome.Istudent.getAssignment_score();
                        HashMap<String, Integer> t_score = StudentHome.Istudent.getTotal_score();
                        score.put(lecturer_id, mark);
                        t_score.put(lecturer_id,mark);
                        StudentHome.Istudent.setAssignment_score(score);
                        StudentHome.Istudent.setTotal_score(t_score);
                        svm.updateStudent(StudentHome.Istudent);
                    }
                }
            } catch (Exception e) {
                handler.post(() -> {
                    loading.dismiss();
                    failed.show();
                });
            }
        });


    }

    public void onInstructionClick(View view){
        instruction.show();
    }
}
