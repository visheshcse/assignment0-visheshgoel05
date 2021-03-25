package com.example.helloworld;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Light {
    public Light(float lightValue, long timestamp) {
        this.lightValue = lightValue;
        this.timestamp = timestamp;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "lightValue")
    public float lightValue;

    @ColumnInfo(name = "Timestamp")
    public long timestamp;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public float getLightValue() {
        return lightValue;
    }

    public void setLightValue(float lightValue) {
        this.lightValue = lightValue;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Light{" +
                "uid=" + uid +
                ", lightValue=" + lightValue +
                ", timestamp=" + timestamp +
                '}';
    }
}
