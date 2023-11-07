package com.akeem.student.home.test.setup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.akeem.background.EduHubProcessor;
import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityTestScreenBinding;
import com.akeem.retrofit.parserclass.QuestionObj;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TestScreenHandler {
    private Context context;
    private Dialog loading;
    private int current_index = 1;
    private ActivityTestScreenBinding binding;
    int size = Setup.questions.getQuestionObjList().size();
    private List<Integer> answer = new ArrayList<>(size);
    private Integer mark;

    public TestScreenHandler(Context context, ActivityTestScreenBinding binding,String mark) {
        this.context = context;
        loading = new Dialog(context);
        loading.setContentView(R.layout.load_lay);
        loading.setCancelable(false);
        this.mark=Integer.parseInt(mark);
    }






    public void onPrev(View view){
        //first step
        if(binding.radioGroup.getCheckedRadioButtonId() == -1){
            answer.add(current_index,0);
        } else if (binding.radioGroup.getCheckedRadioButtonId() == R.id.option_a) {
            answer.add(current_index,0);
        } else if (binding.radioGroup.getCheckedRadioButtonId() == R.id.option_b) {
            answer.add(current_index,1);
        } else if (binding.radioGroup.getCheckedRadioButtonId() == R.id.option_c) {
            answer.add(current_index,2);
        } else if (binding.radioGroup.getCheckedRadioButtonId() == R.id.option_d) {
            answer.add(current_index,3);
        }
        current_index--;
        //Second step
        if(answer.get(current_index)!=null){
            if(answer.get(current_index) == 0)
            {
                binding.radioGroup.check(R.id.option_a);
            }
            else if (answer.get(current_index) == 1)
            {
                binding.radioGroup.check(R.id.option_b);
            }
            else if (answer.get(current_index) == 2)
            {
                binding.radioGroup.check(R.id.option_c);
            }
            else if (answer.get(current_index) == 3)
            {
                binding.radioGroup.check(R.id.option_d);
            }
        }

        //last step
        binding.question.setText(Setup.questions.getQuestionObjList().get(current_index).getQuestion());
        binding.optionA.setText(Setup.questions.getQuestionObjList().get(current_index).getOptions().get(0));
        binding.optionB.setText(Setup.questions.getQuestionObjList().get(current_index).getOptions().get(1));
        binding.optionC.setText(Setup.questions.getQuestionObjList().get(current_index).getOptions().get(2));
        binding.optionD.setText(Setup.questions.getQuestionObjList().get(current_index).getOptions().get(3));
        binding.remain.setText(String.format(Locale.getDefault(),  "%d/%d", current_index, size));
        if(!binding.nextText.isEnabled()){
            binding.nextText.setEnabled(true);
        }
        if(current_index <= 0){
            binding.prevTest.setEnabled(false);
        }

    }






    public void onNext(View view){
        //first step
        if(answer.get(current_index)!=null){
            if(answer.get(current_index) == 0)
            {
                binding.radioGroup.check(R.id.option_a);
            }
            else if (answer.get(current_index) == 1)
            {
                binding.radioGroup.check(R.id.option_b);
            }
            else if (answer.get(current_index) == 2)
            {
                binding.radioGroup.check(R.id.option_c);
            }
            else if (answer.get(current_index) == 3)
            {
                binding.radioGroup.check(R.id.option_d);
            }
        }
        //Second step
        if(binding.radioGroup.getCheckedRadioButtonId() == -1){
            answer.add(current_index,0);
        } else if (binding.radioGroup.getCheckedRadioButtonId() == R.id.option_a) {
            answer.add(current_index,0);
        } else if (binding.radioGroup.getCheckedRadioButtonId() == R.id.option_b) {
            answer.add(current_index,1);
        } else if (binding.radioGroup.getCheckedRadioButtonId() == R.id.option_c) {
            answer.add(current_index,2);
        } else if (binding.radioGroup.getCheckedRadioButtonId() == R.id.option_d) {
            answer.add(current_index,3);
        }

        //last step
        binding.question.setText(Setup.questions.getQuestionObjList().get(current_index).getQuestion());
        binding.optionA.setText(Setup.questions.getQuestionObjList().get(current_index).getOptions().get(0));
        binding.optionB.setText(Setup.questions.getQuestionObjList().get(current_index).getOptions().get(1));
        binding.optionC.setText(Setup.questions.getQuestionObjList().get(current_index).getOptions().get(2));
        binding.optionD.setText(Setup.questions.getQuestionObjList().get(current_index).getOptions().get(3));
        binding.remain.setText(String.format(Locale.getDefault(),  "%d/%d", current_index, size));
        if(!binding.prevTest.isEnabled()){
            binding.prevTest.setEnabled(true);
        }
        current_index++;
        if(current_index >= size){
            binding.nextText.setEnabled(false);
            binding.submitTest.setEnabled(true);
        }

    }






    public void onSubmit(View view){
        loading.show();
        EduHubProcessor.handler.post(() -> {
            Handler handler =new Handler(Looper.getMainLooper());
            int score = 0;
            int i = 0;
            for (QuestionObj q:Setup.questions.getQuestionObjList()) {
                if(  q.getOptions().get(answer.get(i)).equals(q.getAnswer()) ){
                    score++;
                }
                i++;
            }
            int finalScore = score;
            handler.post(() -> {
                loading.dismiss();
                Intent intent=new Intent(context, TestEnded.class);
                intent.putExtra("Score", finalScore *mark);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            });
        });

    }
}
