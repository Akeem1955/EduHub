package com.ashiri.instuctor;

import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.Query;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insert(Instructor i);
    @Query("Delete From Instructor")
    void clear();

}
