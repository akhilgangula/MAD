package com.example.hw03;

import java.util.List;

class ForeCastEntry extends CurrentWeatherResponse {
    String dt_txt;
}

public class ForecastResponse {
    List<ForeCastEntry> list;
}
