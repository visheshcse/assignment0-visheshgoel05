package com.example.helloworld;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LinearAccelerationDao {
    @Insert
    void insertAll(LinearAcceleration... linearAcceleration);

    @Query("SELECT * FROM LinearAcceleration WHERE timestamp >= :time1 AND " +
            "timestamp <= :time2")
    List<LinearAcceleration> findPast1hr(long time1, long time2);

    @Query("DELETE FROM LinearAcceleration ")
    void deleteAll();
}
