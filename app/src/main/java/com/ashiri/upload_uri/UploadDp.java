package com.ashiri.upload_uri;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Uris.class},version = 1, exportSchema = false)
public abstract class UploadDp extends RoomDatabase {
    public abstract Dao getDao();
    private static UploadDp instance;

    public synchronized static UploadDp getInstance(Context ctx){
        if (instance == null){
            instance = Room.databaseBuilder(
                    ctx.getApplicationContext(),
                    UploadDp.class,
                    "uri.db").build();
        }
        return instance;
    }
}
