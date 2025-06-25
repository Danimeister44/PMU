package com.example.toolbar;


    public class Weather3h {
        private String dateTime;
        private double temp;
        private int pressure;
        private int humidity;
        private double windSpeed;
        private int windDeg;
        private String description;
        private String icon;

        public Weather3h(String dateTime, double temp, int pressure, int humidity,
                         double windSpeed, int windDeg, String description, String icon) {
            this.dateTime = dateTime;
            this.temp = temp;
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
    }
