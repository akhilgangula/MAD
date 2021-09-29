package com.example.homework02;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UserFragment.IHome, IFilterSort {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_view, new UserFragment(new Config(), this), getString(R.string.user_fragment_tag))
                .commit();
    }

    @Override
    public void filterUsers(Config config) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_view, FilterFragment.newInstance(config, this), getString(R.string.filter_fragment_tag))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void sortUsers(Config config) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_view, SortFragment.newInstance(config, this), getString(R.string.sort_fragment_tag))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void applyConfig(List<DataServices.User> users, Config config) {
        UserFragment fragment = (UserFragment)getSupportFragmentManager().findFragmentByTag(getString(R.string.user_fragment_tag));
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_view, UserFragment.newInstance(users, config, this), getString(R.string.user_fragment_tag))
                    .commit();
            return;
        }
        fragment.setConfig(config);
        fragment.setUsers(users);
        getSupportFragmentManager().popBackStack();
    }
}