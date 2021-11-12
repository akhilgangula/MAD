package com.example.hw05;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Forum implements Serializable {
    public String uuid, title, desc;
    @ServerTimestamp
    public Date date;
    @Exclude
    String id;
    @PropertyName("like")
    public Map<String, Boolean> likeMap;

    public Forum() {
        likeMap = new HashMap<>();
    }

    public Forum(String uuid, String title, String desc) {
        this.likeMap = new HashMap<>();
        this.uuid = uuid;
        this.title = title;
        this.desc = desc;
    }
}
