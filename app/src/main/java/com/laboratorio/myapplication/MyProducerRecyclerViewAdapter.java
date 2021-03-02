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
import com.laboratorio.myapplication.model.Producer;
import com.laboratorio.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MyProducerRecyclerViewAdapter extends RecyclerView.Adapter<MyProducerRecyclerViewAdapter.ViewHolder>{

    private List<Producer> mValues = new ArrayList<>();
    private Context context;


    public MyProducerRecyclerViewAdapter(List<Producer> items) {
        mValues = items;
    }

    @Override
    public MyProducerRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new MyProducerRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProducerRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getName());
        holder.description.setText(mValues.get(position).getDescription());
        String s = "data:image/jpeg;base64,";
        byte[] decodedString = Base64.decode(mValues.get(position).getImages().get(1).getValue().replace(s, ""), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
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
        public final ImageView image;
        public Producer mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            image = (ImageView) view.findViewById(R.id.image);
        }

    }
}
