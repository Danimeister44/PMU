package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    List<Weather3h> prognoza;

    private ListView listView;

    public static final Map<String, double[]> cityCoordinates = new HashMap<>();
    static {
        cityCoordinates.put("Subotica", new double[]{46.1, 19.6667});
        cityCoordinates.put("Novi Sad", new double[]{45.2517, 19.8369});
        cityCoordinates.put("Beograd", new double[]{44.804, 20.4651});
        cityCoordinates.put("San Francisko", new double[]{37.7749, -122.4194});
        cityCoordinates.put("Sidnej", new double[]{-33.8679, 151.2073});
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

listView=findViewById(R.id.listView);

        // Assigning ID of the toolbar to a variable
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Using toolbar as ActionBar
        setSupportActionBar(toolbar);

        // Display application icon in the toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.weather_clear_symbolic);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
        loadForecast();
    }

    private void loadForecast() {
        String city = PreferencesManager.getCity(this);
        int days = PreferencesManager.getDays(this);
        String units = PreferencesManager.getUnits(this);

        Log.d("SETTINGS", "City: " + city + ", Days: " + days + ", Units: " + units);

        try {
            // Učitavanje lokalnog JSON fajla
            InputStream is = getAssets().open("forecast_sample_40.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonString = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonString);

            // Računamo maksimalan broj vremenskih tačaka
            int maxPoints = days * 8; // jer ima 8 tačaka dnevno na svakih 3h

            // Parsiramo podatke
            List<Weather3h> prognoza = Weather3hParser.parse(jsonObject, maxPoints);

            // Povezujemo adapter sa ListView-om
            Weather3hAdapter adapter = new Weather3hAdapter(this, prognoza);
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                //azuriraj podatke
                return true;

            case R.id.menu_pollution:
                String city = PreferencesManager.getCity(this);
                double[] coords = cityCoordinates.get(city);

                if (coords != null) {
                    double lat = coords[0];
                    double lon = coords[1];

                    Intent pollutionIntent = new Intent(this, AirPollutionActivity.class);
                    pollutionIntent.putExtra("lat", lat);
                    pollutionIntent.putExtra("lon", lon);
                    startActivity(pollutionIntent);
                } else {
                    Toast.makeText(this, "Coordinates not found for selected city", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onResume() {
        super.onResume();
        loadForecast();
    }



/*
    private void loadSettings1() {
        String city = PreferencesManager.getCity(this);
        int days = PreferencesManager.getDays(this);
        String units = PreferencesManager.getUnits(this);

        double[] coords = cityCoordinates.get(city);
        if (coords == null) {
            Toast.makeText(this, "Unknown city: " + city, Toast.LENGTH_SHORT).show();
            return;
        }

        String apiKey = getString(R.string.weather_api_key);
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=%s&appid=%s",
                coords[0], coords[1], units, apiKey
        );

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        prognoza = WWeatherParser.parse(jsonObject, maxPoints);;
                        WeatherAdapter adapter = new WeatherAdapter(MainActivity.this, prognoza);
                        ListView listView = findViewById(R.id.listView);
                        listView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "Failed to load weather data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(request);
    }
*/
}