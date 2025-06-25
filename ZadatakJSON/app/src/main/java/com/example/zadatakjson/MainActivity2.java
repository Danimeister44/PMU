package com.example.zadatakjson;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    ListView listViewZemljiste;
    ArrayList<Zemljiste> landList = new ArrayList<>();
    ZemljisteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewZemljiste = findViewById(R.id.listViewZemljiste);
        adapter = new ZemljisteAdapter(this, landList);
        listViewZemljiste.setAdapter(adapter);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://people.vts.su.ac.rs/~sanjam/zemljiste.txt";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray zemljista = response.getJSONArray("zemljiste");

                        for (int i = 0; i < zemljista.length(); i++) {
                            JSONObject obj = zemljista.getJSONObject(i);
                            JSONObject opis = obj.getJSONObject("Opis");
                            JSONObject vlasnik = obj.getJSONObject("vlasnik");

                            Zemljiste land = new Zemljiste(
                                    obj.getString("grad"),
                                    obj.getString("ulica"),
                                    obj.getInt("broj"),

                                    opis.getInt("povrsina"),
                                    opis.getString("tip"),
                                    opis.getInt("id"),
                                    opis.getString("kvalitet"),

                                    vlasnik.getString("privatno"),
                                    vlasnik.getString("dugovanje"),
                                    vlasnik.getInt("telefon")
                            );

                            landList.add(land);
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                }
        );


        queue.add(jsonObjectRequest);
    }
}