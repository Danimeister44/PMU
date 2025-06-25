package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weatherapp.R;

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

                PreferencesManager.savePreferences(SettingsActivity.this, selectedCity, days, units);



                Intent resultIntent = new Intent();
                resultIntent.putExtra("city", selectedCity);
                resultIntent.putExtra("days", days);
                resultIntent.putExtra("units", units);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}