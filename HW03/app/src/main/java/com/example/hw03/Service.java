package com.example.hw03;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.hw03.bean.CurrentWeatherResponse;
import com.example.hw03.bean.ForecastResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Service {
    static OkHttpClient client = new OkHttpClient();

    public static void getCurrentWeather(Data.City city, Handler handler) {

        HttpUrl request = HttpUrl
                .parse(Util.BASE_URL + Util.CURR_WEATHER_PATH)
                .newBuilder()
                .addQueryParameter(Util.CITY_KEY, city.getCity())
                .addQueryParameter(Util.APP_KEY, Util.API_KEY)
                .addQueryParameter(Util.UNIT_KEY, Util.UNIT_VALUE)
                .build();
        client.newCall(new Request.Builder().url(request).build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Message message = new Message();
                message.arg1 = -1;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                    Message message = new Message();
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    CurrentWeatherResponse currentWeatherResponse = gson.fromJson(response.body().charStream(), CurrentWeatherResponse.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Util.CURR_WEATHER_RESPONSE_KEY, (Serializable) currentWeatherResponse);
                    message.arg1 = 1;
                    message.setData(bundle);
                } else {
                    message.arg1 = 0;
                }
                    handler.sendMessage(message);
            }
        });
    }

    public static void getForecastWeather(Data.City city, Handler handler) {

        HttpUrl request = HttpUrl
                .parse(Util.BASE_URL + Util.FORECAST_WEATHER_PATH)
                .newBuilder()
                .addQueryParameter(Util.CITY_KEY, city.getCity())
                .addQueryParameter(Util.APP_KEY, Util.API_KEY)
                .addQueryParameter(Util.UNIT_KEY, Util.UNIT_VALUE)
                .build();
        client.newCall(new Request.Builder().url(request).build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Message message = new Message();
                message.arg1 = -1;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                    Message message = new Message();
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    ForecastResponse currentWeatherResponse = gson.fromJson(response.body().charStream(), ForecastResponse.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Util.FORECAST_WEATHER_RESPONSE_KEY, (Serializable) currentWeatherResponse.list);
                    message.setData(bundle);
                    message.arg1 = 1;
                } else {
                    message.arg1 = 0;
                }
                    handler.sendMessage(message);
            }
        });
    }
}
