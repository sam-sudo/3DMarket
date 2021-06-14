package com.example.a3dmarket.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.a3dmarket.Adapters.Preview_Items_ImgAdapter;
import com.example.a3dmarket.Adapters.Preview_Items_ImgAdapter_Detail;
import com.example.a3dmarket.CheckoutPayment;
import com.example.a3dmarket.R;
import com.example.a3dmarket.SharedPref;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ItemDetail extends AppCompatActivity {

    SharedPref sharedPref;
    Button compraButton;
    String getUrl = "";
    LinearLayoutCompat layout;
    LinearLayout barra;
    ImageView back;

    private Preview_Items_ImgAdapter_Detail preview_items_imgAdapter_detail;


    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdetail);

        layout = (LinearLayoutCompat)findViewById(R.id.layoutItem);
        barra = (LinearLayout)findViewById(R.id.barra);


        sharedPref = new SharedPref(this);



        TextView  name        = findViewById(R.id.name);
        TextView  desc        = findViewById(R.id.desc);
        TextView  price       = findViewById(R.id.price);
        TextView  description = findViewById(R.id.description);
        ImageView img         = findViewById(R.id.img);
        ImageView arrow         = findViewById(R.id.back);

        back = (ImageView) findViewById(R.id.back);

        compraButton = (Button) findViewById(R.id.compraButton);

        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.AppThemeDark);
            layout.setBackgroundColor(Color.parseColor("#4A4A4A"));
            name.setTextColor(Color.parseColor("#FFFFFF"));
            price.setTextColor(Color.parseColor("#FFFFFF"));
            description.setTextColor(Color.parseColor("#FFFFFF"));
            desc.setTextColor(Color.parseColor("#FFFFFF"));
            compraButton.setBackground(getDrawable(R.drawable.button_bg_dark));
            arrow.setImageResource(R.drawable.arrow);
            barra.setBackgroundColor(Color.parseColor("#FF2B2B2B"));
        }else{
            setTheme(R.style.AppThemeDay);
            layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();


        //hacer array de imagenes
        ArrayList<Uri> imgUriList = new ArrayList<>();

        for(String itemList: extras.getStringArrayList("img")){
            imgUriList.add(Uri.parse(itemList));
        }



        //img.setim(extras.getString("name"));
        Picasso.get().load(imgUriList.get(0)).into(img);
        name.setText(extras.getString("name"));
        price.setText(extras.getString("price")+ "€");
        description.setText(extras.getString("description"));
        getUrl = extras.getString("fileUrl");




        RecyclerView recyclerView = this.findViewById(R.id.previewImgReciclerViewDetail);

        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(horizontalLayoutManager);

        preview_items_imgAdapter_detail = new Preview_Items_ImgAdapter_Detail(getApplicationContext(),imgUriList);


        recyclerView.setAdapter(preview_items_imgAdapter_detail);


        compraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //downloadAfterPay();
                Intent intent = new Intent(ItemDetail.this, CheckoutPayment.class);
                intent.putExtra("name",name.getText());
                intent.putExtra("price",extras.getString("price"));
                intent.putExtra("img", imgUriList.get(0));
                intent.putExtra("fileUrl", getUrl);
                view.getContext().startActivity(intent);
            }
        });



    }



    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}
