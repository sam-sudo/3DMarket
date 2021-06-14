package com.example.a3dmarket.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.a3dmarket.Adapters.ItemAdapter;
import com.example.a3dmarket.Adapters.ItemAdapterNotUser;
import com.example.a3dmarket.Item;
import com.example.a3dmarket.R;
import com.example.a3dmarket.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragmentNotUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragmentNotUser extends Fragment {

    SharedPref sharedPref;


    FirebaseFirestore db    = FirebaseFirestore.getInstance();
    List<Item> itemList     = new ArrayList<>();
    RecyclerView itemRecyclerView;
    SwipeRefreshLayout refreshLayout;
    ItemAdapterNotUser adapter = new ItemAdapterNotUser(itemList);
    SearchView searchView ;


    public SearchFragmentNotUser() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchFragmentNotUser newInstance(String param1, String param2) {
        SearchFragmentNotUser fragment = new SearchFragmentNotUser();
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
        View view = inflater.inflate(R.layout.fragment_search_not_user, container, false);

        itemRecyclerView= view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        searchView =  view.findViewById(R.id.searchView);
        itemRecyclerView.setAdapter(adapter);

        sharedPref = new SharedPref(getContext());

        if (sharedPref.loadNightModeState() == true){
            getActivity().setTheme(R.style.AppThemeDark);

        }else{
            getActivity().setTheme(R.style.AppThemeDay);
        }

        //Pinterest efect grid
        itemRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        );

        // Create a firestore reference from our app
        //CollectionReference item = db.collection("items");

        // Create a storage reference from our app
        //FirebaseStorage storage = FirebaseStorage.getInstance();
        //StorageReference storageRef = storage.getReference();




        /*refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                itemList.clear();
                printAllDocumentFromFirebase("items",itemRecyclerView,"");
                refreshLayout.setRefreshing(false);
                //adapter.notifyDataSetChanged();
                Log.d("TAG", "Refrash DONE");

            }
        });*/



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                printAllDocumentFromFirebase("items",itemRecyclerView, newText.toLowerCase());





                return false;
            }
        });



        return view;


    }


    private void printAllDocumentFromFirebase(String nameCollection, RecyclerView recyclerView, String wordToSearch) {




        db.collection(nameCollection).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                itemList.clear();

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        ArrayList<Map<String, Object>> imgList = (ArrayList<Map<String, Object>>) document.get("urlList");



                        if(wordToSearch.equalsIgnoreCase("")){


                            ArrayList url = imgList;
                            String price = (String) document.getData().get("price");
                            String name = (String) document.getData().get("name");
                            String description = (String) document.getData().get("description");
                            String fileUrl = (String) document.getData().get("urlFile");
                            String author = (String) document.getData().get("author");
                            //Log.d("TAG", document.getId() + " => " + url);
                            itemList.add(new Item(url, fileUrl, name, price, description,author));

                        }else {

                            if(document.getData().get("name").toString().toLowerCase().contains(wordToSearch)){



                                ArrayList url = imgList;
                                String price = (String) document.getData().get("price");
                                String name = (String) document.getData().get("name");
                                String description = (String) document.getData().get("description");
                                String fileUrl = (String) document.getData().get("urlFile");
                                String author = (String) document.getData().get("author");
                                //Log.d("TAG", document.getId() + " => " + url);
                                itemList.add(new Item(url, fileUrl, name, price, description,author));

                            }


                        }


                    }


                    //get first the new data with a reverse from list of firebase
                    Collections.reverse(itemList);
                    adapter.notifyDataSetChanged();
                    Log.d("TAG", "Error dentro metodo");

                } else {

                    Log.d("TAG", "Error getting documents: ", task.getException());
                }

                Log.d("TAG", "onQueryTextChange: " + itemList.size());

            }

        });
    }


}