package com.antor.cymono;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    Context context = this;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sp;

    private ImageView img_next;
    private EditText edit_email, edit_pass;
    String email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.act_login);

        sp = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#2A2E37"));
        window.setNavigationBarColor(Color.parseColor("#2A2E37"));

        img_next = findViewById(R.id.img_next);
        edit_email = findViewById(R.id.edit_email);
        edit_pass = findViewById(R.id.edit_pass);

        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loginUser() {
        email = edit_email.getText().toString().trim();
        pass = edit_pass.getText().toString().trim();

        if (email == "" || pass == "") {
            Toast.makeText(getApplicationContext(), "Field Not be Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(this, email + " " + pass, Toast.LENGTH_SHORT).show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = auth.getCurrentUser();
                            fetchPublicAddress();
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("login", true).apply();
                        } else {
                            Toast.makeText(Login.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void fetchPublicAddress() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("userprivate").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String publicAddress = documentSnapshot.getString("public");
                        if (publicAddress != null) {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("publicAddress", publicAddress).apply();
                            fetchPublicData(publicAddress);
                        }
                    }
                });
    }

    public void fetchPublicData(String publicAddress) {
        db.collection("user").document(publicAddress).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        String bio = documentSnapshot.getString("bio");
                        String profile_pic = documentSnapshot.getString("profile_pic");

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("name", name).apply();
                        editor.putString("bio", bio).apply();
                        editor.putString("profile_pic", profile_pic).apply();
                        Toast.makeText(context, "Welcome! " + name, Toast.LENGTH_SHORT).show();
                        finish();
                        //Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickFinish(View view) {
        finish();
    }
}