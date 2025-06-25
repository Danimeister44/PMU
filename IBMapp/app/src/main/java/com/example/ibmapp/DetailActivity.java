package com.example.ibmapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        TextView popuni = findViewById(R.id.textViewDetalji);
        Button TakeMeAllTheWayHome = findViewById(R.id.buttonHome);
        Intent detaljno = new Intent();
        float bmi = getIntent().getFloatExtra("bmi", -1f);

        if (bmi == -1) {
            popuni.setText("BMI je indeks telesne mase koji pomaže u proceni uhranjenosti osobe...");
        } else if (bmi < 18.5) {
            popuni.setText("Pothranjenost: Jedite češće, uključite više kalorija, posavetujte se sa lekarom.");
        } else if (bmi < 25) {
            popuni.setText("Normalna težina: Bravo! Održavajte zdrav način života, balansiranu ishranu i fizičku aktivnost.");
        } else {
            popuni.setText("Prekomerna težina ili gojaznost: Smanjite unos kalorija, povećajte fizičku aktivnost, posetite lekara.");
        }

        TakeMeAllTheWayHome.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent detalj = new Intent(DetailActivity.this, MainActivity.class );
            startActivity(detalj);
            overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out);
        }
    });

    }

}