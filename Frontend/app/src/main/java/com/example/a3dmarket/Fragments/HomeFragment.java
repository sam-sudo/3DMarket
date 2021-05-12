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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    FirebaseFirestore db    = FirebaseFirestore.getInstance();
    List<Item> itemList     = new ArrayList<>();
    RecyclerView itemRecyclerView;
    SwipeRefreshLayout refreshLayout;
    ItemAdapter adapter = new ItemAdapter(itemList);

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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

                        String url = (String) document.getData().get("url");
                        String price = (String) document.getData().get("price");
                        String name = (String) document.getData().get("name");
                        //Log.d("TAG", document.getId() + " => " + url);
                        itemList.add(new Item(url, name, price));

                    }


                    //get first the new data with a reverse from list of firebase
                    //Collections.reverse(itemList);
                    adapter.notifyDataSetChanged();
                    Log.d("TAG", "Error dentro metodo");

                } else {

                    Log.d("TAG", "Error getting documents: ", task.getException());
                }

            }

        });
    }
}