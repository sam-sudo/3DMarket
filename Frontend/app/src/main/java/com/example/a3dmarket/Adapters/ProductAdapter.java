package com.example.a3dmarket.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3dmarket.Item;
import com.example.a3dmarket.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private ArrayList<Item> productList;
    private Context context;

    public ProductAdapter(ArrayList<Item> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.product_item, null);

        Item item = (Item) getItem(position);

        ImageView img = convertView.findViewById(R.id.productImg);
        TextView name = convertView.findViewById(R.id.titulo);
        TextView price = convertView.findViewById(R.id.price);
        TextView date = convertView.findViewById(R.id.dateSale);

        Picasso.get().load(item.getImgList().get(0)).into(img);
        name.setText(item.getName());
        price.setText(item.getPrice());
        date.setText(item.getDateSale());

        return convertView;
    }
}
