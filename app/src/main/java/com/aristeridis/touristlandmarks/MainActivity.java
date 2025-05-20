package com.aristeridis.touristlandmarks;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1001;
    private static final int LOCATION_PERMISSION_REQUEST = 2002;

    private RecyclerView recyclerView;
    private LandmarkAdapter adapter;
    private List<Landmark> landmarkList = new ArrayList<>();
    private Button addLandmarkButton;
    private TextView titleTextView;
    private FusedLocationProviderClient fusedLocationClient;
    private byte[] selectedPhotoBytes;
    private double currentLatitude = 0.0, currentLongitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTextView = findViewById(R.id.titleTextView);
        addLandmarkButton = findViewById(R.id.addLandmarkButton);
        recyclerView = findViewById(R.id.landmarksRecyclerView);

        titleTextView.setText("Τουριστικά Αξιοθέατα");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermissionAndFetch();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LandmarkAdapter(landmarkList, landmark -> showDeleteDialog(landmark));
        recyclerView.setAdapter(adapter);

        addLandmarkButton.setOnClickListener(v -> openImagePicker());
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream is = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                selectedPhotoBytes = baos.toByteArray();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Σφάλμα στην εικόνα", Toast.LENGTH_SHORT).show();
                return;
            }
            addNewLandmarkToList();
        }
    }

    private void addNewLandmarkToList() {
        String newId = UUID.randomUUID().toString();
        String newName = "Landmark " + (landmarkList.size() + 1);
        String newCategory = "Κατηγορία";
        double lat = currentLatitude;
        double lon = currentLongitude;
        byte[] photoBytes = selectedPhotoBytes != null ? selectedPhotoBytes : new byte[0];

        Landmark newLandmark = new Landmark(newId, newName, newCategory, photoBytes, lat, lon);
        landmarkList.add(newLandmark);

        adapter.notifyItemInserted(landmarkList.size() - 1);
        recyclerView.smoothScrollToPosition(landmarkList.size() - 1);
    }

    private void requestLocationPermissionAndFetch() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        } else {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationPermissionAndFetch();
            }
        }
    }

    private void showDeleteDialog(Landmark landmark) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Διαγραφή")
                .setMessage("Θέλετε να διαγράψετε το «" + landmark.name + "»;")
                .setPositiveButton("Ναι", (dialog, which) -> deleteLandmark(landmark))
                .setNegativeButton("Όχι", null)
                .show();
    }

    private void deleteLandmark(Landmark landmark) {
        int pos = landmarkList.indexOf(landmark);
        if (pos >= 0) {
            landmarkList.remove(pos);
            adapter.notifyItemRemoved(pos);
        }
    }
}
