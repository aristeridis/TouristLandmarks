package com.aristeridis.touristlandmarks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static final String DB_NAME = "landmarks.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "landmarks";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_IMAGE = "image";
    public static final String TIMESTAMP = "timestamp";

    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public SQLiteDatabase getInstance() {
        return this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);

    }

    public void createTable(SQLiteDatabase db) {
        String query = ("CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL," +
                COL_DESCRIPTION + " TEXT, " +
                COL_LATITUDE + " REAL, " +
                COL_LONGITUDE + " REAL, " +
                COL_IMAGE + " TEXT, " +
                TIMESTAMP + " TEXT );"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<Landmark> getAllLandmarks() {
        Cursor cursor = getInstance().rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<Landmark> landmarks = new ArrayList<>();
        while (cursor.moveToNext()) {
            Landmark landmark = new Landmark(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getBlob(6)
            );
            landmarks.add(landmark);
        }
        cursor.close();
        return landmarks;

    }
    public Landmark getLastLandmark(){
        Cursor cursor = getInstance().rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_ID + " DESC LIMIT 1", null);
        Landmark landmark = null;
        while (cursor.moveToNext()) {
             landmark = new Landmark(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getBlob(6)
                     );
        }
        cursor.close();
        return landmark;
    }
    public boolean deleteLandmarkById(String id){
       return getInstance().delete( TABLE_NAME, COL_ID + " =?", new String[]{id}) > 0;
    }
    public long saveLandmark(Landmark landmark){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, landmark.getName());
        values.put(COL_DESCRIPTION, landmark.getAddress());
        values.put(COL_LATITUDE, landmark.getLatitude());
        values.put(COL_LONGITUDE, landmark.getLongitude());
        values.put(COL_IMAGE, landmark.getPhoto());
        values.put(TIMESTAMP, landmark.getTimeStamp());
        return getInstance().insert(TABLE_NAME, null, values);
    }
}
