package com.antor.cymono.RecyclerView;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antor.cymono.R;

public class ViewHolderHome extends RecyclerView.ViewHolder {

    ImageView imageView, img_profile;
    TextView nameView, emailView, channel_name, channel_des, channel_time;

    public ViewHolderHome(@NonNull View itemView) {
        super(itemView);
        img_profile = itemView.findViewById(R.id.img_profile);
        channel_name = itemView.findViewById(R.id.channel_name);
        channel_des = itemView.findViewById(R.id.channel_des);
        channel_time = itemView.findViewById(R.id.channel_time);
    }
}

