package com.example.a3dmarket.Fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a3dmarket.Adapters.Preview_Items_ImgAdapter;
import com.example.a3dmarket.Home;
import com.example.a3dmarket.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UploadFragment extends Fragment {

    TextView editFileName;
    TextView editName;
    TextView editPrice;
    TextView editDescription;

    Button btnSelectImg;
    Button btnUp;
    Button btnSelectFile;

    Uri uri;
    Uri file3d;

    ArrayList<Uri> previewImgList;
    String fileName;
    String imgName;
    String userEmail;
    String id;


    Context context ;


    private Preview_Items_ImgAdapter preview_items_imgAdapter;
    //REAL TIME FIREBASE
    //DatabaseReference db    = FirebaseDatabase.getInstance().getReference();

    //DATABASE FIREBASE
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;



    private int VALOR_RETORNO = 1;
    //STORAGE FIREBASE
    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context= getActivity();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upload, container, false);


        btnSelectImg  = view.findViewById(R.id.selectImg);
        btnSelectFile = view.findViewById(R.id.selectFile);
        btnUp         = view.findViewById(R.id.upFiles);

        editFileName    = view.findViewById(R.id.previewObjetText);
        editName        = view.findViewById(R.id.editName);
        editPrice       = view.findViewById(R.id.editPrice);
        editDescription = view.findViewById(R.id.editDescription);

        mAuth     = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        id = mAuth.getUid();


        previewImgList = new ArrayList();



        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    userEmail = snapshot.child("email").getValue().toString();

                }else{
                    Toast.makeText(getContext(), "TOAAS", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btnSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);


            }
        });


        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/vnd.ms-pki.stl");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), VALOR_RETORNO);

            }
        });


        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Uri> imagesUri = new ArrayList<Uri>() {
                };

                if(previewImgList.size() > 0 ){

                    for (Uri uri: previewImgList) {
                        Log.d("TAG", "onClick: " + uri);
                        imagesUri.add(uri);
                    }

                    try {
                        UpFilesToFirebase(imagesUri);

                        btnUp.setEnabled(false);
                    }catch (Exception e){
                        Log.d("TAG", "onClick: FALLO AL SUBIR ARCHUVOS");
                        Toast.makeText(getContext(),"Faltan datos por rellenar",Toast.LENGTH_SHORT).show();
                        btnUp.setEnabled(true);
                    }







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

                     LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

                     recyclerView.setLayoutManager(horizontalLayoutManager);

                     preview_items_imgAdapter = new Preview_Items_ImgAdapter(context,previewImgList);

                     recyclerView.setAdapter(preview_items_imgAdapter);

                 }

                 if(mime.matches(fileRegex)){

                     Log.d("TAG", "URI: " +uri);

                     file3d = uri;
                     fileName = getNameFromDrive(uri);

                     editFileName.setText(fileName);
                 }

                 if (uri != null && previewImgList.size() > 0) {
                     btnUp.setEnabled(true);
                 }

             }
         }
     }

    private String getNameFromDrive(Uri uri) {
        // retrun the name from a file in drive
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()){
            return  cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        }

        return "";
    }

    private void UpFilesToFirebase(ArrayList<Uri> uriArr) {

        //-----SUBIR AL STORAGE-----
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        String uniqueID = UUID.randomUUID().toString();

        // Create a reference
        // Archivo
        StorageReference fileRef = storageRef.child("archivos/"+ uniqueID + "_" + fileName );

        ArrayList<String> realUrlList = new ArrayList<>();


        //------SUBIR DATOS A FIRESTORE DATABSE-----

        fileRef.putFile(file3d).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Map<String, Object> objetToSendToFirebase = new HashMap<>();

                        objetToSendToFirebase.put("name", editName.getText().toString());
                        objetToSendToFirebase.put("price", editPrice.getText().toString());
                        objetToSendToFirebase.put("description", editDescription.getText().toString());
                        objetToSendToFirebase.put("urlFile", uri.toString());
                        objetToSendToFirebase.put("author", userEmail);
                        //objetToSendToFirebase.put("imgList", uriArr);



                        for (int i = 0; i < uriArr.size() ; i++) {

                            imgName = getNameFromDrive(uriArr.get(i));
                            String uniqueID = UUID.randomUUID().toString();


                            // Create a reference
                            // imagen
                            StorageReference imagesRef = storageRef.child("imagen/"+ uniqueID+"_" +imgName );

                            int count = i;
                            imagesRef.putFile(uriArr.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Log.d("TAG", "onSuccess: UPLOADED IMG");


                                    imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {


                                            Log.d("TAG", "onSuccess2: " + uri.toString());

                                            realUrlList.add(uri.toString());

                                            if(count >= uriArr.size()-1){

                                                objetToSendToFirebase.put("urlList", realUrlList);


                                                db.collection("items").add(objetToSendToFirebase).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d("TAG", "UpFilesToFirebase: DONEE!!!");
                                                        try {
                                                            Intent intent = new Intent(context, Home.class);
                                                            getContext().startActivity(intent);
                                                            getActivity().finish();
                                                            previewImgList.clear();
                                                        }catch (Exception e){}
                                                    }
                                                });

                                            }

                                        }
                                    });


                                }
                            });
                        }
                    }
                });

            }
        });
















    }





}