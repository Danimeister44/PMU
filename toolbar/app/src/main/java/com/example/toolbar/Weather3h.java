package com.example.toolbar;


import java.io.Serializable;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Weather3h implements Serializable {
    public String getFormattedDate() {
        try {
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = parser.parse(dateTime);
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM HH:mm", Locale.getDefault());
            return formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateTime; // vrati original ako ne uspe parsiranje
        }
    }
    private double tempMin;
    private double tempMax;
    private String dateTime;
        private double temp;
        private int pressure;
        private int humidity;
        private double windSpeed;
        private int windDeg;
        private String description;
        private String icon;

    public Weather3h(String dateTime, double temp, double tempMin, double tempMax, int pressure, int humidity,
                     double windSpeed, int windDeg, String description, String icon) {
        this.dateTime = dateTime;
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.description = description;
        this.icon = icon;
    }


    public String getDateTime() {
            return dateTime;
        }

        public double getTemp() {
            return temp;
        }

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        public double getWindSpeed() {
            return windSpeed;
        }

        public int getWindDeg() {
            return windDeg;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }
}