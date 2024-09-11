package com.antor.cymono.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antor.cymono.R;
import com.bumptech.glide.Glide;

public class AdapterC1 extends RecyclerView.Adapter<ViewHolderC1> {

    Context context;
    List<ItemC1> items;

    public AdapterC1(Context context, List<ItemC1> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolderC1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderC1(LayoutInflater.from(context).inflate(R.layout.cust_channel1b, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderC1 holder, int position) {
        Glide.with(context).load(items.get(position).getImg_profile()).into(holder.img_profile);
        holder.txt_name.setText(items.get(position).getTxt_name());
        holder.txt_info.setText(items.get(position).getTxt_info());
        holder.tet_post.setText(items.get(position).getTet_post());
        if (items.get(position).getImg1() == null) {
            holder.img1.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(items.get(position).getImg1()).into(holder.img1);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
