package com.example.putovanje;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    /*
    private EditText imeEdt;
    private EditText datumEdt;
    private RadioGroup statusRadioGroup;
    private RadioGroup osiguranje;
    private RadioButton minhen,lisabon,dablin;
    private RadioButton da,ne;
    private Switch vip;
    private Button submit;
    * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button btnSubmit = findViewById(R.id.submit_button);
        RadioGroup cityGroup = findViewById(R.id.attend_radio_group);
        Switch vipSw = findViewById(R.id.sw_vip);
        RadioGroup osiguranje = findViewById(R.id.osiguranje_radio_group);
        EditText nameET = findViewById(R.id.ime_text);
        EditText dateET = findViewById(R.id.datum_text);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String date = dateET.getText().toString();

                int selectedCityId = cityGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioCity = findViewById(selectedCityId);
                String city = selectedRadioCity.getText().toString();

                boolean vip = vipSw.isChecked();
                int selectedInsId = osiguranje.getCheckedRadioButtonId();
                RadioButton selectedIns = findViewById(selectedInsId);
                String osiguranje = selectedIns.getText().toString();


                Intent result = new Intent(MainActivity.this,activity_finish.class);
                result.putExtra("name",name);
                result.putExtra("date",date);
                result.putExtra("city",city);
                result.putExtra("vip",vip);
                result.putExtra("insurance",osiguranje);
                startActivity(result);
            }
        });
    }

}