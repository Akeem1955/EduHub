package com.akeem.retrofit.parserclass;

public class Choice {
    private int index;
    private GptMessage message;
    private String finish_reason;

    public Choice() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public GptMessage getMessage() {
        return message;
    }

    public void setMessage(GptMessage message) {
        this.message = message;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }
}
