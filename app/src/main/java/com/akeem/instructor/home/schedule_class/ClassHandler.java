package com.akeem.instructor.home.schedule_class;

import android.content.Context;
import android.view.View;

import com.akeem.instructor.home.interfaces.Finished;

public class ClassHandler {
    private Context ctx;
    private Finished finished;

    public ClassHandler(Context ctx, Finished finished) {
        this.ctx = ctx;
        this.finished = finished;
    }

    public void handleBackClick(View view){
        finished.onFinish();

    }
}
