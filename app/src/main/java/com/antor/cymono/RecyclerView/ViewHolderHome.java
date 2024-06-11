package com.antor.cymono.RecyclerView;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antor.cymono.R;

public class ViewHolderHome extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView,emailView;

    public ViewHolderHome(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        nameView = itemView.findViewById(R.id.name);
        emailView = itemView.findViewById(R.id.email);
    }
}
