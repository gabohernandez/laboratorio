package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.myapplication.dummy.DummyContent;
import com.laboratorio.myapplication.model.Product;
import com.laboratorio.myapplication.service.ProductService;
import com.laboratorio.myapplication.service.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * A fragment representing a list of Items.
 */
public class ProductFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public List<Product> products;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductFragment newInstance(int columnCount) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-235-40-183.compute-1.amazonaws.com/api/product")
//                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();
//
//        Service service = retrofit.create(Service.class);
//
//        List<Product> products = service.getProducts();
/*
        Intent i  = new Intent(MainActivity.class, ProductService.class);
        i.setAction("Buscar");
        startService(i);
*/
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_item_list, container, false);

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


            recyclerView.setAdapter(new MyProductRecyclerViewAdapter(products));
        }
        return view;
    }

    public void modifyCount(int count){
        //TODO: Sumar cantidad
    }
}