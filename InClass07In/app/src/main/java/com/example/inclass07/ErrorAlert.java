package com.example.inclass07;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class ErrorAlert {
    public static void showError(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
//                .setNegativeButton("Close", (dialogInterface, i) -> {
//                    Toast.makeText(context, "Close Called", Toast.LENGTH_SHORT).show();
//                })
//                .setNeutralButton("Cancel", (dialogInterface, i) -> {
//                    Toast.makeText(context, "Cancel Called", Toast.LENGTH_SHORT).show();
//                });
        builder.create().show();
    }
}
