package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

        txtDate.setText(item.getDateTime());
        txtTemp.setText(String.format("%.1f°C", item.getTemp()));
        txtDesc.setText(item.getDescription());

        return rowView;
    }
}