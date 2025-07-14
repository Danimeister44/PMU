package com.example.toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;

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
                            Weather3hAdapter adapter = new Weather3hAdapter(MainActivity.this, prognoza);
                            listView.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, "API error", Toast.LENGTH_SHORT).show();
                    });

            queue.add(request);


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
                startActivityForResult(intent, 1); // Request code 1
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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

            // Save manually
            SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("city", city);
            editor.putInt("days", days);
            editor.putString("units", units);
            editor.apply();

            loadForecast(); // Refresh
        }
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
                        prognoza = Weather3hParser(jsonObject, maxPoints);;
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