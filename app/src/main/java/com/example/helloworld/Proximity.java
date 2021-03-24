package com.example.helloworld;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Proximity {

    public Proximity(float proximityValue) {
        this.proximityValue = proximityValue;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "proximityValue")
    public float proximityValue;
}
