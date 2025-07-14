package com.example.toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.security.auth.callback.Callback;

import androidx.appcompat.widget.SearchView;





public class MainActivity extends AppCompatActivity {
    List<Weather3h> prognoza;
    private RecyclerView recyclerView;
    private WeatherDatabaseHelper dbHelper;
    private TextView tvOfflineMode; //ZA PROVERU DA LI IMAMO INTERNETA.

    public static final Map<String, double[]> cityCoordinates = new HashMap<>();
    static {
        cityCoordinates.put("Subotica", new double[]{46.1, 19.6667});
        cityCoordinates.put("Novi Sad", new double[]{45.2517, 19.8369});
        cityCoordinates.put("Beograd", new double[]{44.804, 20.4651});
        cityCoordinates.put("San Francisko", new double[]{37.7749, -122.4194});
        cityCoordinates.put("Sidnej", new double[]{-33.8679, 151.2073});
        //Ja dodao.
        cityCoordinates.put("Sombor", new double[]{45.7742, 19.1128});
        cityCoordinates.put("Svetozar Miletic", new double[]{45.7583, 19.2500});
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.weather_clear_symbolic);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        dbHelper = new WeatherDatabaseHelper(this);

        tvOfflineMode = findViewById(R.id.tvOfflineMode);

        loadForecast();
    }
    //PROVERAVA DA LI SMO ONLINE (WIFI).
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private void loadForecast() {
        String city = PreferencesManager.getCity(this);
        int days = PreferencesManager.getDays(this);
        String units = PreferencesManager.getUnits(this);

        Log.d("SETTINGS", "City: " + city + ", Days: " + days + ", Units: " + units);

        if (isOnline()) {
            Log.d("WeatherApp", "Loading data from API");
            tvOfflineMode.setVisibility(View.GONE);
            fetchForecastFromApi(city, days, units);
        } else {
            Log.d("WeatherApp", "No internet, loading data from SQLite");
            tvOfflineMode.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No internet connection, loading saved data", Toast.LENGTH_LONG).show();
            loadForecastFromDatabase(city);
        }
    }

    private void fetchForecastFromApi(String city, int days, String units) {
        try {
            double[] coords = cityCoordinates.get(city);
            if (coords == null) {
                Toast.makeText(this, "City coordinates not found", Toast.LENGTH_SHORT).show();
                return;
            }
            double lat = coords[0];
            double lon = coords[1];

            String apiKey = "ec3587d76f1911b937ac26130a5ef814";

            String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat +
                    "&lon=" + lon +
                    "&units=" + units +
                    "&appid=" + apiKey;

            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            int maxPoints = days * 8;
                            List<Weather3h> prognoza = Weather3hParser.parse(response, maxPoints);

                            // Save forecast to DB
                            saveForecastToDatabase(city, prognoza);

                            // Save city history
                            if (!prognoza.isEmpty()) {
                                Weather3h latestWeather = prognoza.get(0);
                                saveCityHistory(city, latestWeather);
                            }

                            // Show data in RecyclerView
                            Weather3hAdapterRecycler adapter = new Weather3hAdapterRecycler(this, prognoza, city);
                            recyclerView.setAdapter(adapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "API error", Toast.LENGTH_SHORT).show();
                        loadForecastFromDatabase(city);
                    });
            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveCityHistory(String city, Weather3h weather) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.insertOrUpdateHistory(city, weather.getDateTime(), weather.getTemp(), weather.getDescription());
    }

    private void saveForecastToDatabase(String city, List<Weather3h> forecasts) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(WeatherDatabaseHelper.TABLE_FORECAST, WeatherDatabaseHelper.COLUMN_CITY + "=?", new String[]{city});

        for (Weather3h weather : forecasts) {
            dbHelper.insertForecast(weather, city);
        }
    }

    private void loadForecastFromDatabase(String city) {
        List<Weather3h> savedForecasts = dbHelper.getForecasts(city);
        if (savedForecasts.isEmpty()) {
            Toast.makeText(this, "No saved data available", Toast.LENGTH_SHORT).show();
        } else {
            Weather3hAdapterRecycler adapter = new Weather3hAdapterRecycler(this, savedForecasts, city);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate menu_main.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        // Pronađi Search item
        MenuItem searchItem = menu.findItem(R.id.action_search);
        // Pretvori ga u SearchView
        SearchView searchView = (SearchView) searchItem.getActionView();
        // Postavi hint
        searchView.setQueryHint("Enter city...");
        // Listener za pretragu
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Sačuvaj grad u Preferences
                SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("city", query);
                editor.apply();
                // Ponovo učitaj prognozu sa novim gradom i podešavanjima
                loadForecast();
                // Zatvori tastaturu i collapse SearchView
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // Ne reagujemo dok kuca
                return false;
            }
        });
        return true; // obavezno true da se meni prikaže
    }
    public void fetchWeatherByCity(String cityName) {
        String apiKey = "ec3587d76f1911b937ac26130a5ef814";  // stavi svoj API ključ ovde
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + cityName + "&appid=" + apiKey + "&units=metric";
        // Kreiraj JSON zahtev
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Obrada JSON odgovora
                        try {
                            String city = response.getString("name");
                            JSONObject main = response.getJSONObject("main");
                            double temp = main.getDouble("temp");
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject weather = weatherArray.getJSONObject(0);
                            String description = weather.getString("description");
                            // Na primer: prikaži ili koristi podatke dalje
                            Log.d("Weather", "Grad: " + city + ", Temp: " + temp + "°C, Opis: " + description);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Obrada greške
                        Log.e("Weather", "Greška pri dohvatu podataka: " + error.getMessage());
                    }
                }
        );
        // Dodaj zahtev u RequestQueue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }



    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pollution:
                String city = PreferencesManager.getCity(this);
                double[] coords = cityCoordinates.get(city);

                if (coords != null) {
                    double lat = coords[0];
                    double lon = coords[1];

                    Intent pollutionIntent = new Intent(this, AirPollutionActivity.class);
                    pollutionIntent.putExtra("lat", lat);
                    pollutionIntent.putExtra("lon", lon);
                    pollutionIntent.putExtra("city", city);
                    startActivity(pollutionIntent);
                } else {
                    Toast.makeText(this, "Coordinates not found for selected city", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, 1);
                return true;

            case R.id.menu_history:
                Intent intent1 = new Intent(this, HistoryActivity.class);
                startActivity(intent1);  // Fixed: start the activity here
                return true;

            case R.id.menu_refresh:
                loadForecast();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadForecast();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String city = data.getStringExtra("city");
            int days = data.getIntExtra("days", 3);
            String units = data.getStringExtra("units");

            SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("city", city);
            editor.putInt("days", days);
            editor.putString("units", units);
            editor.apply();

            loadForecast();
        }
    }
}