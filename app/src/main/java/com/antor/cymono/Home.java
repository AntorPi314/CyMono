package com.antor.cymono;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.antor.cymono.RecyclerView.ItemHome;
import com.antor.cymono.RecyclerView.AdapterHome;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Home extends AppCompatActivity {
    Context context = this;
    FirebaseFirestore db = FirebaseFirestore.getInstance();;
    SharedPreferences sp, sp_item;
    private AdapterHome adapterHome;
    private SwipeRefreshLayout swipeRefreshLayout;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    List<ItemHome> items = new ArrayList<ItemHome>();

    private ImageView profile_pic;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        context = getApplicationContext();
        setContentView(R.layout.act_home);
        sp = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        sp_item = context.getSharedPreferences("item_list", Context.MODE_PRIVATE);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#2A2E37"));
        window.setNavigationBarColor(Color.parseColor("#2A2E37"));

        profile_pic = findViewById(R.id.profile_pic);
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MyProfile.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.rv_home);

        loadItemsFromSharedPreferences();

/*        items.add(new ItemHome("/channel/bcast1/CSE30", "Bengal Tiger", "A page dedicated to the majestic Bengal tiger", "10:00 AM", "https://media.4-paws.org/d/b/0/5/db05d79a7b3002447904b841871363190b45b74d/VIER%20PFOTEN_2017-10-20_164-3854x2667-1920x1329.webp"));
        items.add(new ItemHome("/channel/bcast1/CSE30", "Wallpaper", "A collection of stunning wallpapers from around the world", "10:00 AM", "https://images.pexels.com/photos/19550021/pexels-photo-19550021/free-photo-of-rocky-peak-of-a-snow-covered-mountain-in-the-french-pyrenees.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        items.add(new ItemHome("/channel/bcast1/CSE30", "Bangladesh Cricket", "A passionate community for fans of Bangladesh cricket", "10:00 AM", "https://businessinspection.com.bd/wp-content/uploads/2022/03/How-Bangladesh-Cricket-Board-Makes-Money-1.jpg"));

        for (int i = 1; i < 10; i++) {
            items.add(new ItemHome("/channel/bcast1/CSE30", "Bengal Tiger", "A page dedicated to the majestic Bengal tiger", "10:00 AM", "https://media.4-paws.org/d/b/0/5/db05d79a7b3002447904b841871363190b45b74d/VIER%20PFOTEN_2017-10-20_164-3854x2667-1920x1329.webp"));
        }*/

        adapterHome = new AdapterHome(this,  context, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterHome);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            items.clear();
            loadItemsFromSharedPreferences();
            adapterHome.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

        });

        DatabaseReference myRef = database.getReference("message");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Toast.makeText(Home.this, value, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void clickComingSoon(View view) {
        Toast.makeText(context, "Coming Soon...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
        updateChannelList();
    }

    private void updateUserInfo() {
        if (sp.getBoolean("login", true)) {
            String profile_pic_url = sp.getString("profile_pic", "");
            if (!profile_pic_url.isEmpty()) {
                Glide.with(this).load(profile_pic_url).into(profile_pic);
            }
        } else {
            profile_pic.setImageResource(R.drawable.b);
        }
    }

    private void updateChannelList() {
        loadItemsFromSharedPreferences();
        adapterHome.notifyDataSetChanged();
    }

    public void showItemRemoveDialog(int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cust_dia_remove_channel);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView tvDialogMessage = dialog.findViewById(R.id.tv_dialog_message);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnDelete = dialog.findViewById(R.id.btn_delete);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (position >= 0 && position < items.size()) {
                    items.remove(position);
                    adapterHome.notifyItemRemoved(position);
                    adapterHome.notifyItemRangeChanged(position, items.size());
                    saveItemsToSharedPreferences();
                }
                Toast.makeText(Home.this, "Remove Channel successfully", Toast.LENGTH_SHORT).show();
                adapterHome.notifyDataSetChanged();
            }
        });

        dialog.show();
    }

    private void showAddChannelDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.cust_dia_add_channel, null);

        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(dialogView);

        final EditText editTextChannelCode = dialogView.findViewById(R.id.edit_channel_code);
        Button btnAddChannel = dialogView.findViewById(R.id.btn_add);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        btnAddChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddChannel(editTextChannelCode.getText().toString().trim());
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void clickAddChannel(View view) {
       showAddChannelDialog();
    }

    private void loadItemsFromSharedPreferences() {
        String json = sp_item.getString("item_list", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<ItemHome>>() {}.getType();
            List<ItemHome> loadedItems = gson.fromJson(json, type);

            if (loadedItems != null && !loadedItems.isEmpty()) {
                items.clear();
                items.addAll(loadedItems);
            }
        }
    }

    private void AddChannel(String channelCode) {
        db.collection("/share").document(channelCode).get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        if(documentSnapshot.getString("channel") == null || documentSnapshot.getString("channel").isEmpty()){
                            Toast.makeText(Home.this, "User can not be a Channel", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String channel = documentSnapshot.getString("channel");
                        String path = documentSnapshot.getString("path");
                        String C_path = "/channel/" + channel + "/" + path;

                        db.collection(C_path).document("info").get().addOnSuccessListener(documentSnapshot2 -> {
                                    if (documentSnapshot2.exists()) {
                                        String name = documentSnapshot2.getString("channel_name");
                                        String des = documentSnapshot2.getString("des");
                                        String pic = documentSnapshot2.getString("profile_pic");

                                        ItemHome newItem = new ItemHome(C_path, name, des, "", pic);
                                        items.add(0, newItem);
                                        adapterHome.notifyDataSetChanged();

                                        saveItemsToSharedPreferences();
                                    } else {
                                        Toast.makeText(this, "Info Not Found", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error fetching data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(Home.this, "Channel Not Found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Home.this, "Error fetching data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveItemsToSharedPreferences() {
        SharedPreferences.Editor editor = sp_item.edit();
        String json = gson.toJson(items);
        editor.putString("item_list", json);
        editor.apply();
    }

    public void clickAbout(View view) {
        Intent intent = new Intent(Home.this, About.class);
        startActivity(intent);
    }

}

