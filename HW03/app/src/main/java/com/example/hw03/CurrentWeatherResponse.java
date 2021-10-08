package com.example.hw03;

import java.io.Serializable;
import java.util.List;

class Weather implements Serializable{
    String description, icon;
}

public class CurrentWeatherResponse implements Serializable {
    Main main;
    List<Weather> weather;
    Wind wind;
    Clouds clouds;
}

class Main implements Serializable{
    String temp, temp_min, temp_max, humidity;
}

class Wind implements Serializable{
    String speed,deg;
}

class Clouds implements Serializable{
    String all;
}