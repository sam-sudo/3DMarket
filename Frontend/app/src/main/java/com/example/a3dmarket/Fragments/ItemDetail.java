package com.example.a3dmarket.Fragments;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a3dmarket.R;

public class ItemDetail extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdetail);

        TextView name = findViewById(R.id.name);
        TextView price = findViewById(R.id.price);
        ImageView img = findViewById(R.id.img);

        Bundle extras = getIntent().getExtras();


        //img.setim(extras.getString("name"));
        name.setText(extras.getString("name"));
        price.setText(extras.getString("price"));
    }
}
