package com.akeem.retrofit.parserclass;

public class GptMessage {
    private String role;
    private String content;
    public GptMessage(){}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
