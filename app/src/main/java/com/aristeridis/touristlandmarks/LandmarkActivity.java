package com.aristeridis.touristlandmarks;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class LandmarkActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        database = new Database(this);
        if (!checkPermissions()) askPermissions();
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    private void getLocation() {
        if (!checkPermissions()) return;
        Task<Location> locationTask = fusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null);

        locationTask.addOnCompleteListener(task -> {
            Location location = task.getResult();
            CompletableFuture.supplyAsync(()-> getLatLng(location)).thenAccept(addresses -> {setLocation(addresses,location);});
        }).addOnFailureListener(e -> {
            e.printStackTrace();
        });
    }

    private void setLocation(List<Address> addresses,Location location) {
        if (addresses!=null && !addresses.isEmpty()) {
            Address address = addresses.get(0);
            String city = address.getLocality();
            String addressLine = address.getAddressLine(0);
            String latitude = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());
            saveLocation(city, addressLine, latitude, longitude);
        }else {
            saveLocation(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        }

    }

    private void saveLocation(String city,  String addressLine, String latitude, String longitude) {
        long millis=System.currentTimeMillis();
        Landmark landmark=new Landmark(city,addressLine,latitude,longitude,String.valueOf(millis));
        long id = database.saveLandmark(landmark);
        if (id==-1){
return;
        }
        runOnUiThread(()->{

        }

    }
    private void saveLocation( String latitude, String longitude) {

    }

    private List<Address> getLatLng(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            return geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void askPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private boolean checkPermissions() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED);

    }
}
