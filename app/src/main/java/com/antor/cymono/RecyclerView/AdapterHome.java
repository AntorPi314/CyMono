package com.antor.cymono.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.antor.cymono.R;
import com.bumptech.glide.Glide;

public class AdapterHome extends RecyclerView.Adapter<ViewHolderHome> {

    Context context;
    List<ItemHome> items;

    public AdapterHome(Context context, List<ItemHome> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolderHome onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new ViewHolderHome(LayoutInflater.from(context).inflate(R.layout.cust_home1,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHome holder, int position) {
        holder.channel_name.setText(items.get(position).getChannelName());
        holder.channel_des.setText(items.get(position).getChannelDes());
        holder.channel_time.setText(items.get(position).getChannelTime());
        Glide.with(context).load(items.get(position).getImageURL()).into(holder.img_profile);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
