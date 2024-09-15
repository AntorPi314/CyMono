package com.antor.cymono;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.Window;

public class UIUtils {
    public static void setStatusBarAndNavigationBar(Activity activity) {
        Window window = activity.getWindow();
        window.setStatusBarColor(Color.parseColor("#2A2E37"));
        window.setNavigationBarColor(Color.parseColor("#2A2E37"));

        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }
}

