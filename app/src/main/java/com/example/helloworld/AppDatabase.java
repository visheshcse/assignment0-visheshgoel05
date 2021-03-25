package com.example.helloworld;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Accelerometer.class, LinearAcceleration.class, Light.class, Proximity.class, GPS.class, Magnetometer.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AccelerometerDao accelerometerDao();
    public abstract LinearAccelerationDao linearAccelerationDao();
    public abstract LightDao lightDao();
    public abstract ProximityDao proximityDao();
    public abstract GPSDao gpsDao();
    public abstract MagnetometerDao magnetometerDao();
}
