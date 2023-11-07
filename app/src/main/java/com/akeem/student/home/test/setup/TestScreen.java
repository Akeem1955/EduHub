package com.akeem.student.home.test.setup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.akeem.eduhub.R;
import com.akeem.eduhub.databinding.ActivityTestScreenBinding;

import java.util.Locale;

public class TestScreen extends AppCompatActivity {
    private ActivityTestScreenBinding binding;
    private TestScreenHandler handler;
    private CountDownTimer timer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_screen);
        long milis = Long.parseLong(getIntent().getStringExtra("duration"));

        int size = Setup.questions.getQuestionObjList().size();

        //duration
        //score_per-question
        //
        binding = DataBindingUtil.setContentView(this,R.layout.activity_setup);
        handler = new TestScreenHandler(this,binding,getIntent().getStringExtra("score_per_question"));
        binding.setHandler(handler);
        binding.prevTest.setEnabled(false);
        binding.question.setText(Setup.questions.getQuestionObjList().get(0).getQuestion());
        binding.optionA.setText(Setup.questions.getQuestionObjList().get(0).getOptions().get(0));
        binding.optionB.setText(Setup.questions.getQuestionObjList().get(0).getOptions().get(1));
        binding.optionC.setText(Setup.questions.getQuestionObjList().get(0).getOptions().get(2));
        binding.optionD.setText(Setup.questions.getQuestionObjList().get(0).getOptions().get(3));
        binding.remain.setText("1/"+size);
        timer = new CountDownTimer(milis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int min = (int) (millisUntilFinished / 60000);
                int sec = (int) ((millisUntilFinished / 1000) % 60);
                System.out.println(millisUntilFinished);
                CharSequence charSequence = String.format(Locale.getDefault(), " %02d:%02d ", Integer.valueOf(min), Integer.valueOf(sec));
                binding.timer.setText(charSequence);
            }

            @Override
            public void onFinish() {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.onSubmit(binding.submitTest);
    }
}