package com.example.a3dmarket.Fragments;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;

import com.example.a3dmarket.R;
import com.squareup.picasso.Picasso;

public class ItemDetail extends AppCompatActivity {


    Button compraButton;
    String getUrl = "";

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdetail);



        TextView  name  = findViewById(R.id.name);
        TextView  price = findViewById(R.id.price);
        TextView  description = findViewById(R.id.description);
        ImageView img   = findViewById(R.id.img);

        compraButton = (Button) findViewById(R.id.compraButton);

        Bundle extras = getIntent().getExtras();


        //img.setim(extras.getString("name"));
        Picasso.get().load(extras.getString("img")).into(img);
        name.setText(extras.getString("name"));
        price.setText(extras.getString("price"));
        description.setText(extras.getString("description"));
        getUrl = extras.getString("fileUrl");

        compraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
