package com.akeem.retrofit.parserclass;

import java.util.ArrayList;
import java.util.List;

public class GptInput {
    private String model;
    private List<GptMessage> gptMessages = new ArrayList<>();
    public GptInput(){}


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<GptMessage> getMessages() {
        return gptMessages;
    }

    public void setMessages(List<GptMessage> gptMessages) {
        this.gptMessages = gptMessages;
    }
}
