package com.example.zadatakjson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewZemljiste;
    ArrayList<Zemljiste> listaZemlje = new ArrayList<>();
    ZemljisteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_http);

        listViewZemljiste = findViewById(R.id.listViewZemljiste);
        adapter = new ZemljisteAdapter(this, listaZemlje);
        listViewZemljiste.setAdapter(adapter);
        new FetchData().execute("https://people.vts.su.ac.rs/~sanjam/zemljiste.txt");
    }

    private class FetchData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            HTTPhandler handler = new HTTPhandler();
            return handler.getJson(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray zemljista = json.getJSONArray("zemljiste");

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

                    listaZemlje.add(land);
                }

                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}