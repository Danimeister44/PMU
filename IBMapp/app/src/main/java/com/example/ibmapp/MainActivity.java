package com.example.ibmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // UI elements
        EditText ime = findViewById(R.id.editTextIme);
        RadioGroup pol = findViewById(R.id.pol);
        EditText godine = findViewById(R.id.editTextNumberGodine);
        EditText visina = findViewById(R.id.editTextNumberVisina);
        EditText tezina = findViewById(R.id.editTextNumberTezina);
        Button posalji = findViewById(R.id.buttonSend);

        posalji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ime.getText().toString();
                int genderId = pol.getCheckedRadioButtonId();
                RadioButton selectedRadioCity = findViewById(genderId);
                String gender = selectedRadioCity.getText().toString();
                String age = godine.getText().toString();
                String height = visina.getText().toString();
                String weight = tezina.getText().toString();

                Intent result = new Intent(MainActivity.this, activity_result.class);
                result.putExtra("name", name);
                result.putExtra("gender", gender);
                result.putExtra("age", age);
                result.putExtra("height", height);
                result.putExtra("weight", weight);

                startActivity(result);
                overridePendingTransition(R.anim.slide_out_left, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.BMI_INFO) {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.EXIT) {
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
