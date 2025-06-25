package com.example.zadatakliste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<Drzava> listaDrzava;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listaDrzava);
        //ListView Drzave = findViewById(R.id.listaDrzava); ovo je bio problem.
        listaDrzava = new ArrayList<>();
        listaDrzava.add(new Drzava("serbia", "Srbija", "Beograd", R.drawable.serbia));
        listaDrzava.add(new Drzava("hungary", "Mađarska", "Budimpešta", R.drawable.hungary));
        listaDrzava.add(new Drzava("germany", "Nemačka", "Berlin", R.drawable.germany));
        listaDrzava.add(new Drzava("spain", "Španija", "Madrid", R.drawable.spain));
        listaDrzava.add(new Drzava("italy", "Italija", "Rim", R.drawable.italy));
        listaDrzava.add(new Drzava("australia", "Australija", "Kanbera", R.drawable.australia));


        DrzavaAdapter adapter = new DrzavaAdapter(this, listaDrzava);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Drzava selected = listaDrzava.get(i);
            Intent intent = new Intent(MainActivity.this, DrzavaDetailsActivity.class);
            intent.putExtra("country_code", selected.getCode());
            intent.putExtra("image", selected.getImageResId());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_out_left, android.R.anim.fade_out);
        });



    }
}