package com.example.helloworld;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LightDao {
    @Insert
    void insertAll(Light... light);

    @Query("SELECT * FROM Light WHERE timestamp >= :time1 AND " +
            "timestamp <= :time2")
    List<Light> findPast1hr(long time1, long time2);

    @Query("DELETE FROM Light ")
    void deleteAll();
}
