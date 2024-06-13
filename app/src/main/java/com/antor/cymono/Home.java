package com.antor.cymono;

import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.act_home);

        RecyclerView recyclerView = findViewById(R.id.rv_home);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item("John wick","john.wick@email.com",R.drawable.a));
        items.add(new Item("Robert j","robert.j@email.com",R.drawable.b));
        items.add(new Item("James Gunn","james.gunn@email.com",R.drawable.c));
        items.add(new Item("Ricky tales","rickey.tales@email.com",R.drawable.d));
        items.add(new Item("Micky mose","mickey.mouse@email.com",R.drawable.e));
        items.add(new Item("Pick War","pick.war@email.com",R.drawable.f));
        items.add(new Item("Leg piece","leg.piece@email.com",R.drawable.g));
        items.add(new Item("Apple Mac","apple.mac@email.com",R.drawable.g));
        items.add(new Item("John wick","john.wick@email.com",R.drawable.a));
        items.add(new Item("Robert j","robert.j@email.com",R.drawable.b));
        items.add(new Item("James Gunn","james.gunn@email.com",R.drawable.c));
        items.add(new Item("Ricky tales","rickey.tales@email.com",R.drawable.d));
        items.add(new Item("Micky mose","mickey.mouse@email.com",R.drawable.e));
        items.add(new Item("Pick War","pick.war@email.com",R.drawable.f));
        items.add(new Item("Leg piece","leg.piece@email.com",R.drawable.g));
        items.add(new Item("Apple Mac","apple.mac@email.com",R.drawable.g));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterHome(getApplicationContext(),items));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}