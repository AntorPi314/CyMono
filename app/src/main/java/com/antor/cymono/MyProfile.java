package com.antor.cymono;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfile extends AppCompatActivity {
    private ImageView img_account, img_profile;
    public Context context;
    private Button but_theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        context = getApplicationContext();
        setContentView(R.layout.act_my_profile);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#2A2E37"));
        window.setNavigationBarColor(Color.parseColor("#2A2E37"));

        img_account = findViewById(R.id.img_account);
        img_profile = findViewById(R.id.img_profile);
        but_theme = findViewById(R.id.but_theme);

        Glide.with(this).load("https://avatars.githubusercontent.com/u/123496530?v=4").into(img_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void clickAccount(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(context, Login.class);
            startActivity(intent);
        } else {
            showLogoutDialog();
        }
    }
    public void clickMyAccess(View view) {
        Intent intent = new Intent(this, MyAccess.class);
        startActivity(intent);
    }
    public void clickTheme(View view) {
        Intent intent = new Intent(this, Theme.class);
        startActivity(intent);
    }
    public void clickFinish(View view){
        finish();
    }
    private void showLogoutDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cust_dia_logout);

        TextView tvDialogMessage = dialog.findViewById(R.id.tv_dialog_message);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnLogout = dialog.findViewById(R.id.btn_logout);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Toast.makeText(MyProfile.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}