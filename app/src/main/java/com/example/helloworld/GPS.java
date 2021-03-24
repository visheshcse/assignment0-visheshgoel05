package com.example.helloworld;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class GPS {
    public GPS(float latitude, float longitude, long timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "latitude")
    public float latitude;

    @ColumnInfo(name = "longitude")
    public float longitude;

    @ColumnInfo(name = "Timestamp")
    public long timestamp;
}
