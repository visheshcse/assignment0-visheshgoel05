package com.example.helloworld;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccelerometerDao {
    @Insert
    void insertAll(Accelerometer... accelerometer);

    @Query("SELECT * FROM Accelerometer WHERE timestamp >= :time1 AND " +
            "timestamp <= :time2")
    List<Accelerometer> findPast1hr(long time1, long time2);

    @Query("DELETE FROM Accelerometer ")
    void deleteAll();
}
