package com.example.hw03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements CitiesFragment.ICity, CurrentFragment.ICurrent, ForecastFragment.IForeCast {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.containerView, CitiesFragment.newInstance(this), Util.FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onCitySelected(Data.City city) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, CurrentFragment.newInstance(city, this), Util.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void checkForeCast(Data.City city) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, ForecastFragment.newInstance(city, this), Util.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToMainPage() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goToBackToCurrentWeather() {
        getSupportFragmentManager().popBackStack();
    }
}