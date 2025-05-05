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
    @ColumnInfo(name = "landamarkName")
    @NonNull
    String name;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte[] photo;

    public Landmark(String id, String description, @NonNull String name, byte[] photo) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.photo = photo;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Landmark{" +
                "id='" + id + '\'' +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
