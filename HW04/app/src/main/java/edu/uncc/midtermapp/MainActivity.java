package edu.uncc.midtermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener, SignUpFragment.SignUpFragmentListener {
    UserToken mUserToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void createAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SignUpFragment())
                .commit();
    }

    @Override
    public void loginSuccessfulGotoPosts(UserToken userToken) {
        mUserToken = userToken;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, PostsListFragment.newInstance(mUserToken))
                .commit();
    }

    @Override
    public void cancelSignUp() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void registerSuccessfulGotoPosts(UserToken userToken) {
        mUserToken = userToken;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, PostsListFragment.newInstance(mUserToken))
                .commit();
    }
}