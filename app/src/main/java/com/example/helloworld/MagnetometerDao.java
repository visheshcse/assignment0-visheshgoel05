package com.example.helloworld;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MagnetometerDao {
    @Insert
    void insertAll(Magnetometer... magnetometer);

    @Query("SELECT * FROM Magnetometer WHERE timestamp >= :time1 AND " +
            "timestamp <= :time2")
    List<Magnetometer> findPast1hr(long time1, long time2);

    @Query("DELETE FROM Magnetometer ")
    void deleteAll();
}
