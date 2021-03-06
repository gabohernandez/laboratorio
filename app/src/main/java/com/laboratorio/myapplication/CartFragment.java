package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laboratorio.myapplication.model.Product;

import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CartFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public List<Product> products;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private Context context;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CartFragment newInstance(int columnCount) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_product_item_list, container, false);
        this.context = container.getContext();
        // Set the adapter
        /* if (view instanceof RecyclerView) {*/
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list); //(RecyclerView) view;
        if (mColumnCount <= 1) {
            LinearLayoutManager layout = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layout);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layout.getOrientation()));
        } else {
            GridLayoutManager layout = new GridLayoutManager(context, mColumnCount);
            recyclerView.setLayoutManager(layout);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), layout.getOrientation()));
        }

        recyclerView.setAdapter(new MyCartRecyclerViewAdapter(products));
        //}

        Button finishBuyButton = view.findViewById(R.id.finishBuyButton);
        finishBuyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    double[] distance = {
                            0,0
                    };
                    ((MainActivity) context).showLastStep(null, distance);
                }
            }
        });
        if (this.products.isEmpty()) {
            finishBuyButton.setVisibility(View.GONE);
        } else {
            finishBuyButton.setVisibility(View.VISIBLE);
        }
        return view;
    }

}