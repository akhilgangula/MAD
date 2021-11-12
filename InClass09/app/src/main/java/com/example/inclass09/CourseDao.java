package com.example.inclass09;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM course")
    List<Course> getAll();

    @Delete
    void delete(Course course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Course... course);
}