package com.laboratorio.myapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laboratorio.myapplication.model.Product;

public class MyAboutUsRecyclerViewAdapter extends RecyclerView.Adapter<MyAboutUsRecyclerViewAdapter.ViewHolder>{


    @Override
    public MyAboutUsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAboutUsRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView aboutus;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            aboutus = (TextView) view.findViewById(R.id.aboutus);
        }

    }

}
