package com.akeem.instructor.parser;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Cancel extends BaseObservable {
    private String reason;


    @Bindable
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
