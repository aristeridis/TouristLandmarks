package com.aristeridis.touristlandmarks;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity
public class Landmark {
    @PrimaryKey
    @NonNull
    String id;
    @ColumnInfo(name = "landamarkName")
    @NonNull
    String name;
    String city;
    String address;
    String latitude;
    String longitude;
    public String timeStamp;


    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte[] photo;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
    public byte[] getPhoto() {

        return photo;
    }

    public void setPhoto(byte[] photo) {

        this.photo = photo;
    }

    public Landmark(String id, String description, @NonNull String name, byte[] photo,String latitude, String longitude) {
        this.id = id;
        this.address = description;
        this.name = name;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Landmark( String city, String address, String latitude, String longitude, String timeStamp) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Landmark{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
