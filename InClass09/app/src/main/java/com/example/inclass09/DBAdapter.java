package com.example.inclass09;

import android.content.Context;

import androidx.room.Room;

public class DBAdapter {
    private static CourseDatabase instance;

    public void setInstance(Context context) {
        instance = Room.databaseBuilder(context,
                CourseDatabase.class, "database-name").allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public static CourseDatabase getInstance() {
        if(instance == null) {
            throw new IllegalStateException("Initialize the DB");
        }
        return instance;
    }
}
