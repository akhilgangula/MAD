package com.example.inclass09;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements GradesFragment.IGradesAction, CourseFragment.IAddCourse {

    private static final String FRAGMENT_TAG = "fragment_tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DBAdapter().setInstance(getBaseContext());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.containerView, GradesFragment.newInstance(this), FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onAddCourseClick() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, CourseFragment.newInstance(this), FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onGradeDelete() {

    }

    @Override
    public void onCourseAdd() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, GradesFragment.newInstance(this), FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onCancelAdd() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, GradesFragment.newInstance(this), FRAGMENT_TAG)
                .commit();
    }
}