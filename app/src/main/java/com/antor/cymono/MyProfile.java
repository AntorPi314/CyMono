package com.antor.cymono;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences sp;
    Context context = this;

    private ImageView img_account, profile_pic;
    private TextView txt_user_name, txt_user_bio;
    private Button but_theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        context = getApplicationContext();
        setContentView(R.layout.act_my_profile);
        UIUtils.setStatusBarAndNavigationBar(this);

        sp = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);

        img_account = findViewById(R.id.img_account);
        profile_pic = findViewById(R.id.profile_pic);
        but_theme = findViewById(R.id.but_theme);
        txt_user_name = findViewById(R.id.txt_user_name);
        txt_user_bio = findViewById(R.id.txt_user_bio);

        // updateUserInfo();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
    }

    private void updateUserInfo(){
        if (sp.getBoolean("login", true)) {
            txt_user_name.setText(sp.getString("name", "No Name"));
            txt_user_bio.setText(sp.getString("bio", "No Bio"));
            String profile_pic_url = sp.getString("profile_pic", "");
            if (!profile_pic_url.isEmpty()) {
                Glide.with(this).load(profile_pic_url).into(profile_pic);
            }
        }else{
            txt_user_name.setText("User");
            txt_user_bio.setText("User Description");
            profile_pic.setImageResource(R.drawable.b);
        }
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

    public void clickAbout(View view) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    public void clickPassword(View view) {
        Intent intent = new Intent(this, PasswordSettings.class);
        startActivity(intent);
    }

    public void clickFinish(View view) {
        finish();
    }

    public void clickComingSoon(View view) {
        Toast.makeText(context, "Coming Soon...", Toast.LENGTH_SHORT).show();
    }

    private void showLogoutDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cust_dia_logout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

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
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("login", false).apply();
                updateUserInfo();
                Toast.makeText(MyProfile.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}