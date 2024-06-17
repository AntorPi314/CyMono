package com.antor.cymono;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfile extends AppCompatActivity {
    private ImageView img_account;
    public Context context;
    private Button but_theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        context = getApplicationContext();
        setContentView(R.layout.act_my_profile);

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