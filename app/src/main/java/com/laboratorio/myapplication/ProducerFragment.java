package com.laboratorio.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laboratorio.myapplication.model.Producer;
import com.laboratorio.myapplication.model.Product;

import java.util.List;

public class ProducerFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public List<Producer> producers;

    public void onCreate(Bundle savedInstanceState) {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.producer_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                LinearLayoutManager layout = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layout);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layout.getOrientation()));
            } else {
                GridLayoutManager layout = new GridLayoutManager(context, mColumnCount);
                recyclerView.setLayoutManager(layout);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layout.getOrientation()));
            }

            recyclerView.setAdapter(new MyProducerRecyclerViewAdapter(producers));
        }
        return view;
    }
}
