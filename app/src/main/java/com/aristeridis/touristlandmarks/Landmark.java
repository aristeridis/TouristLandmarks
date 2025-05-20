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
    String category;
    double latitude;
    double longitude;
    @ColumnInfo(name = "landamarkName")
    @NonNull
    String name;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte[] photo;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public byte[] getPhoto() {

        return photo;
    }

    public void setPhoto(byte[] photo) {

        this.photo = photo;
    }

    public Landmark(String id, String category, @NonNull String name, byte[] photo,double latitude, double longitude) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Landmark() {
    }

    @Override
    public String toString() {
        return "Landmark{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
