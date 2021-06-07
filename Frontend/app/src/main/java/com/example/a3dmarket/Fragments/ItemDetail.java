package com.example.a3dmarket.Fragments;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.a3dmarket.Adapters.Preview_Items_ImgAdapter;
import com.example.a3dmarket.CheckoutPayment;
import com.example.a3dmarket.R;
import com.example.a3dmarket.SharedPref;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ItemDetail extends AppCompatActivity {

    SharedPref sharedPref;
    Button compraButton;
    String getUrl = "";
    LinearLayoutCompat layout;

    private Preview_Items_ImgAdapter preview_items_imgAdapter;


    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdetail);

        layout = (LinearLayoutCompat)findViewById(R.id.layoutItem);

        sharedPref = new SharedPref(this);



        TextView  name  = findViewById(R.id.name);
        TextView  desc  = findViewById(R.id.desc);
        TextView  price = findViewById(R.id.price);
        TextView  description = findViewById(R.id.description);
        ImageView img   = findViewById(R.id.img);

        compraButton = (Button) findViewById(R.id.compraButton);

        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.AppThemeDark);
            layout.setBackgroundColor(Color.parseColor("#4A4A4A"));
            name.setTextColor(Color.parseColor("#FFFFFF"));
            price.setTextColor(Color.parseColor("#FFFFFF"));
            description.setTextColor(Color.parseColor("#FFFFFF"));
            desc.setTextColor(Color.parseColor("#FFFFFF"));
            compraButton.setBackground(getDrawable(R.drawable.button_bg_dark));
        }else{
            setTheme(R.style.AppThemeDay);
            layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        Bundle extras = getIntent().getExtras();


        //hacer array de imagenes
        ArrayList<Uri> imgUriList = new ArrayList<>();

        for(String itemList: extras.getStringArrayList("img")){
            imgUriList.add(Uri.parse(itemList));
        }



        //img.setim(extras.getString("name"));
        Picasso.get().load(imgUriList.get(0)).into(img);
        name.setText(extras.getString("name"));
        price.setText(extras.getString("price"));
        description.setText(extras.getString("description"));
        getUrl = extras.getString("fileUrl");




        RecyclerView recyclerView = this.findViewById(R.id.previewImgReciclerViewDetail);

        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(horizontalLayoutManager);

        preview_items_imgAdapter = new Preview_Items_ImgAdapter(getApplicationContext(),imgUriList);

        recyclerView.setAdapter(preview_items_imgAdapter);






        compraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //downloadAfterPay();
                Intent intent = new Intent(ItemDetail.this, CheckoutPayment.class);
                intent.putExtra("name",name.getText());
                intent.putExtra("price",price.getText());
                intent.putExtra("img", imgUriList.get(0));
                view.getContext().startActivity(intent);
            }
        });



    }

    private void downloadAfterPay() {
        Log.d("TAG", "onClick: " + getUrl);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getUrl));
        String title = URLUtil.guessFileName(getUrl, null, null);
        request.setTitle(title);
        request.setDescription("Descargando archivo");
        String coockie = CookieManager.getInstance().getCookie(getUrl);
        request.addRequestHeader("cookie", coockie);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "titulo");

        DownloadManager downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);

        Toast.makeText(getApplicationContext(), "Comenzando descarga", Toast.LENGTH_SHORT).show();
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
