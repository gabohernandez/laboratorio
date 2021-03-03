package com.laboratorio.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laboratorio.myapplication.model.Cart;
import com.laboratorio.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MyProductSingleRecyclerViewAdapter extends RecyclerView.Adapter<MyProductSingleRecyclerViewAdapter.ViewHolder> {

    private Product mValue = new Product();
    private Context context;

    public MyProductSingleRecyclerViewAdapter(Product product) {
            this.mValue = product;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_single_item, parent, false);
        return new MyProductSingleRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValue;
        holder.title.setText(mValue.getTitle());
        holder.description.setText(mValue.getDescription());
        holder.price.setText(String.valueOf("$" + mValue.getPrice()));
        String s = "data:image/jpeg;base64,";
        byte[] decodedString = Base64.decode(mValue.getImages().get(0).getValue().replace(s, ""), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
        holder.image.setImageBitmap(decodedByte);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView title;
        public final TextView description;
        public final TextView price;
        public final ImageView image;

        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.productTitle);
            description = (TextView) view.findViewById(R.id.productDetail);
            price = (TextView) view.findViewById(R.id.price);
            image = (ImageView) view.findViewById(R.id.productImageView);
        }

    }
}
