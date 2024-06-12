package com.antor.cymono.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antor.cymono.R;

public class AdapterHome extends RecyclerView.Adapter<ViewHolderHome> {


    Context context;
    List<Item> items;

    public AdapterHome(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolderHome onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new ViewHolderHome(LayoutInflater.from(context).inflate(R.layout.cust_itemview_home,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolderHome holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.emailView.setText(items.get(position).getEmail());
        holder.imageView.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
