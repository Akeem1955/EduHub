package com.ashiri.instuctor;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Instructor.class},version = 1, exportSchema = false)
public abstract class Db extends RoomDatabase {
    public abstract Dao getDao();
    private static Db instance;

    public synchronized static Db getInstance(Context ctx){
        if (instance == null){
            instance = Room.databaseBuilder(
                    ctx.getApplicationContext(),
                    Db.class,
                    "instructor.db"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }


}
