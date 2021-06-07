package com.example.a3dmarket.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3dmarket.Adapters.ProductAdapter;
import com.example.a3dmarket.Item;
import com.example.a3dmarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseFirestore db    = FirebaseFirestore.getInstance();

    TextView name, email;



    ListView listView;
    ArrayList productList = new ArrayList();

    ProductAdapter productAdapter ;

    String userEmail;

    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) view.findViewById(R.id.name_profile);
        email = (TextView) view.findViewById(R.id.email_profile);



        listView = view.findViewById(R.id.productList);
        productAdapter = new ProductAdapter(productList, view.getContext());
        listView.setAdapter(productAdapter);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        id = mAuth.getUid();


        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    userEmail = snapshot.child("email").getValue().toString();
                    name.setText(snapshot.child("name").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                }else{
                    Toast.makeText(getContext(), "TOAAS", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        db.collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {


                        Log.d("TAG", "onComplete: "+ document.getData().get("author"));
                        Log.d("TAG", "onComplete: email "+ userEmail);

                        ArrayList<Map<String, Object>> imgList = (ArrayList<Map<String, Object>>) document.get("urlList");


                        ArrayList url = imgList;
                        String price = (String) document.getData().get("price");
                        String name = (String) document.getData().get("name");
                        String description = (String) document.getData().get("description");
                        String fileUrl = (String) document.getData().get("urlFile");
                        String author = (String) document.getData().get("author");
                        //Log.d("TAG", document.getId() + " => " + url);


                        if(author.equalsIgnoreCase(userEmail) ){
                            productList.add(new Item(url, fileUrl, name, price, description, author));
                            Log.d("name", "onComplete2: " + author);
                        }


                    }

                    productAdapter.notifyDataSetChanged();

                } else {

                    Log.d("TAG", "Error getting documents: ", task.getException());
                }

            }


        });







        return view;
    }
}