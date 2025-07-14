package com.example.toolbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class WeatherDatabaseHelper extends SQLiteOpenHelper {
    //KREIRANJE BASE
    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 4;
    //KREIRANJE TABELE ZA VREMENSKU PROGNOZU
    public static final String TABLE_FORECAST = "forecast";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_DATETIME = "datetime";
    public static final String COLUMN_TEMP = "temp";
    public static final String COLUMN_TEMP_MIN = "temp_min";
    public static final String COLUMN_TEMP_MAX = "temp_max";
    public static final String COLUMN_PRESSURE = "pressure";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_WIND_SPEED = "wind_speed";
    public static final String COLUMN_WIND_DEG = "wind_deg";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ICON = "icon";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_FORECAST + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CITY + " TEXT, " +
                    COLUMN_DATETIME + " TEXT, " +
                    COLUMN_TEMP + " REAL, " +              // <-- SPACE after column name!
                    COLUMN_TEMP_MIN + " REAL, " +
                    COLUMN_TEMP_MAX + " REAL, " +
                    COLUMN_PRESSURE + " INTEGER, " +
                    COLUMN_HUMIDITY + " INTEGER, " +
                    COLUMN_WIND_SPEED + " REAL, " +
                    COLUMN_WIND_DEG + " INTEGER, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_ICON + " TEXT" +
                    ");";
    //KREIARANJE TABELE ZA ISTORIJU
    public static final String TABLE_HISTORY = "search_history";
    public static final String COLUMN_HISTORY_ID = "_id";
    public static final String COLUMN_HISTORY_CITY = "city";
    public static final String COLUMN_HISTORY_LAST_ACCESS = "last_access";
    public static final String COLUMN_HISTORY_TEMP = "temp";
    public static final String COLUMN_HISTORY_DESCRIPTION = "description";

    private static final String HISTORY_TABLE_CREATE =
            "CREATE TABLE " + TABLE_HISTORY + " (" +
                    COLUMN_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_HISTORY_CITY + " TEXT UNIQUE, " +  // UNIQUE da ne dupliraš gradove
                    COLUMN_HISTORY_LAST_ACCESS + " TEXT, " +
                    COLUMN_HISTORY_TEMP + " REAL, " +  // <-- add space here too!
                    COLUMN_HISTORY_DESCRIPTION + " TEXT" +
                    ");";

    public WeatherDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(HISTORY_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORECAST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    public void insertForecast(Weather3h weather, String city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, city);
        values.put(COLUMN_DATETIME, weather.getDateTime());
        values.put(COLUMN_TEMP, weather.getTemp());
        values.put(COLUMN_TEMP_MIN, weather.getTempMin());
        values.put(COLUMN_TEMP_MAX, weather.getTempMax());
        values.put(COLUMN_PRESSURE, weather.getPressure());
        values.put(COLUMN_HUMIDITY, weather.getHumidity());
        values.put(COLUMN_WIND_SPEED, weather.getWindSpeed());
        values.put(COLUMN_WIND_DEG, weather.getWindDeg());
        values.put(COLUMN_DESCRIPTION, weather.getDescription());
        values.put(COLUMN_ICON, weather.getIcon());

        db.insert(TABLE_FORECAST, null, values);
        db.close();
    }

    public List<Weather3h> getForecasts(String city) {
        List<Weather3h> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FORECAST, null, COLUMN_CITY + "=?",
                new String[]{city}, null, null, COLUMN_DATETIME + " ASC");

        while (cursor.moveToNext()) {
            Weather3h weather = new Weather3h(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATETIME)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TEMP)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TEMP_MIN)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TEMP_MAX)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRESSURE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HUMIDITY)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WIND_SPEED)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WIND_DEG)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ICON))
            );
            list.add(weather);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void insertOrUpdateHistory(String city, String lastAccess, double temp, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_HISTORY_CITY, city);
        values.put(COLUMN_HISTORY_LAST_ACCESS, lastAccess);
        values.put(COLUMN_HISTORY_TEMP, temp);
        values.put(COLUMN_HISTORY_DESCRIPTION, description);

        // Pokušaj da update-uješ ako grad već postoji
        int rows = db.update(TABLE_HISTORY, values, COLUMN_HISTORY_CITY + "=?", new String[]{city});
        if (rows == 0) {
            // Ako ne postoji, ubaci novi zapis
            db.insert(TABLE_HISTORY, null, values);
        }
        db.close();
    }

    public List<CityHistory> getSearchHistory() {
        List<CityHistory> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HISTORY, null, null, null, null, null, COLUMN_HISTORY_LAST_ACCESS + " DESC");

        while (cursor.moveToNext()) {
            CityHistory item = new CityHistory(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HISTORY_CITY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HISTORY_LAST_ACCESS)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_HISTORY_TEMP)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HISTORY_DESCRIPTION))
            );
            list.add(item);
        }

        cursor.close();
        db.close();
        return list;
    }
}
