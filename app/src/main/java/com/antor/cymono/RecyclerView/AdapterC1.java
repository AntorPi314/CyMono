package com.antor.cymono.RecyclerView;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antor.cymono.Channel1;
import com.antor.cymono.R;
import com.bumptech.glide.Glide;

import com.google.firebase.firestore.FirebaseFirestore;


public class AdapterC1 extends RecyclerView.Adapter<ViewHolderC1> {

    Context context;
    List<ItemC1> items;
    Channel1 C1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public AdapterC1(Channel1 C1, Context context, List<ItemC1> items) {
        this.C1 = C1;
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
        ItemC1 item = items.get(position);
       // holder.txt_name.setAutoLinkMask(Linkify.WEB_URLS);
        // holder.txt_name.setMovementMethod(LinkMovementMethod.getInstance());
        Glide.with(context).load(items.get(position).getImg_profile()).into(holder.img_profile);
        holder.txt_name.setText(items.get(position).getTxt_name());
        holder.txt_info.setText(items.get(position).getTxt_info());
        holder.tet_post.setText(items.get(position).getTet_post());
        if (items.get(position).getImg1() == "" || items.get(position).getImg1() == null) {
            holder.linear_img.setVisibility(View.GONE);
        } else {
            holder.linear_img.setVisibility(View.VISIBLE);
            Glide.with(context).load(items.get(position).getImg1()).into(holder.img1);
        }

        holder.itemView.setOnLongClickListener(v -> {
            Log.d("AdapterC1", "Long click on item at position: " + position);
            C1.showEditPostDialog(items.get(position).getPostId(), items.get(position).getTet_post(), items.get(position).getImg1());

            //Toast.makeText(context, items.get(position).getPostId(), Toast.LENGTH_SHORT).show();

            return true;
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
