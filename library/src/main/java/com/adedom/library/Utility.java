package com.adedom.library;

import android.content.Context;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.io.File;

public class Utility {

    private Context context;

    public Utility(Context context) {
        this.context = context;
    }

    public void showLong(int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showLong(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showShort(int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showShort(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void uploadProfile(String imageUri) {
        Ion.with(context)
                .load(Pathiphon.BASE_URL + "upload-profile.php")
                .setMultipartFile("file", new File(imageUri.trim()))
                .asString();
    }
}
