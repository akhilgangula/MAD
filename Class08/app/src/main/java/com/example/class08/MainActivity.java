package com.example.class08;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements LoginFragment.ILogin, RegisterFragment.IRegister, ForumsFragment.IForum, NewForumFragment.INewForum {

    final String FRAGMENT_TAG = "fragment_tag";
    final String FORUM_FRAGMENT_TAG = "FORUM_FRAGMENT_TAG";
    final String NEW_FORUM_FRAGMENT_TAG = "NEW_FORUM_FRAGMENT_TAG";

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
    public void launchRegister() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, RegisterFragment.newInstance(this), FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onLogin(String uuid) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, ForumsFragment.newInstance(this, uuid), FORUM_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onAccountCreation(String uuid) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, ForumsFragment.newInstance(this, uuid), FORUM_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onCancelRegistration() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, LoginFragment.newInstance(this), FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onLogout() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, LoginFragment.newInstance(this), FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void newForum(String uuid) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, NewForumFragment.newInstance(this, uuid), NEW_FORUM_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void createNewForum(String uuid) {
        ForumsFragment fragment = (ForumsFragment) getSupportFragmentManager().findFragmentByTag(FORUM_FRAGMENT_TAG);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerView, ForumsFragment.newInstance(this, uuid), FORUM_FRAGMENT_TAG)
                    .commit();
            return;
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelNewForum(String uuid) {
        ForumsFragment fragment = (ForumsFragment) getSupportFragmentManager().findFragmentByTag(FORUM_FRAGMENT_TAG);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerView, ForumsFragment.newInstance(this, uuid), FORUM_FRAGMENT_TAG)
                    .commit();
            return;
        }
        getSupportFragmentManager().popBackStack();

    }
}