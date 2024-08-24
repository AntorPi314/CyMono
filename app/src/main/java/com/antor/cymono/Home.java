package com.antor.cymono;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private ImageView img_profile;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        context = getApplicationContext();
        setContentView(R.layout.act_home);

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
        items.add(new ItemHome("Hello World!", "How are you", "10:00 AM", "https://images.pexels.com/photos/19550021/pexels-photo-19550021/free-photo-of-rocky-peak-of-a-snow-covered-mountain-in-the-french-pyrenees.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" ));






        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterHome(getApplicationContext(), items));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}