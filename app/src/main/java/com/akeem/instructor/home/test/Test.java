package com.akeem.instructor.home.test;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Test extends BaseObservable {
    private String topic;
    private String concentrate;
    private String score_per_question;
    private String no_of_question;
    private String duration;


    public Test(){}

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
    public String getScore_per_question() {
        return score_per_question;
    }

    public void setScore_per_question(String score_per_question) {
        this.score_per_question = score_per_question;
    }
    @Bindable
    public String getNo_of_question() {
        return no_of_question;
    }

    public void setNo_of_question(String no_of_question) {
        this.no_of_question = no_of_question;
    }

    @Bindable
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }



}
