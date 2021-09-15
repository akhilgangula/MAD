package com.example.inclass04;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginListener, RegisterFragment.IRegisterListener, UpdateAccountFragment.IUpdateListener, AccountFragment.IAccountListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_id, new LoginFragment(), getString(R.string.fragment_tag))
                .commit();
    }

    @Override
    public void onCreate() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, new RegisterFragment(), getString(R.string.fragment_tag))
                .commit();
    }

    @Override
    public void onLogin(DataServices.Account account) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, AccountFragment.newInstance(account), getString(R.string.account_fragment))
                .commit();
    }

    @Override
    public void onSubmit(DataServices.Account account) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, AccountFragment.newInstance(account), getString(R.string.account_fragment))
                .commit();
    }

    @Override
    public void onCancel() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, new LoginFragment(), getString(R.string.fragment_tag))
                .commit();
    }

    @Override
    public void onUpdateSubmit(DataServices.Account account) {

        AccountFragment fragment = (AccountFragment)getSupportFragmentManager().findFragmentByTag(getString(R.string.account_fragment));
        if(fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_id, AccountFragment.newInstance(account), getString(R.string.account_fragment))
                    .commit();
            return;
        }
        fragment.updateAccount(account);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onUpdateCancel() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onEditProfile(DataServices.Account account) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, UpdateAccountFragment.newInstance(account), getString(R.string.fragment_tag))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLogOut() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, new LoginFragment(), getString(R.string.fragment_tag))
                .commit();
    }
}