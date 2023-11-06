package com.akeem.eduhub;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.akeem.background.EduHubHandler;
import com.akeem.background.EduHubProcessor;

public class AppViewModel extends AndroidViewModel {
    public AppViewModel(@NonNull Application application) {
        super(application);
       EduHubProcessor processor = EduHubProcessor.getInstance();
       processor.start();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        EduHubProcessor.getInstance().quitSafely();
    }
}
