package com.example.vremenskaprognoza;

import static com.example.vremenskaprognoza.MainActivity.cityCoordinates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vremenskaprognoza.R;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    Spinner spinnerCity;
    RadioGroup radioGroupDays, radioGroupUnits;
    CheckBox checkBoxRemember;
    Button buttonSave;

    String[] cities = {"Subotica", "Novi Sad", "Beograd", "San Francisko", "Sidnej"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinnerCity = findViewById(R.id.spinnerCity);
        radioGroupDays = findViewById(R.id.radioGroupDays);
        radioGroupUnits = findViewById(R.id.radioGroupUnits);
        checkBoxRemember = findViewById(R.id.checkBoxRemember);
        buttonSave = findViewById(R.id.buttonSave);

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


        // Podesi spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedCity = spinnerCity.getSelectedItem().toString();

                int days = 7; // podrazumevano
                if (radioGroupDays.getCheckedRadioButtonId() == R.id.radio3days) {
                    days = 3;
                } else if (radioGroupDays.getCheckedRadioButtonId() == R.id.radio5days) {
                    days = 5;
                }

                String units = "metric";
                if (radioGroupUnits.getCheckedRadioButtonId() == R.id.radioImperial) {
                    units = "imperial";
                }

                boolean remember = checkBoxRemember.isChecked();

                if (remember) {
                    SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("city", selectedCity);
                    editor.putInt("days", days);
                    editor.putString("units", units);
                    editor.apply();
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("city", selectedCity);
                resultIntent.putExtra("days", days);
                resultIntent.putExtra("units", units);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
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
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}