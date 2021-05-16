package com.example.a3dmarket.Fragments;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a3dmarket.R;
import com.squareup.picasso.Picasso;

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
        Picasso.get().load(extras.getString("img")).into(img);
        name.setText(extras.getString("name"));
        price.setText(extras.getString("price"));
    }
}
