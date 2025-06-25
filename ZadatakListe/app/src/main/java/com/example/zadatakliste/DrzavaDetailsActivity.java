package com.example.zadatakliste;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class DrzavaDetailsActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences prefs = newBase.getSharedPreferences("settings", MODE_PRIVATE);
        String lang = prefs.getString("lang", "sr"); // default to Serbian
        Context context = LocaleHelper.setLocale(newBase, lang);
        super.attachBaseContext(context);
    }
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drzava_details);
        imageView = findViewById(R.id.imageView2);
        textView = findViewById(R.id.textView);

        TextView opis = findViewById(R.id.textViewOpis);

        Button btnSerbian = findViewById(R.id.buttonSerbian);
        Button btnEnglish = findViewById(R.id.buttonEnglish);

        btnSerbian.setOnClickListener(v -> {
            getSharedPreferences("settings", MODE_PRIVATE)
                    .edit().putString("lang", "sr").apply();
            recreate();
        });

        btnEnglish.setOnClickListener(v -> {
            getSharedPreferences("settings", MODE_PRIVATE)
                    .edit().putString("lang", "en").apply();
            recreate();
        });

        Intent intent = getIntent();
        if(intent != null)
        {
            String name = intent.getStringExtra("naziv");
            String grad = intent.getStringExtra("capital");
            int imageResId = intent.getIntExtra("image",0);

            textView.setText(name);
            imageView.setImageResource(imageResId);
            String countryCode = intent.getStringExtra("country_code");
            if (countryCode != null) {
            switch (countryCode) {
                case "serbia":
                    textView.setText(getString(R.string.country_serbia));
                    opis.setText(getString(R.string.description_serbia));
                    break;
                case "hungary":
                    textView.setText(getString(R.string.country_hungary));
                    opis.setText(getString(R.string.description_hungary));
                    break;
                case "germany":
                    textView.setText(getString(R.string.country_germany));
                    opis.setText(getString(R.string.description_germany));
                    break;
                case "spain":
                    textView.setText(getString(R.string.country_spain));
                    opis.setText(getString(R.string.description_spain));
                    break;
                case "italy":
                    textView.setText(getString(R.string.country_italy));
                    opis.setText(getString(R.string.description_italy));
                    break;
                case "australia":
                    textView.setText(getString(R.string.country_australia));
                    opis.setText(getString(R.string.description_australia));
                    break;
            }
        }

        }

    }
}