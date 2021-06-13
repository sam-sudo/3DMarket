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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.a3dmarket.Adapters.ItemAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment  {

    SharedPref sharedPref;


    FirebaseFirestore db    = FirebaseFirestore.getInstance();
    List<Item> itemList     = new ArrayList<>();
    RecyclerView itemRecyclerView;
    SwipeRefreshLayout refreshLayout;
    ItemAdapter adapter = new ItemAdapter(itemList);
    SearchView searchView ;
    /*int check = 0;
    CheckBox check_0_100;
    CheckBox check_100_400;
    CheckBox more_400;*/
    Spinner spinner;

    String textToSearch="";


    public SearchFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        itemRecyclerView= view.findViewById(R.id.recyclerView);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        searchView =  view.findViewById(R.id.searchView);
        itemRecyclerView.setAdapter(adapter);
        /*check_100_400 = view.findViewById(R.id.B_100_400);
        check_0_100 = view.findViewById(R.id.less_100);
        more_400 = view.findViewById(R.id.more_400);*/
        sharedPref = new SharedPref(getContext());
        spinner = view.findViewById(R.id.spinner);


        ArrayAdapter adapterSpinner = ArrayAdapter.createFromResource(
                getContext(),R.array.listaSpinner,android.R.layout.simple_list_item_1);

        adapterSpinner.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapterSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "onItemSelected erererer: " + position);

                printAllDocumentFromFirebase("items",itemRecyclerView, textToSearch,position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        if (sharedPref.loadNightModeState() == true){
            getActivity().setTheme(R.style.AppThemeDark);

        }else{
            getActivity().setTheme(R.style.AppThemeDay);
        }

        //Pinterest efect grid
        itemRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        );

        //printAllDocumentFromFirebase("items",itemRecyclerView, textToSearch,0);


        /*check_0_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Log.d("TAG", "onCheckedChanged: 0");
                    check = 1;
                    printAllDocumentFromFirebase("items",itemRecyclerView, textToSearch,1);

                }else {
                    check = 0;
                    printAllDocumentFromFirebase("items",itemRecyclerView, textToSearch,0);
                }
                adapter.notifyDataSetChanged();
            }
        });


        check_100_400.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Log.d("TAG", "onCheckedChanged: 0");
                    check = 2;
                    printAllDocumentFromFirebase("items",itemRecyclerView, textToSearch,2);

                }else {
                    check = 0;
                    printAllDocumentFromFirebase("items",itemRecyclerView, textToSearch,0);
                }
                adapter.notifyDataSetChanged();
            }
        });

        more_400.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Log.d("TAG", "onCheckedChanged: 0");
                    check = 3;
                    printAllDocumentFromFirebase("items",itemRecyclerView, textToSearch,3);

                }else {
                    check = 0;
                    printAllDocumentFromFirebase("items",itemRecyclerView, textToSearch,0);
                }
                adapter.notifyDataSetChanged();
            }
        });*/


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                textToSearch = newText;

                printAllDocumentFromFirebase("items",itemRecyclerView, textToSearch,0);



                return false;
            }
        });



        return view;


    }






    private void printAllDocumentFromFirebase(String nameCollection, RecyclerView recyclerView, String wordToSearch, int filter) {

        itemList.clear();

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
                        String author = (String) document.getData().get("author");
                        //Log.d("TAG", document.getId() + " => " + url);

                        if(wordToSearch.equalsIgnoreCase("")){

                            regexPrice(document, url, price, name, description, fileUrl, author,filter);

                        }else {

                            if(document.getData().get("name").toString().contains(wordToSearch)){
                                regexPrice(document, url, price, name, description, fileUrl, author,filter);

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

            }

            private void regexPrice(QueryDocumentSnapshot document, ArrayList url, String price, String name, String description, String fileUrl, String author, int filter) {
                int priceToSearch = Integer.parseInt(document.getData().get("price").toString());

                switch (filter){

                    default:
                        itemList.add(new Item(url, fileUrl, name, price, description, author));
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:

                        if(priceToSearch <= 100 ){
                            itemList.add(new Item(url, fileUrl, name, price, description, author));
                            adapter.notifyDataSetChanged();
                        }

                        break;
                    case 2:

                        if(priceToSearch >= 100 && priceToSearch <= 400){
                            itemList.add(new Item(url, fileUrl, name, price, description, author));
                            adapter.notifyDataSetChanged();
                        }

                        break;
                    case 3:

                        if(priceToSearch >= 400){
                            itemList.add(new Item(url, fileUrl, name, price, description, author));
                        }

                        break;
                }
            }

        });
    }



}