package com.example.helloworld;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Proximity {

    public Proximity(float proximityValue, long timestamp) {
        this.timestamp = timestamp;
        this.proximityValue = proximityValue;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "Timestamp")
    public long timestamp;

    @ColumnInfo(name = "proximityValue")
    public float proximityValue;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getProximityValue() {
        return proximityValue;
    }

    public void setProximityValue(float proximityValue) {
        this.proximityValue = proximityValue;
    }

    @Override
    public String toString() {
        return "Proximity{" +
                "uid=" + uid +
                ", timestamp=" + timestamp +
                ", proximityValue=" + proximityValue +
                '}';
    }
}
