package com.example.a3dmarket.Fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.a3dmarket.Adapters.Preview_Items_ImgAdapter;
import com.example.a3dmarket.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UploadFragment extends Fragment {

    TextView fileName;
    Button btnSelectImg;
    Button btnUp;
    Button btnSelectFile;
    Uri uri;
    Uri file3d;
    ArrayList<Uri> previewImgList;


    private Preview_Items_ImgAdapter preview_items_imgAdapter;
    DatabaseReference db    = FirebaseDatabase.getInstance().getReference();

    private int VALOR_RETORNO = 1;
    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload, container, false);


        btnSelectImg = view.findViewById(R.id.selectImg);
        btnSelectFile = view.findViewById(R.id.selectFile);
        btnUp = view.findViewById(R.id.upFiles);
        fileName = view.findViewById(R.id.previewObjetText);
        previewImgList = new ArrayList();





        btnSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /**
                 * / : poder seleccionar todos los archivos
                 * audio/* : solo seleccionar archivos de audio .mp3,.wav...
                 * video/* : solo seleccionar archivos de video .mp4,.avi....
                 * image/* : solo seleccionar archivos de imágen .jpg,.png....
                 * text/plain : solo seleccionar archivos con texto plano.
                 *
                 * Con setType("video/*|image/*"); Con pipe puedes poner varios
                 *
                 */

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);


            }
        });


        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * / : poder seleccionar todos los archivos
                 * audio/* : solo seleccionar archivos de audio .mp3,.wav...
                 * video/* : solo seleccionar archivos de video .mp4,.avi....
                 * image/* : solo seleccionar archivos de imágen .jpg,.png....
                 * text/plain : solo seleccionar archivos con texto plano.
                 *
                 * Con setType("video/*|image/*"); Con pipe puedes poner varios
                 *
                 */

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/vnd.ms-pki.stl");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);

            }
        });


        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(previewImgList.size() > 0 ){

                    for (Uri uri: previewImgList) {
                        UpFilesToFirebase(uri);
                    }


                    previewImgList.clear();
                    btnUp.setEnabled(false);


                }


            }
        });


        return view;
    }



     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         Context context = getContext();

         String fileRegex = ".*\\.stl";
         String imgRegex = "image/.*";

         if ( resultCode == Activity.RESULT_OK) {

             if (data == null) {

                 return;
             }

              uri = data.getData();



             if(uri != null){
                 ContentResolver cr = context.getContentResolver();
                 String mime = cr.getType(uri);
                 Log.d("TAG", "onActivityResult: es ...." + mime);

                 if(mime.matches(imgRegex)){
                     Log.d("TAG", "onActivityResult: es imagen");
                     //btnUp.setEnabled(true);

                     previewImgList.add(uri);

                     RecyclerView recyclerView = getView().findViewById(R.id.previewImgReciclerView);

                     LinearLayoutManager horizontalLayoutManager
                             = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

                     recyclerView.setLayoutManager(horizontalLayoutManager);

                     preview_items_imgAdapter = new Preview_Items_ImgAdapter(context,previewImgList);

                     recyclerView.setAdapter(preview_items_imgAdapter);

                 }

                 if(mime.matches(fileRegex)){

                     file3d = uri;
                     fileName.setText(uri.getLastPathSegment());
                 }

                 if (file3d != null && previewImgList.size() > 0) {
                     btnUp.setEnabled(true);
                 }



             }
         }
     }

    private void UpFilesToFirebase(Uri uri) {

        //SUBIR AL STORAGE
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        // Create a reference 
        StorageReference mountainImagesRef = storageRef.child("imagen/"+ uri.getLastPathSegment());
        mountainImagesRef.putFile(uri);

        //SUBIR DATOS A FIRESTORE DATABSE

        //db.child("items").child(String.valueOf(new Date().getTime())).child("username").setValue("name");

    }


}