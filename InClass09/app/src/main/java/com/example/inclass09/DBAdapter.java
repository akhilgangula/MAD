package com.example.inclass09;

import android.content.Context;

import androidx.room.Room;

public class DBAdapter {
    private static CourseDatabase instance;
    private static final String DB_NAME = "course-app";
    public void setInstance(Context context) {
        instance = Room.databaseBuilder(context,
                CourseDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public static CourseDatabase getInstance() {
        if(instance == null) {
            throw new IllegalStateException("Initialize the DB");
        }
        return instance;
    }
}
