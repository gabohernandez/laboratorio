package com.laboratorio.myapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.laboratorio.myapplication.dummy.DummyContent.DummyItem;
import com.laboratorio.myapplication.model.Cart;
import com.laboratorio.myapplication.model.Product;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProductRecyclerViewAdapter extends RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder> {

    private List<Product> mValues = new ArrayList<>();
    private List<Cart> cart = new ArrayList<>();


    public MyProductRecyclerViewAdapter(List<Product> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getTitle());
        holder.description.setText(mValues.get(position).getDescription());
        String s = "data:image/jpeg;base64,";
        byte[] decodedString = Base64.decode(mValues.get(position).getImages().get(0).getValue().replace(s, ""), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
        holder.image.setImageBitmap(decodedByte);
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
        public final ImageView image;
        public final Button buttonPlus;
        public final Button buttonSubstract;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            count = (TextView) view.findViewById(R.id.count);
            image = (ImageView) view.findViewById(R.id.image);
            buttonPlus = (Button) view.findViewById(R.id.buttonPlus);
            buttonPlus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    count.setText(String.valueOf(Integer.valueOf(count.getText().toString()) + 1));
                }
            });

            buttonSubstract = (Button) view.findViewById(R.id.buttonSubstract);
            buttonSubstract.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int newCount = Integer.valueOf(count.getText().toString()) - 1;
                    count.setText(String.valueOf(newCount <= 0 ? 0: newCount ));
                }
            });

//            mIdView = (TextView) view.findViewById(R.id.item_number);
//            mContentView = (TextView) view.findViewById(R.id.content);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
        //}
    }

}