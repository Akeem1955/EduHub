package com.akeem.retrofit.parserclass;

import java.util.ArrayList;

public class QuestionObj {
    private String question;
    private ArrayList<String> options;
    private String answer;


    public QuestionObj() {
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}



