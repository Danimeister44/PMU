package com.example.toolbar;

import static com.example.toolbar.MainActivity.cityCoordinates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvCityName, textViewDateTime, textViewDescription, textViewTempRange, textViewPressure, textViewHumidity, textViewWind;
    private ImageView imageViewIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Assigning ID of the toolbar to a variable
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Using toolbar as ActionBar
        setSupportActionBar(toolbar);
        //Klikom na toolbar nas vraća nazad.
        // Enable title to be clickable
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to MainActivity
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                // Optional: Clear activity stack to avoid multiple instances
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });


        // Display application icon in the toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.weather_clear_symbolic);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String units = prefs.getString("units", "metric"); // default is metric (C)
        String cityName = getIntent().getStringExtra("city");

        tvCityName = findViewById(R.id.tvCityName);
        textViewDateTime = findViewById(R.id.textViewDateTime);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewTempRange = findViewById(R.id.textViewTempRange);
        textViewPressure = findViewById(R.id.textViewPressure);
        textViewHumidity = findViewById(R.id.textViewHumidity);
        textViewWind = findViewById(R.id.textViewWind);
        imageViewIcon = findViewById(R.id.imageViewIcon);


        Weather3h weather = (Weather3h) getIntent().getSerializableExtra("weather");

        if (weather != null) {

            tvCityName.setText(cityName);
            textViewDateTime.setText(weather.getDateTime());
            textViewDescription.setText(weather.getDescription());
            textViewTempRange.setText(String.format("%.1f°C", weather.getTemp()));
            textViewPressure.setText("Pressure: " + weather.getPressure() + " hPa");
            textViewHumidity.setText("Humidity: " + weather.getHumidity() + "%");
            textViewWind.setText("Wind: " + weather.getWindSpeed() + " m/s");

            String iconUrl = "https://openweathermap.org/img/wn/" + weather.getIcon() + "@2x.png";
            Glide.with(this).load(iconUrl).into(imageViewIcon);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            searchItem.setVisible(false);
        }
        return true;

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
                String cityName = getIntent().getStringExtra("city");
                if (cityName == null || cityName.isEmpty()) {
                    Toast.makeText(this, "City name is missing", Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent intent1 = new Intent(this, HistoryActivity.class);
                intent1.putExtra("city", cityName);
                startActivity(intent1);
                return true;

            case R.id.menu_refresh:
                Intent intent2 = new Intent(this, MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
