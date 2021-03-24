package com.example.helloworld;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Accelerometer.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AccelerometerDao accelerometerDao();
    public abstract LinearAccelerationDao linearAccelerationDao();
}
