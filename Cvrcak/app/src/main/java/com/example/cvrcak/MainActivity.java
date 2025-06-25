package com.example.cvrcak;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private RadioGroup vrsteTemp;
    private EditText brojCrvkuta;
    private Button unesi;
    private TextView ispis;
    private EditText promenaTekstaZaFarenhajt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views (only once)
        vrsteTemp = findViewById(R.id.vrstaTemperature);
        brojCrvkuta = findViewById(R.id.editTextNumber2);
        unesi = findViewById(R.id.button);
        ispis = findViewById(R.id.textView);
        promenaTekstaZaFarenhajt = findViewById(R.id.editTextText3);

        // Now attach the listener using `vrsteTemp`, which is already defined above
        vrsteTemp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton) {
                    promenaTekstaZaFarenhajt.setText("Unesite broj cvrkuta za 25 sekundi.");
                } else if (checkedId == R.id.radioButton2) {
                    promenaTekstaZaFarenhajt.setText("Unesite broj cvrkuta za 14 sekundi.");
                }
            }
        });
    }


    public void izracunaj(View view)
    {
        int broj = Integer.parseInt(brojCrvkuta.getText().toString());

        StringBuilder result = new StringBuilder();
        if(vrsteTemp.getCheckedRadioButtonId() == R.id.radioButton)
        {
            int resnje = broj/3+4;
            result.append("Temperatura je:" + resnje + "℃");
        }
        else if(vrsteTemp.getCheckedRadioButtonId() == R.id.radioButton2)
        {

            int rez = broj+40;
            result.append("Temperatura je:" + rez + "℉");
        }
        ispis.setText(result.toString());
    }
}