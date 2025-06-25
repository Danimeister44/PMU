package com.example.pizza;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private RadioGroup pizzaSizeRadioGroup;
    private CheckBox meatCheckBox;
    private CheckBox veggiesCheckBox;
    private CheckBox olivesCheckBox;
    private Switch deliverySwitch;
    private Button orderButton;
    private TextView totalPriceTextView;
    private ImageView image;



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

        pizzaSizeRadioGroup = findViewById(R.id.rg_pizza_size);
        meatCheckBox = findViewById(R.id.cb_pepperoni);
        veggiesCheckBox = findViewById(R.id.cb_veggies);
        olivesCheckBox = findViewById(R.id.cb_olives);
        deliverySwitch = findViewById(R.id.sw_delivery);
        orderButton = findViewById(R.id.btn_order);
        totalPriceTextView = findViewById(R.id.tv_order_summary);
        image = findViewById(R.id.imageView);

    }

    public void orderPizza(View view) {
        int pizzaSizePrice = 0;
        int toppingsPrice = 0;
        int deliveryPrice = 0;
        int totalPrice = 0;

        StringBuilder result = new StringBuilder();

        if (pizzaSizeRadioGroup.getCheckedRadioButtonId() == R.id.rb_small) {
            pizzaSizePrice = 10;
            result.append("You chose a 10 inch pizza.\n");
        } else if (pizzaSizeRadioGroup.getCheckedRadioButtonId() == R.id.rb_medium) {
            pizzaSizePrice = 11;
            result.append("You chose a 11 inch pizza.\n");
        } else if (pizzaSizeRadioGroup.getCheckedRadioButtonId() == R.id.rb_large) {
            pizzaSizePrice = 12;
            result.append("You chose a 12 inch pizza.\n");
        }

        if (meatCheckBox.isChecked()) {
            toppingsPrice += 2;
            result.append("Added meat topping.\n");
        }
        if (veggiesCheckBox.isChecked()) {
            toppingsPrice += 2;
            result.append("Added veggies topping.\n");
        }
        if (olivesCheckBox.isChecked()) {
            toppingsPrice += 2;
            result.append("Added olives topping.\n");
        }

        if (deliverySwitch.isChecked()) {
            deliveryPrice = 5;
            result.append("Delivery selected.\n");
        }
        else{
            result.append("You didn't check delivery, so you have to pick it up.\n");
        }

        totalPrice = pizzaSizePrice + toppingsPrice + deliveryPrice;
        result.append("Total Price: $" + totalPrice);

        totalPriceTextView.setText(result.toString());

        image.setVisibility(View.VISIBLE);
    }
}

