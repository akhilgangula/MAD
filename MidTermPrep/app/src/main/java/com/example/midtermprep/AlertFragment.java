package com.example.midtermprep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlertFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertFragment extends Fragment {

    public AlertFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_alert, container, false);
        view.findViewById(R.id.showAlertButton).setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Error")
                    .setMessage("Unable to perform task")
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        Toast.makeText(view.getContext(), "OK Called", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Close", (dialogInterface, i) -> {
                        Toast.makeText(view.getContext(), "Close Called", Toast.LENGTH_SHORT).show();
                    })
                    .setNeutralButton("Cancel", (dialogInterface, i) -> {
                        Toast.makeText(view.getContext(), "Cancel Called", Toast.LENGTH_SHORT).show();
                    });
            builder.create().show();
        });
        return view;
    }
}