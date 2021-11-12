package com.example.inclass09new;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {

    @PrimaryKey
    public int cid;

    @ColumnInfo(name="course_grade")
    public String grade;

    @ColumnInfo(name="course_title")
    public String title;

    @ColumnInfo(name="credit_hour")
    public int hour;
}
