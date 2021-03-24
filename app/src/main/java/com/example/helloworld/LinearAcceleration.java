package com.example.helloworld;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class LinearAcceleration {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "X")
    public float x;

    @ColumnInfo(name = "Y")
    public float y;

    @ColumnInfo(name = "Z")
    public float z;

    @ColumnInfo(name = "Timestamp")
    public long timestamp;

    public LinearAcceleration(float x, float y, float z, long timestamp) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LinearAcceleration{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", timestamp=" + timestamp +
                '}';
    }
}
