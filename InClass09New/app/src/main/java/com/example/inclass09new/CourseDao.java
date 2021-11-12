package com.example.inclass09new;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM course")
    List<Course> getAll();

    @Delete
    void delete(Course course);

    @Insert
    void insertAll(Course... course);
}
