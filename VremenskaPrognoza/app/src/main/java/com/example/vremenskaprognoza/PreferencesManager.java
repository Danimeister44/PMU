package com.example.vremenskaprognoza;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_CITY = "city";
    private static final String KEY_DAYS = "days";
    private static final String KEY_UNITS = "units";

    public static void savePreferences(Context context, String city, int days, String units) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_CITY, city);
        editor.putInt(KEY_DAYS, days);
        editor.putString(KEY_UNITS, units);
        editor.apply();
    }

    public static String getCity(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_CITY, "Subotica"); // default: Subotica
    }

    public static int getDays(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_DAYS, 7); // default: 7 dana
    }

    public static String getUnits(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_UNITS, "metric"); // default: metric
    }
}