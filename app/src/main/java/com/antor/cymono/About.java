package com.antor.cymono;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.text.method.LinkMovementMethod;

public class About extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TextView txt_about;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.act_about);
        UIUtils.setStatusBarAndNavigationBar(this);

        txt_about = findViewById(R.id.txt_about);
        txt_about.setTextIsSelectable(true);
        txt_about.setMovementMethod(LinkMovementMethod.getInstance());

        DatabaseReference myRef = database.getReference("apps/about");

        // myRef.setValue("Hello, World!");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                txt_about.setText(value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    public void clickFinish(View view) {
        finish();
    }
}