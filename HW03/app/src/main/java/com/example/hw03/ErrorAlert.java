package com.example.hw03;

import android.app.AlertDialog;
import android.content.Context;

public class ErrorAlert {
    public static void showError(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
        builder.create().show();
    }
}
