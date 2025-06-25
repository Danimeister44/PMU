package com.example.zadatakliste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DrzavaAdapter extends ArrayAdapter<Drzava> {
    public DrzavaAdapter(Context context, List<Drzava> drzave)
    {
        super(context, 0, drzave);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Drzava drzava = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_country, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageViewCountry);
        TextView textView = convertView.findViewById(R.id.imeDrzave);
        TextView textView1 = convertView.findViewById(R.id.glavniGrad);

        imageView.setImageResource(drzava.getImageResId());
        textView.setText(drzava.getName());
        textView1.setText(drzava.getCapital());

        return convertView;
    }
}
