package com.laboratorio.myapplication;

import android.content.Context;
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

public class MyProductSingleRecyclerViewAdapter extends RecyclerView.Adapter {

    private Product mValue = new Product();
    private Context context;

    public MyProductSingleRecyclerViewAdapter(Product product) {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new MyProductSingleRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

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
