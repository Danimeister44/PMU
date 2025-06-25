package com.example.ibmcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //povezujemo promenljive sa id-evima.
        EditText ime = findViewById(R.id.editTextIme);
        RadioGroup pol = findViewById(R.id.pol);
        RadioButton muski = findViewById(R.id.radioButtonM);
        RadioButton zenski = findViewById(R.id.radioButtonZ);
        EditText godine = findViewById(R.id.editTextNumberGodine);
        EditText visina = findViewById(R.id.editTextNumberVisina);
        EditText tezina = findViewById(R.id.editTextNumberTezina);
        Button posalji = findViewById(R.id.buttonSend);
        //dugme on click metoda.
        posalji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uzimamo ime.
                String name = ime.getText().toString();
                //uzimamo grupu dugmadi i onda se posalje id od jednog od dugmeta
                // koje je izabrano.
                int genderId = pol.getCheckedRadioButtonId();
                RadioButton selectedRadioCity = findViewById(genderId);
                String gender = selectedRadioCity.getText().toString();
                //uzimamo godine.
                String age = godine.getText().toString();
                //uzimamo visinu.
                String height = visina.getText().toString();
                //uzimamo tezinu.
                String weight = tezina.getText().toString();
                //kreiramo intent za slanje podataka na novi activity.
                Intent result = new Intent(MainActivity.this, com.example.ibmcalculator.activity_result.class);
                //prenosimo pet podataka: ime, pol, godine, visinu i tezinu.
                result.putExtra("name", name);
                result.putExtra("gender", gender);
                result.putExtra("age", age);
                result.putExtra("height", height);
                result.putExtra("weight", weight);

                startActivity(result);
            }
        });
    }
}