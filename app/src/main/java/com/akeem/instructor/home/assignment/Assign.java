package com.akeem.instructor.home.assignment;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Assign extends BaseObservable {
    private String topic;
    private String concentrate;
    private String instruction;
    private String question_a;
    private String question_b;
    private String question_c;
    private String name;


    public Assign() {
    }
    @Bindable
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    @Bindable
    public String getConcentrate() {
        return concentrate;
    }

    public void setConcentrate(String concentrate) {
        this.concentrate = concentrate;
    }
    @Bindable
    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Bindable
    public String getQuestion_a() {
        return question_a;
    }

    public void setQuestion_a(String question_a) {
        this.question_a = question_a;
    }

    @Bindable
    public String getQuestion_b() {
        return question_b;
    }

    public void setQuestion_b(String question_b) {
        this.question_b = question_b;
    }

    @Bindable
    public String getQuestion_c() {
        return question_c;
    }

    public void setQuestion_c(String question_c) {
        this.question_c = question_c;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
