package com.example.hw03.bean;

import java.io.Serializable;
import java.util.List;

public class CurrentWeatherResponse implements Serializable {
    public Main main;
    public List<Weather> weather;
    public Wind wind;
    public Clouds clouds;
}

