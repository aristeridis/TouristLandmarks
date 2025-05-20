package com.aristeridis.touristlandmarks;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LandmarksListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LandmarkAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmarks_list);

        recyclerView = findViewById(R.id.landmarksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getDatabase(this);

        loadLandmarks();
    }

    private void loadLandmarks() {
        new Thread(() -> {
            final List<Landmark> landmarks = db.landmarksDao().getAllLandmarks();

            runOnUiThread(() -> {
                adapter = new LandmarkAdapter(landmarks, landmark -> showDeleteDialog(landmark));
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

    private void showDeleteDialog(Landmark landmark) {
        new AlertDialog.Builder(this)
                .setTitle("Διαγραφή")
                .setMessage("Θέλετε να διαγράψετε το σημείο: " + landmark.name + "?")
                .setPositiveButton("Ναι", (dialog, which) -> deleteLandmark(landmark))
                .setNegativeButton("Όχι", null)
                .show();
    }

    private void deleteLandmark(Landmark landmark) {
        new Thread(() -> {
            db.landmarksDao().delete(landmark);

            runOnUiThread(() -> {
                adapter.landmarks.remove(landmark);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }
}
