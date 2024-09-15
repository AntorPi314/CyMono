package com.antor.cymono;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ImageViewer extends AppCompatActivity {
    private WebView webview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.act_image_viewer);
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#000000"));
        window.setNavigationBarColor(Color.parseColor("#000000"));
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        webview1 = findViewById(R.id.webview1);
        webview1.getSettings().setJavaScriptEnabled(true);
        webview1.getSettings().setSupportZoom(true);
        webview1.getSettings().setBuiltInZoomControls(true);
        webview1.getSettings().setDisplayZoomControls(false);
        webview1.getSettings().setLoadWithOverviewMode(true);
        webview1.getSettings().setUseWideViewPort(true);
        webview1.setVerticalScrollBarEnabled(false);
        webview1.setHorizontalScrollBarEnabled(false);

        Intent intent = getIntent();
        String image_url = intent.getStringExtra("image_url");
        if (image_url != null) {
            webview1.loadUrl(image_url);
        }
        webview1.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView _param1, String _param2) {
                super.onPageFinished(_param1, _param2);
                webview1.setVisibility(View.VISIBLE);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
