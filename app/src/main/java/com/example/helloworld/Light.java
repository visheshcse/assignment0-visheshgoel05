package com.example.helloworld;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Light {

    public Light(float lightValue) {
        this.lightValue = lightValue;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "lightValue")
    public float lightValue;


}
