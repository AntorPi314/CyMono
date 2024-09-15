package com.antor.cymono.RecyclerView;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antor.cymono.Channel1;
import com.antor.cymono.Home;
import com.antor.cymono.R;
import com.bumptech.glide.Glide;

public class AdapterHome extends RecyclerView.Adapter<ViewHolderHome> {

    private final Context context;
    private final List<ItemHome> items;
    Intent intent;
    private final Activity activity;

    public AdapterHome(Activity activity, Context context, List<ItemHome> items) {
        this.activity = activity;
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
        ItemHome item = items.get(position);
        holder.channel_name.setText(items.get(position).getChannelName());
        holder.channel_des.setText(items.get(position).getChannelDes());
        holder.channel_time.setText(items.get(position).getChannelTime());
        Glide.with(context).load(items.get(position).getImageURL()).into(holder.img_profile);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Channel1.class);
            intent.putExtra("C_path", items.get(position).getChannelPath());
            activity.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (activity instanceof Home) {
                ((Home) activity).showItemRemoveDialog(position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
