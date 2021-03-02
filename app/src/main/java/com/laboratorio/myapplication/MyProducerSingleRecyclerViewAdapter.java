package com.laboratorio.myapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laboratorio.myapplication.model.Producer;

public class MyProducerSingleRecyclerViewAdapter extends RecyclerView.Adapter<MyProducerSingleRecyclerViewAdapter.ViewHolder>{

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView email;
        public final TextView phone;
        public final TextView origin;
        public final TextView description;
        public final ImageView image;
        public Producer mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.producerName);
            email = (TextView) view.findViewById(R.id.producerEmail);
            phone = (TextView) view.findViewById(R.id.producerPhone);
            origin = (TextView) view.findViewById(R.id.producerOrigin);
            description = (TextView) view.findViewById(R.id.producerDescription);
            image = (ImageView) view.findViewById(R.id.image);
        }

    }
}
