package com.aristeridis.touristlandmarks;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Landmark {
    @PrimaryKey
    @NonNull
    String id;
    String description;
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

    public Landmark(String id, String description, @NonNull String name, byte[] photo,double latitude, double longitude) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Landmark{" +
                "id='" + id + '\'' +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
