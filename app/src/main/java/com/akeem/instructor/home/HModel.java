package com.akeem.instructor.home;

import android.view.View;

public class HModel {
    private String title;
    private int drawable;
    private View.OnClickListener listener;


    public HModel(String title, int drawable, View.OnClickListener listener) {
        this.title = title;
        this.drawable = drawable;
        this.listener = listener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
