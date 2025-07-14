package com.example.priprema;

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

public class MainActivity extends AppCompatActivity {

    ListView listViewStudent;
    ArrayList<Student> listaStudenata = new ArrayList<>();
    StudentAdapter studentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listViewStudent = findViewById(R.id.listviewStudent);
        studentAdapter = new StudentAdapter(this, listaStudenata);
        listViewStudent.setAdapter(studentAdapter);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://people.vts.su.ac.rs/~sanjam/jsonPodaci.txt";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray zemljista = response.getJSONArray("studenti");

                        for (int i = 0; i < zemljista.length(); i++) {
                            JSONObject obj = zemljista.getJSONObject(i);
                            JSONObject opis = obj.getJSONObject("Opis");
                            JSONObject vlasnik = obj.getJSONObject("vlasnik");

                            JSONObject ocene = obj.getJSONObject("ocene");
                            Student s = new Student(
                                    obj.getInt("index"),
                                    obj.getInt("god_rodjenja"),
                                    obj.getInt("god_upisa"),
                                    ocene.getDouble("prosek")
                            );

                            listaStudenata.add(s);
                        }

                        studentAdapter.notifyDataSetChanged();

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