package com.example.priprema;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {
    public StudentAdapter(Context context, ArrayList<Student> listaStudenata){
        super(context,0,listaStudenata);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        Student s = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }
        TextView tvIndeks = convertView.findViewById(R.id.index);
        TextView tvProsek = convertView.findViewById(R.id.prosek);
        TextView tvRodjendan = convertView.findViewById(R.id.rodjendan);
        TextView tvUpis = convertView.findViewById(R.id.upis);

        tvIndeks.setText("Indeks: "+s.index);
        tvProsek.setText("Prosek: " + s.prosek);
        tvRodjendan.setText("Rodjendan: "+s.god_rodjenja);
        tvUpis.setText("Upis: "+s.god_upisa);
        return convertView;
    }
}
