package com.aristeridis.touristlandmarks;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class LandmarkViewModel extends AndroidViewModel {

    private LandmarksDao landmarksDao;
    private LiveData<List<Landmark>> allLandmarks;

    public LandmarkViewModel(Application application) {
        super(application);
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "landmarks-db").build();
        landmarksDao = db.landmarksDao();
        allLandmarks = (LiveData<List<Landmark>>) landmarksDao.getAllLandmarks();  // Υποθέτω ότι θέλεις να το κάνεις LiveData
    }

    public void insert(Landmark landmark) {
        new Thread(() -> landmarksDao.insert(landmark)).start();
    }

    public LiveData<List<Landmark>> getAllLandmarks() {
        return allLandmarks;
    }
}

