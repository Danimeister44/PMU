package com.example.ibmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_result extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        //dugme za treci activity.
        Button detalji = findViewById(R.id.buttonDetail);


        //Rezultat mora ovde.
        TextView Rezultat = findViewById(R.id.textViewResultBMI);
        //uzimamo podatke iz intenta sa MainActivity.java.
        //prenosimo pet podataka: ime, pol, godine, visinu i tezinu.
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String gender = intent.getStringExtra("gender");
        String age = intent.getStringExtra("age");
        String height = intent.getStringExtra("height");
        String weight = intent.getStringExtra("weight");

        //u TextView sa id ispis se upisuju podaci.
        TextView ispisano = findViewById(R.id.ispis);
        //ispis svih podataka.
        ispisano.setText("Ime: "+name+"\n"+"Pol: "+gender+"\n"
                +"Godine: "+age+"\n"+"Visina: "+height+" cm"+"\n"
                +"Težina: "+weight+" kg"+"\n");
        //nekako ispisati bmi kalkulaciju.
        float w = Float.parseFloat(weight);
        float h = Float.parseFloat(height) / 100;
        float bmi = w / (h*h);
        // 0.0f je podrazumevana vrednost.
        //float bmi_value = intent.getFloatExtra("bmi", 0.0f);
        float bmi_value = bmi;


        if (bmi_value < 18.5) {
            Rezultat.setText("Pothranjenost " +bmi_value);
        } else if (bmi_value < 25) {
            Rezultat.setText("Normalna težina " +bmi_value);
        } else if (bmi_value < 30) {
            Rezultat.setText("Prekomerna težina " +bmi_value);
        } else {
            Rezultat.setText("Gojaznost " +bmi_value);
        }
        //klikom na dugme Detalji ide se na sledeci activity.
        detalji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detalji = new Intent(activity_result.this, DetailActivity.class );
                detalji.putExtra("bmi",bmi_value);
                startActivity(detalji);
                overridePendingTransition(R.anim.fade_forward, android.R.anim.fade_in);
            }
        });


    }

}