package com.antor.cymono;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.antor.cymono.RecyclerView.Item;
import com.antor.cymono.RecyclerView.ItemHome;
import com.antor.cymono.RecyclerView.AdapterHome;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private ImageView img_profile;
    public Context context;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        context = getApplicationContext();
        setContentView(R.layout.act_home);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#2A2E37"));
        window.setNavigationBarColor(Color.parseColor("#2A2E37"));


        img_profile = findViewById(R.id.img_profile);


        Glide.with(this).load("https://avatars.githubusercontent.com/u/123496530?v=4").into(img_profile);


        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MyProfile.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_home);

        List<ItemHome> items = new ArrayList<ItemHome>();
        items.add(new ItemHome("Bengal Tiger", "A page dedicated to the majestic Bengal tiger", "10:00 AM", "https://media.4-paws.org/d/b/0/5/db05d79a7b3002447904b841871363190b45b74d/VIER%20PFOTEN_2017-10-20_164-3854x2667-1920x1329.webp"));
        items.add(new ItemHome("Wallpaper", "A collection of stunning wallpapers from around the world", "10:00 AM", "https://images.pexels.com/photos/19550021/pexels-photo-19550021/free-photo-of-rocky-peak-of-a-snow-covered-mountain-in-the-french-pyrenees.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        items.add(new ItemHome("Bangladesh Cricket", "A passionate community for fans of Bangladesh cricket", "10:00 AM", "https://businessinspection.com.bd/wp-content/uploads/2022/03/How-Bangladesh-Cricket-Board-Makes-Money-1.jpg"));


        for (int i = 1; i < 10; i++) {
            items.add(new ItemHome("Bengal Tiger", "A page dedicated to the majestic Bengal tiger", "10:00 AM", "https://media.4-paws.org/d/b/0/5/db05d79a7b3002447904b841871363190b45b74d/VIER%20PFOTEN_2017-10-20_164-3854x2667-1920x1329.webp"));
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterHome(getApplicationContext(), items));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showChannelCodeDialog() {
        // Inflate the custom dialog layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.cust_dia_add_channel, null);

        // Create the AlertDialog
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(dialogView);

        // Get references to the views
        final EditText editTextChannelCode = dialogView.findViewById(R.id.editTextChannelCode);
        Button btnAddChannel = dialogView.findViewById(R.id.btnAddChannel);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        // Handle the Add button click
        btnAddChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a Toast when Add is clicked
                Toast.makeText(Home.this, "Add button clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();  // Close the dialog
            }
        });

        // Handle the Cancel button click
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a Toast when Cancel is clicked
                Toast.makeText(Home.this, "Cancel button clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();  // Close the dialog
            }
        });

        // Show the dialog
        dialog.show();
    }


    public void clickAddChannel(View view) {

            FirebaseFirestore.getInstance().document("/share/CSE30/path").get().addOnSuccessListener(documentSnapshot -> {
                // Get the value from Firestore
                String pathValue = documentSnapshot.getString("path");

                // Show the Toast after fetching the value
                Toast.makeText(Home.this, pathValue, Toast.LENGTH_SHORT).show();

                // Show the dialog after the value is fetched
                showChannelCodeDialog();
            }).addOnFailureListener(e -> {
                // Handle any errors if the data fetch fails
                Toast.makeText(Home.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            });

    }

}

