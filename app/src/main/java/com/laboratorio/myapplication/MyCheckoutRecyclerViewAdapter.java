package com.laboratorio.myapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyCheckoutRecyclerViewAdapter extends RecyclerView.Adapter<MyCheckoutRecyclerViewAdapter.ViewHolder> {

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
        public final TextView amount;
        public final RadioGroup options;
        public final RadioButton cash;
        public final RadioButton credit;
        public final RadioButton debit;
        public final Button buyButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            amount = (TextView) view.findViewById(R.id.amount);
            options = (RadioGroup) view.findViewById(R.id.radioGroup);
            cash = (RadioButton) view.findViewById(R.id.cash);
            credit = (RadioButton) view.findViewById(R.id.creditCard);
            debit = (RadioButton) view.findViewById(R.id.debitCard);
            buyButton = (Button) view.findViewById(R.id.buyButton);
        }

    }
}
