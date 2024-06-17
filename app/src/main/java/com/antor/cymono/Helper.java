package com.antor.cymono;

import android.content.Context;
import android.widget.Toast;

public class Helper {

    public void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
