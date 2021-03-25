package com.example.helloworld;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GPSDao {
    @Insert
    void insertAll(GPS... gps);

    @Query("SELECT * FROM GPS WHERE timestamp >= :time1 AND " +
            "timestamp <= :time2")
    List<GPS> findPast1hr(long time1, long time2);

    @Query("DELETE FROM GPS ")
    void deleteAll();
}
