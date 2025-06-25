package com.example.toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weatherapp.R;

import java.util.List;

public class Weather3hAdapter extends BaseAdapter {
    private Context context;
    private List<Weather3h> weatherList;
    private LayoutInflater inflater;

    public Weather3hAdapter(Context context, List<Weather3h> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.item_weather3h, parent, false);

        TextView txtDate = rowView.findViewById(R.id.txtDate);
        TextView txtTemp = rowView.findViewById(R.id.txtTemp);
        TextView txtDesc = rowView.findViewById(R.id.txtDesc);

        Weather3h item = weatherList.get(position);

        // Get the user's preferred unit from SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String units = prefs.getString("units", "metric");
        String symbol = units.equals("imperial") ? "°F" : "°C";

        txtDate.setText(item.getDateTime());
        txtTemp.setText(String.format("%.1f%s", item.getTemp(), symbol)); // Uses selected unit
        txtDesc.setText(item.getDescription());

        return rowView;
    }
}