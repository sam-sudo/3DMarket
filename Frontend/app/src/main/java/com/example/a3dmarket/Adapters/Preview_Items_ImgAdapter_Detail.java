package com.example.a3dmarket.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3dmarket.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Preview_Items_ImgAdapter_Detail extends RecyclerView.Adapter<Preview_Items_ImgAdapter_Detail.ViewHolder> {

    private ArrayList<Uri> previewImgList = new ArrayList<>();
    private LayoutInflater mInflater;
    Context context;



    public Preview_Items_ImgAdapter_Detail(Context context, ArrayList previewImgList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.previewImgList = previewImgList;

    }


    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.preview_items_img_detail, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setItemImg(previewImgList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: ----------------------------" );



            }
        });




    }

    @Override
    public int getItemCount() {
        return previewImgList.size();
    }



     class ViewHolder extends RecyclerView.ViewHolder{

         RoundedImageView itemImgView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemImgView = itemView.findViewById(R.id.itemImg);
        }

         void setItemImg(Uri item){


             Picasso.get().load(item).into(itemImgView);

         }


    }

}
