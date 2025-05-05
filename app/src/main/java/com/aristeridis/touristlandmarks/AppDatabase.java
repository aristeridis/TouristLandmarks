package com.aristeridis.touristlandmarks;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Landmark.class},version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract LandmarksDao landmarksDao();
}