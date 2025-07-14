package com.example.toolbar;

public class CityHistory {
    private String city;
    private String lastAccess;
    private double temp;
    private String description;

    public CityHistory(String city, String lastAccess, double temp, String description) {
        this.city = city;
        this.lastAccess = lastAccess;
        this.temp = temp;
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public String getLastAccess() {
        return lastAccess;
    }

    public double getTemp() {
        return temp;
    }

    public String getDescription() {
        return description;
    }
}
