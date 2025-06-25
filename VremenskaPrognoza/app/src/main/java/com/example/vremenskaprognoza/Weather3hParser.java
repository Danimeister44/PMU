package com.example.vremenskaprognoza;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Weather3hParser {
    public static List<Weather3h> parse(JSONObject jsonObject, int maxPoints) {
        List<Weather3h> forecastList = new ArrayList<>();

        try {
            JSONArray listArray = jsonObject.getJSONArray("list");

            for (int i = 0; i < listArray.length() && i < maxPoints; i++) {
                JSONObject item = listArray.getJSONObject(i);

                String dateTime = item.getString("dt_txt");

                JSONObject main = item.getJSONObject("main");
                double temp = main.getDouble("temp");
                int pressure = main.getInt("pressure");
                int humidity = main.getInt("humidity");

                JSONObject weather = item.getJSONArray("weather").getJSONObject(0);
                String description = weather.getString("description");
                String icon = weather.getString("icon");

                JSONObject wind = item.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                int windDeg = wind.getInt("deg");

                Weather3h weather3h = new Weather3h(dateTime, temp, pressure, humidity, windSpeed, windDeg, description, icon);
                forecastList.add(weather3h);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return forecastList;
    }
}