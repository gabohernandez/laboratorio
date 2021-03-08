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

import androidx.recyclerview.widget.RecyclerView;

import com.laboratorio.myapplication.dummy.DummyContent.DummyItem;
import com.laboratorio.myapplication.model.CartHistory;
import com.laboratorio.myapplication.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCartHistoryRecyclerViewAdapter extends RecyclerView.Adapter<MyCartHistoryRecyclerViewAdapter.ViewHolder> {

    private List<CartHistory> mValues = new ArrayList<>();
    private Map<Long, CartHistory> cartHistory = new HashMap<>();
    ;
    private Context context;

    public MyCartHistoryRecyclerViewAdapter(List<CartHistory> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.date.setText(mValues.get(position).getDate());
        holder.amount.setText(mValues.get(position).getAmount().toString());
        holder.node.setText(String.valueOf("$" + mValues.get(position).getNode().getName()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView date;
        public final TextView node;
        public final TextView amount;
        public CartHistory mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            date = (TextView) view.findViewById(R.id.dateCartHistory);
            node = (TextView) view.findViewById(R.id.nodeCartHistory);
            amount = (TextView) view.findViewById(R.id.amountCartHistory);

        }

    }

}