package com.aristeridis.touristlandmarks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    public static byte[] getImageBytesFromUri(Uri imageUri, android.content.ContentResolver contentResolver) throws IOException {
        InputStream inputStream = contentResolver.openInputStream(imageUri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getImageBytesFromImageView(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}

