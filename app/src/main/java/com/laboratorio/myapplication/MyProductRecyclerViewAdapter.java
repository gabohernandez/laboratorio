package com.laboratorio.myapplication;

import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.laboratorio.myapplication.dummy.DummyContent.DummyItem;
import com.laboratorio.myapplication.model.Cart;
import com.laboratorio.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProductRecyclerViewAdapter extends RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder> {

    private List<Product> mValues = new ArrayList<>();
    private List<Cart> cart = new ArrayList<>();
    private Context context;

    public MyProductRecyclerViewAdapter(List<Product> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getTitle());
        holder.description.setText(mValues.get(position).getDescription());
        holder.price.setText(String.valueOf("$" + mValues.get(position).getPrice()));
        String s = "data:image/jpeg;base64,";
        byte[] decodedString = Base64.decode(mValues.get(position).getImages().get(0).getValue().replace(s, ""), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
        holder.image.setImageBitmap(decodedByte);
        holder.count.setText(String.valueOf(mValues.get(position).getCount()));

        holder.buttonPlus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mValues.get(position).getStock() == Integer.valueOf(holder.count.getText().toString())) {
                    Toast.makeText(context, "No hay más stock disponible" ,Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.count.setText(String.valueOf(Integer.valueOf(holder.count.getText().toString()) + 1));
                mValues.get(position).setCount(Integer.valueOf(holder.count.getText().toString()));
                if (context instanceof MainActivity){
                    ((MainActivity) context).modifyTotal(mValues.get(position));
                }
            }
        });

        holder.buttonSubstract.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int newCount = Integer.valueOf(holder.count.getText().toString()) - 1;
                holder.count.setText(String.valueOf(newCount <= 0 ? 0: newCount ));
                mValues.get(position).setCount(Integer.valueOf(holder.count.getText().toString()));
                if (context instanceof  MainActivity){
                    ((MainActivity) context).modifyTotal(mValues.get(position));
                }
            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (context instanceof  MainActivity){
                    ((MainActivity) context).changeFragmentToSingleProduct(mValues.get(position).getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView description;
        public final TextView count;
        public final TextView price;
        public final ImageView image;
        public final Button buttonPlus;
        public final Button buttonSubstract;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.productTitle);
            description = (TextView) view.findViewById(R.id.productDetail);
            count = (TextView) view.findViewById(R.id.count);
            price = (TextView) view.findViewById(R.id.price);
            image = (ImageView) view.findViewById(R.id.productImageView);
            buttonPlus = (Button) view.findViewById(R.id.buttonPlus);
            buttonSubstract = (Button) view.findViewById(R.id.buttonSubstract);
        }

    }

}