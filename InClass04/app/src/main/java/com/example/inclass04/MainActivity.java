package com.example.inclass04;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginListener, RegisterFragment.IRegisterListener, UpdateAccountFragment.IUpdateListener, AccountFragment.IAccountListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.login));
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_id, new LoginFragment(), getString(R.string.fragment_tag))
                .commit();
    }

    @Override
    public void onCreate() {
        setTitle(getString(R.string.register_account));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, new RegisterFragment(), getString(R.string.fragment_tag))
                .commit();
    }

    @Override
    public void onLogin(DataServices.Account account) {
        setTitle(getString(R.string.account));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, AccountFragment.newInstance(account), getString(R.string.account_fragment))
                .commit();
    }

    @Override
    public void onSubmit(DataServices.Account account) {
        setTitle(getString(R.string.account));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, AccountFragment.newInstance(account), getString(R.string.account_fragment))
                .commit();
    }

    @Override
    public void onCancel() {
        setTitle(getString(R.string.login));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, new LoginFragment(), getString(R.string.fragment_tag))
                .commit();
    }

    @Override
    public void onUpdateSubmit(DataServices.Account account) {
        setTitle(getString(R.string.account));
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
        setTitle(getString(R.string.account));
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onEditProfile(DataServices.Account account) {
        setTitle(getString(R.string.update_account));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, UpdateAccountFragment.newInstance(account), getString(R.string.fragment_tag))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLogOut() {
        setTitle(getString(R.string.login));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_id, new LoginFragment(), getString(R.string.fragment_tag))
                .commit();
    }
}