package com.aristeridis.touristlandmarks;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private LandmarkViewModel landmarksViewModel;
    private ImageView imageView;
    private TextView nameTextView, descriptionTextView;
    private Location currentLocation;

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastKnownLocation();

        imageView = findViewById(R.id.imageView);
        nameTextView = findViewById(R.id.nameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        Button selectImageButton = findViewById(R.id.selectImageButton);
        selectImageButton.setOnClickListener(v -> openImagePicker());

        landmarksViewModel = new ViewModelProvider(this).get(LandmarkViewModel.class);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveLandmark());
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    private void getLastKnownLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        currentLocation = location;
                    }
                });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                byte[] imageBytes = ImageUtils.getImageBytesFromUri(selectedImageUri, getContentResolver());
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveLandmark() {
        String name = nameTextView.getText().toString();
        String description = descriptionTextView.getText().toString();
        byte[] photo = ImageUtils.getImageBytesFromImageView(imageView);  // Χρησιμοποιούμε την ImageUtils
        String latitude = String.valueOf(currentLocation != null ? currentLocation.getLatitude() : 0.0);
        String longitude = String.valueOf(currentLocation != null ? currentLocation.getLongitude() : 0.0);

        Landmark landmark = new Landmark("1", description, name, photo, latitude, longitude);
        landmarksViewModel.insert(landmark);
    }

}
