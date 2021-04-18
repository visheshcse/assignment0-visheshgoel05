package com.example.helloworld;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WifiApInfoDao {
    @Insert
    void insertAll(WifiAPInfoEntity... wifiApInfoEntities);

    @Query("SELECT * FROM WifiAPInfoEntity WHERE apName == :apName ")
    List<WifiAPInfoEntity> findApData(String apName);

    @Query("DELETE FROM WifiAPInfoEntity ")
    void deleteAll();
}
