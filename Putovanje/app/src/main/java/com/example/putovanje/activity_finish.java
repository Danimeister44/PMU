package com.example.putovanje;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_finish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finish);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("date");
        String city = intent.getStringExtra("city");
        boolean isVip = intent.getBooleanExtra("vip",false);
        String insurance = intent.getStringExtra("insurance");

        TextView resultTV = findViewById(R.id.complete);

        resultTV.setText("Uspešno ste rezervisali!\n\n" + "Ime: " + name + "\n" +
                "Datum: " + date + "\n" + "Grad: " + city + "\n" +
                "VIP: " + (isVip ? "Da" : "Ne") + "\n" + "Osiguranje: " + insurance);
    }
}