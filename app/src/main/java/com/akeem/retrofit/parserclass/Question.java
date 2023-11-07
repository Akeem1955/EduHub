package com.akeem.retrofit.parserclass;


import java.util.List;

public class Question{
    private List<QuestionObj> questionObjList;

    public Question() {
    }

    public List<QuestionObj> getQuestionObjList() {

        return questionObjList;
    }

    public void setQuestionObjList(List<QuestionObj> questionObjList) {
        this.questionObjList = questionObjList;
    }
}