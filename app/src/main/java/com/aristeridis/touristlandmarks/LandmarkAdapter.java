package com.aristeridis.touristlandmarks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LandmarkAdapter extends RecyclerView.Adapter<LandmarkAdapter.LandmarkViewHolder> {

    public interface OnLandmarkClickListener {
        void onLandmarkClick(Landmark landmark);
    }

    List<Landmark> landmarks;
    private final OnLandmarkClickListener listener;

    public LandmarkAdapter(List<Landmark> landmarks, OnLandmarkClickListener listener) {
        this.landmarks = landmarks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LandmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_landmark, parent, false);
        return new LandmarkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LandmarkViewHolder holder, int position) {
        Landmark landmark = landmarks.get(position);

        holder.nameText.setText(landmark.name);

        holder.categoryText.setText(landmark.category);

        String locStr = String.format("Lat: %.5f, Lon: %.5f",
                landmark.latitude, landmark.longitude);
        holder.locationText.setText(locStr);

        if (landmark.photo != null && landmark.photo.length > 0) {
            Bitmap bmp = BitmapFactory.decodeByteArray(landmark.photo, 0, landmark.photo.length);
            holder.photoView.setImageBitmap(bmp);
        } else {
            holder.photoView.setImageResource(android.R.color.darker_gray);
        }

        holder.itemView.setOnLongClickListener(v -> {
            listener.onLandmarkClick(landmark);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return landmarks.size();
    }

    static class LandmarkViewHolder extends RecyclerView.ViewHolder {
        ImageView photoView;
        TextView nameText, categoryText, locationText;

        public LandmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            photoView     = itemView.findViewById(R.id.photoView);
            nameText      = itemView.findViewById(R.id.nameText);
            categoryText  = itemView.findViewById(R.id.categoryText);
            locationText  = itemView.findViewById(R.id.locationText);
        }
    }
}
