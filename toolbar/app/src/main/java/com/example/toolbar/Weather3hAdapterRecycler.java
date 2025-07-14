package com.example.toolbar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Weather3hAdapterRecycler extends RecyclerView.Adapter<Weather3hAdapterRecycler.ViewHolder> {

    private Context context;
    private List<Weather3h> data;
    private String cityName;  //DA SE POKAZUJE GRAD U DetailsActivity.java

    public Weather3hAdapterRecycler(Context context, List<Weather3h> data, String cityName) {
        this.context = context;
        this.data = data;
        this.cityName = cityName;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDate;
        public TextView textViewTemp;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTemp = itemView.findViewById(R.id.textViewTemp);
        }
    }

    @Override
    public Weather3hAdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Weather3hAdapterRecycler.ViewHolder holder, int position) {
        Weather3h weather = data.get(position);
        holder.textViewDate.setText(weather.getFormattedDate());
        // Read saved units
        String units = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                .getString("units", "metric");

// Determine symbol only
        String unitSymbol = "C";
        if ("imperial".equals(units)) {
            unitSymbol = "F";
        }

// Set text without converting numbers
        holder.textViewTemp.setText(
                String.format("Min: %.1f°%s / Max: %.1f°%s",
                        weather.getTempMin(),
                        unitSymbol,
                        weather.getTempMax(),
                        unitSymbol
                )
        );
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("weather", weather);
            intent.putExtra("city", cityName);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

