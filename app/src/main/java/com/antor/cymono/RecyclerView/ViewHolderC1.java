package com.antor.cymono.RecyclerView;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antor.cymono.R;

public class ViewHolderC1 extends RecyclerView.ViewHolder {

    ImageView img_profile, img_like, img1;
    TextView txt_name, txt_info, tet_post;
    LinearLayout linear_img;

    public ViewHolderC1(@NonNull View itemView) {
        super(itemView);
        img_profile = itemView.findViewById(R.id.img_profile);
        txt_name = itemView.findViewById(R.id.txt_name);
        txt_info = itemView.findViewById(R.id.txt_info);
        img_like = itemView.findViewById(R.id.img_like);
        tet_post = itemView.findViewById(R.id.txt_post);
        img1 = itemView.findViewById(R.id.img1);
        linear_img = itemView.findViewById(R.id.linear_img);
    }
}

