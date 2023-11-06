package com.ashiri.upload_uri;

import android.app.Application;
import android.content.Context;

public class UploadRepo {
    private Dao dao;
    public UploadRepo(Context a){
        this.dao = UploadDp.getInstance(a.getApplicationContext()).getDao();
    }
    public void insert(Uris x){
        dao.insert(x);
    }
    public boolean exit(String uri){return dao.count(uri) > 0;}
}
