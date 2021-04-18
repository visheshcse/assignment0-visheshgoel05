package com.example.helloworld;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WifiAPInfoEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WifiApInfoDao wifiApInfoDao();
}