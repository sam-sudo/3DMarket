package com.example.a3dmarket.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3dmarket.Adapters.ItemAdapter;
import com.example.a3dmarket.Item;
import com.example.a3dmarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    FirebaseFirestore db    = FirebaseFirestore.getInstance();
    List<Item> itemList     = new ArrayList<>();
    RecyclerView itemRecyclerView;
    SwipeRefreshLayout refreshLayout;
    ItemAdapter adapter = new ItemAdapter(itemList);

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        itemRecyclerView= view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        itemRecyclerView.setAdapter(adapter);

        //Pinterest efect grid
        itemRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        );

        // Create a firestore reference from our app
        //CollectionReference item = db.collection("items");

        // Create a storage reference from our app
        //FirebaseStorage storage = FirebaseStorage.getInstance();
        //StorageReference storageRef = storage.getReference();



        printAllDocumentFromFirebase("items",itemRecyclerView);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                itemList.clear();
                printAllDocumentFromFirebase("items",itemRecyclerView);
                refreshLayout.setRefreshing(false);
                //adapter.notifyDataSetChanged();
                Log.d("TAG", "Refrash DONE");

            }
        });


        return view;
    }

    private void printAllDocumentFromFirebase(String nameCollection, RecyclerView recyclerView) {

        db.collection(nameCollection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        ArrayList<Map<String, Object>> imgList = (ArrayList<Map<String, Object>>) document.get("urlList");


                        ArrayList url = imgList;
                        String price = (String) document.getData().get("price");
                        String name = (String) document.getData().get("name");
                        String description = (String) document.getData().get("description");
                        String fileUrl = (String) document.getData().get("urlFile");
                        //Log.d("TAG", document.getId() + " => " + url);
                        itemList.add(new Item(url, fileUrl, name, price, description));


                    }


                    //get first the new data with a reverse from list of firebase
                    Collections.reverse(itemList);
                    adapter.notifyDataSetChanged();
                    Log.d("TAG", "Error dentro metodo");

                } else {

                    Log.d("TAG", "Error getting documents: ", task.getException());
                }

            }

        });
    }
}