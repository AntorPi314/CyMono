package com.antor.cymono;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import com.antor.cymono.RecyclerView.AdapterC1;
import com.antor.cymono.RecyclerView.ItemC1;

public class Channel1 extends AppCompatActivity {

    List<ItemC1> items = new ArrayList<ItemC1>();
    private AdapterC1 adapterC1;

    private static final int PAGE_SIZE = 2;
    private DocumentSnapshot lastVisible;
    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.act_channel1);

        String img_link = "https://avatars.githubusercontent.com/u/123496530?v=4";


        RecyclerView recyclerView = findViewById(R.id.rv_channel1);

        items.add(new ItemC1(img_link, "Antor", "23 Liked | 08:23PM", "This is a post", img_link));
        items.add(new ItemC1(img_link, "Antor", "23 Liked | 08:23PM", "This is a post", img_link));
        items.add(new ItemC1(img_link, "Antor", "23 Liked | 08:23PM", "This is a post", img_link));


        adapterC1 = new AdapterC1(this, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterC1);







        fetchFirestoreData();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void fetchFirestoreData() {
        if (isLoading) return; // Prevent multiple fetches

        isLoading = true;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference postsRef = db.collection("post");

        Query query = postsRef.orderBy("timestamp").limit(PAGE_SIZE);
        if (lastVisible != null) {
            query = query.startAfter(lastVisible);
        }

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().isEmpty()) {
                        // No more data to load
                        isLoading = false;
                        return;
                    }

                    items.clear(); // Clear existing items if you are refreshing
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String imgLink = document.getString("img_link");
                        String name = document.getString("name");
                        String timestamp = document.getString("timestamp");
                        String post = document.getString("post");

                        items.add(new ItemC1(imgLink, name, timestamp, post, imgLink));
                    }
                    adapterC1.notifyDataSetChanged();
                    lastVisible = task.getResult().getDocuments().get(task.getResult().size() - 1);
                    isLoading = false;
                } else {
                    Log.d("Firestore", "Error getting documents: ", task.getException());
                    Toast.makeText(Channel1.this, "Error loading data", Toast.LENGTH_SHORT).show();
                    isLoading = false;
                }
            }
        });
    }
}