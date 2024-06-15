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
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyProfile.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_home);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("John wick", "john.wick@email.com", R.drawable.a));
        items.add(new Item("Robert j", "robert.j@email.com", R.drawable.b));
        items.add(new Item("James Gunn", "james.gunn@email.com", R.drawable.c));
        items.add(new Item("Ricky tales", "rickey.tales@email.com", R.drawable.d));
        items.add(new Item("Micky mose", "mickey.mouse@email.com", R.drawable.e));
        items.add(new Item("Pick War", "pick.war@email.com", R.drawable.f));
        items.add(new Item("Leg piece", "leg.piece@email.com", R.drawable.g));
        items.add(new Item("Apple Mac", "apple.mac@email.com", R.drawable.g));
        items.add(new Item("John wick", "john.wick@email.com", R.drawable.a));
        items.add(new Item("Robert j", "robert.j@email.com", R.drawable.b));
        items.add(new Item("James Gunn", "james.gunn@email.com", R.drawable.c));
        items.add(new Item("Ricky tales", "rickey.tales@email.com", R.drawable.d));
        items.add(new Item("Micky mose", "mickey.mouse@email.com", R.drawable.e));
        items.add(new Item("Pick War", "pick.war@email.com", R.drawable.f));
        items.add(new Item("Leg piece", "leg.piece@email.com", R.drawable.g));
        items.add(new Item("Apple Mac", "apple.mac@email.com", R.drawable.g));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterHome(getApplicationContext(), items));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}