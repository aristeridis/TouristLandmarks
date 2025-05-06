package com.aristeridis.touristlandmarks;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
@Dao
public interface LandmarksDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Landmark... landmarks);

    @Query("SELECT * FROM Landmark")
    List<Landmark> getAllLandmarks();

}
