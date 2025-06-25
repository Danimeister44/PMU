package com.example.zadatakjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
public class ZemljisteAdapter extends  ArrayAdapter<Zemljiste>{
    public ZemljisteAdapter(Context context, ArrayList<Zemljiste> listaZemlje){
        super(context,0,listaZemlje);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Zemljiste s = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_zemljiste, parent, false);
        }
        TextView tvAddress = convertView.findViewById(R.id.adresa);
        TextView tvInfo = convertView.findViewById(R.id.opisInfo);
        TextView tvOwner = convertView.findViewById(R.id.vlasnikInfo);

        tvAddress.setText(s.grad + ", " + s.ulica + " " + s.broj);
        tvInfo.setText("Tip: " + s.tip + " (" + s.povrsina + " m², " + s.kvalitet + ")");
        tvOwner.setText("Privatno: " + s.privatno + ", Dugovanje: " + s.dugovanje + ", Tel: " + s.telefon);

        return convertView;
    }
}
