package com.aristeridis.touristlandmarks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
public class ImageUtils {

    public byte[] getImageBytes(String imagePath) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            FileInputStream fileInputStream = new FileInputStream(imagePath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
    public void setImageFromBytes(byte[] imageBytes, ImageView imageView) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imageView.setImageBitmap(bitmap);
    }

}
