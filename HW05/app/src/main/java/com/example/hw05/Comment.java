package com.example.hw05;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Comment implements Serializable {
    public String userId, commentDesc;
    @ServerTimestamp
    public Date timeStamp;

    @Exclude
    String id;
    public Comment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Comment(String userId, String commentDesc) {
        this.userId = userId;
        this.commentDesc = commentDesc;
    }
}
