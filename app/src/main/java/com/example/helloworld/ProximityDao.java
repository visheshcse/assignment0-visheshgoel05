package com.example.helloworld;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProximityDao {
    @Insert
    void insertAll(Proximity... proximiy);

    @Query("SELECT * FROM Proximity WHERE timestamp >= :time1 AND " +
            "timestamp <= :time2")
    List<Proximity> findPast1hr(long time1, long time2);

    @Query("DELETE FROM Proximity ")
    void deleteAll();
}
