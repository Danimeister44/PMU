package com.example.toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class AirPollutionActivity extends AppCompatActivity {

    TextView textViewIndex, textView2Opis;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_pollution);
        layout = findViewById(R.id.main);
        textViewIndex = findViewById(R.id.textViewIndex);
        textView2Opis = findViewById(R.id.textView2Opis);
        String cityName = getIntent().getStringExtra("city");
        TextView textViewCity = findViewById(R.id.textViewCity);
        textViewCity.setText("City: " + cityName);

        double lat = getIntent().getDoubleExtra("lat", 0);
        double lon = getIntent().getDoubleExtra("lon", 0);
        String apiKey = "ec3587d76f1911b937ac26130a5ef814";

        String url = "https://api.openweathermap.org/data/2.5/air_pollution?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey;

        Log.d("AIR_URL", url);  // for debugging

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray list = response.getJSONArray("list");
                        JSONObject obj = list.getJSONObject(0);
                        JSONObject main = obj.getJSONObject("main");
                        int aqi = main.getInt("aqi");

                        JSONObject components = obj.getJSONObject("components");
                        double so2 = components.getDouble("so2");
                        double no2 = components.getDouble("no2");
                        double pm25 = components.getDouble("pm2_5");
                        double pm10 = components.getDouble("pm10");
                        double o3 = components.getDouble("o3");


                        String description = getAqiDescription(aqi);
                        String indexText = "AQI index: " + aqi + "\n"
                                + "SO₂: " + so2 + " μg/m³\n"
                                + "NO₂: " + no2 + " μg/m³\n"
                                + "PM2.5: " + pm25 + " μg/m³\n"
                                + "PM10: " + pm10 + " μg/m³\n"
                                + "O₃: " + o3 + " μg/m³\n";

                        String descriptionText = "Description: " + description;
                        textViewIndex.setText(indexText);
                        textView2Opis.setText(descriptionText);

                    } catch (Exception e) {
                        e.printStackTrace();
                        textViewIndex.setText("Parsing error");
                        textView2Opis.setText(e.getMessage());
                    }
                },
                error -> {
                    error.printStackTrace();
                    textViewIndex.setText("API request failed.");
                    textView2Opis.setText(error.toString());
                }
        );

        queue.add(request);
    }

    private String getAqiDescription(int aqi) {
        switch (aqi) {
            case 1:
                layout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_bright));
                return "Good";
            case 2:
                layout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
                return "Fair";
            case 3:
                layout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_orange_light));
                return "Moderate";
            case 4:
                layout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light));
                return "Poor";
            case 5:
                layout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                return "Very Poor";
            default:
                layout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_purple));
                return "Unknown";
        }
    }
}