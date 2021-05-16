package com.example.a3dmarket.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;



import com.example.a3dmarket.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UploadFragment extends Fragment {

    Button btnSelectFiles;
    Button btnUp;
    Uri uri;
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


        btnSelectFiles = view.findViewById(R.id.selectFiles);
        btnUp = view.findViewById(R.id.upFiles);

        btnSelectFiles.setOnClickListener(new View.OnClickListener() {
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
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);


            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uri != null){
                    UpFilesToFirebase(uri);
                    uri = null;
                    btnUp.setEnabled(false);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }



     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         Context context = getContext();



         if ( resultCode == Activity.RESULT_OK) {

             if (data == null) {

                 return;
             }

              uri = data.getData();

             if(uri != null){
                 btnUp.setEnabled(true);
             }
         }
     }

    private void UpFilesToFirebase(Uri uri) {

        //SUBIR AL STORAGE
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageRef.child("imagen/"+ uri.getLastPathSegment());
        mountainImagesRef.putFile(uri);

        //SUBIR DATOS A FIRESTORE DATABSE

        //db.child("items").child(String.valueOf(new Date().getTime())).child("username").setValue("name");

    }


}