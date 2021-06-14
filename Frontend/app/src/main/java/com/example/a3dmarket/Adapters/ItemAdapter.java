package com.example.a3dmarket.Adapters;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3dmarket.Fragments.ItemDetail;
import com.example.a3dmarket.Item;
import com.example.a3dmarket.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.InputStream;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterHolder>{

    private List<Item> itemList;
    Bitmap img;

    public ItemAdapter(List<Item> itemList) {
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


                Intent intent = new Intent(v.getContext(), ItemDetail.class);

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



            Object tag = new Object();

            try {


                Picasso.get()
                        .load(item.getImgList().get(0))
                        .tag(tag)
                        .centerCrop()
                        .resize(200, 200)
                        .into(itemImgView);
               //Picasso.get().load("https://www.pinclipart.com/picdir/big/193-1931067_pixel-clipart-finn-50-x-50-px-png.png").into(itemImgView);
            }catch (Exception e){

            }

        }

    }



}
