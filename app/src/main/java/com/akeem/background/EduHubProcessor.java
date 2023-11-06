package com.akeem.background;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

public class EduHubProcessor extends HandlerThread {
    public static EduHubHandler handler;
    private static class HandlerHelper{
        private static final EduHubProcessor processor = new EduHubProcessor();
    }
    public static EduHubProcessor getInstance(){
       return HandlerHelper.processor;
    }

    private EduHubProcessor() {
        super("edu_hub", Process.THREAD_PRIORITY_DEFAULT);
    }

    @Override
    protected void onLooperPrepared() {
        handler = new EduHubHandler(getLooper());
    }


    public EduHubHandler getHandler() {
        return handler;
    }
}
