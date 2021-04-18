package com.example.helloworld;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WifiAPInfoEntity implements Comparable<WifiAPInfoEntity> {

    public WifiAPInfoEntity(){}

    public WifiAPInfoEntity(String apName, String rssiValue) {
        this.apName = apName;
        this.rssiValue = rssiValue;
    }

    public WifiAPInfoEntity(String roomNumber, String apName, String rssiValue) {
        this.roomNumber = roomNumber;
        this.apName = apName;
        this.rssiValue = rssiValue;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "apName")
    public String apName;

    @ColumnInfo(name = "roomNumber")
    public String roomNumber;

    @ColumnInfo(name = "rssiValue")
    public String rssiValue;

    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRssiValue() {
        return rssiValue;
    }

    public void setRssiValue(String rssiValue) {
        this.rssiValue = rssiValue;
    }

    @Override
    public int compareTo(WifiAPInfoEntity o) {
        return (Integer.parseInt(this.getRssiValue()) < Integer.parseInt(o.getRssiValue()) ? -1 :
                (Integer.parseInt(this.getRssiValue()) == Integer.parseInt(o.getRssiValue()) ? 0 : 1));
    }

    @Override
    public String toString() {
        return "WifiAPInfoEntity{" +
                "uid=" + uid +
                ", apName='" + apName + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", rssiValue='" + rssiValue + '\'' +
                '}';
    }
}
