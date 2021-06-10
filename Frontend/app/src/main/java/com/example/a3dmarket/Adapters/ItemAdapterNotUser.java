package com.example.a3dmarket.Adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3dmarket.Fragments.ItemDetail;
import com.example.a3dmarket.Fragments.ItemDetailNotUser;
import com.example.a3dmarket.Item;
import com.example.a3dmarket.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapterNotUser extends RecyclerView.Adapter<ItemAdapterNotUser.ItemAdapterHolder>{

    private List<Item> itemList;

    public ItemAdapterNotUser(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemAdapterHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container,
                        parent,
                        false)
        );
    }



    @Override
    public void onBindViewHolder(@NonNull ItemAdapterHolder holder, int position) {

        holder.setItemImg(itemList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(v.getContext(), ItemDetailNotUser.class);

                intent.putExtra("img" ,itemList.get(position).getImgList());
                intent.putExtra("name" ,itemList.get(position).getName());
                intent.putExtra("price" ,itemList.get(position).getPrice());
                intent.putExtra("description" ,itemList.get(position).getDescription());
                intent.putExtra("fileUrl" ,itemList.get(position).getFileUrl());

                v.getContext().startActivity(intent);



                Log.d("TAG", "onClick: " + position);
            }
        });

    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }




    //---------------------------class---------------

    class ItemAdapterHolder extends RecyclerView.ViewHolder{

        RoundedImageView itemImgView;


        ItemAdapterHolder(@NonNull View itemView) {
            super(itemView);

            itemImgView = itemView.findViewById(R.id.itemImg);
        }

        void setItemImg(Item item){


            try {
                Picasso.get().load(item.getImgList().get(0)).into(itemImgView);
            }catch (Exception e){

            }

        }

    }



}
