package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laboratorio.myapplication.model.Report;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ReportFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public List<Report> report;
    // TODO: Customize parameters
    private int mColumnCount = 1;

    // TODO: Customize parameter initialization

    @SuppressWarnings("unused")
    public static ReportFragment newInstance(int columnCount) {
        ReportFragment fragment = new ReportFragment();
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
        View view = inflater.inflate(R.layout.report_item_list, container, false);

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

            recyclerView.setAdapter(new MyReportRecyclerViewAdapter(report));
        }
        return view;
    }

}