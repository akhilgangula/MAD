package com.example.inclass09;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    @PrimaryKey
    @NonNull
    public String cid;

    @ColumnInfo(name="course_grade")
    public String grade;

    @ColumnInfo(name="course_title")
    public String title;

    @ColumnInfo(name="credit_hour")
    public int hour;

    public Course(String cid, String grade, String title, int hour) {
        this.cid = cid;
        this.grade = grade;
        this.title = title;
        this.hour = hour;
    }
}