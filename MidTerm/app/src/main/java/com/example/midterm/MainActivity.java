package com.example.midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.midterm.response.AuthenticationResponse;

public class MainActivity extends AppCompatActivity implements LoginFragment.IHome, RegisterFragment.IRegister, PostListFragment.IPosts, CreatePostFragment.ICreatePost {

    private static final String FRAGMENT_TAG = "fragment_tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.containerView, LoginFragment.newInstance(this), FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void registerUser() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, RegisterFragment.newInstance(this), FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToHomePage() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, LoginFragment.newInstance(this), FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void launchList(AuthenticationResponse response) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, PostListFragment.newInstance(this, response), FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void createPost(AuthenticationResponse response) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, CreatePostFragment.newInstance(this, response), FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logout() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, LoginFragment.newInstance(this), FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onCreatePost(AuthenticationResponse response) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, PostListFragment.newInstance(this, response), FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelCreatePost(AuthenticationResponse response) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, PostListFragment.newInstance(this, response), FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }
}