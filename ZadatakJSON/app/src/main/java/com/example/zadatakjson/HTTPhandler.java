package com.example.zadatakjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPhandler {

    public String getJson(String adressString){
        StringBuilder ispis = new StringBuilder();
        try {
            URL url = new URL(adressString);
            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while( (line = reader.readLine()) != null)
            {
                ispis.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return ispis.toString();
    }
}
