package com.example.toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final List<CityHistory> historyList;

    public HistoryAdapter(List<CityHistory> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        CityHistory item = historyList.get(position);
        holder.cityTextView.setText(item.getCity());
        holder.tempTextView.setText(String.format("%.1f°C", item.getTemp()));
        holder.descriptionTextView.setText(item.getDescription());
        holder.lastAccessTextView.setText(item.getLastAccess());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView cityTextView;
        TextView tempTextView;
        TextView descriptionTextView;
        TextView lastAccessTextView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTextView = itemView.findViewById(R.id.historyCity);
            tempTextView = itemView.findViewById(R.id.historyTemp);
            descriptionTextView = itemView.findViewById(R.id.historyDescription);
            lastAccessTextView = itemView.findViewById(R.id.historyLastAccess);
        }
    }
}
