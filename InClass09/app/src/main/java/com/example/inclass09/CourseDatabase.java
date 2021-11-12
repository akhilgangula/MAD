package com.example.inclass09;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class CourseDatabase extends RoomDatabase {
    public abstract  CourseDao courseDao();
}