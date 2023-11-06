package com.ashiri.upload_uri;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert
    void insert(Uris uri);


    @Query("SELECT COUNT(*) FROM  Uris WHERE uri = :uri")
    int count(String uri);

}
