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
import com.laboratorio.myapplication.model.Cart;
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
public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    private List<Product> mValues = new ArrayList<>();
    private Map<Long, Product> cartProducts = new HashMap<>();;
    private Context context;

    public CartRecyclerViewAdapter(List<Product> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_product_item, parent, false);
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
        holder.subtotal.setText(String.valueOf("$" + String.valueOf(new BigDecimal(mValues.get(position).getCount()).multiply(mValues.get(position).getPrice()))));


        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Product p = mValues.get(position);
                mValues.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mValues.size());
                if (context instanceof  MainActivity){
                   ((MainActivity) context).deleteProduct(p);
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
        public final TextView subtotal;
        public final ImageView image;
        public final Button buttonDelete;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            count = (TextView) view.findViewById(R.id.count);
            price = (TextView) view.findViewById(R.id.price);
            subtotal = (TextView) view.findViewById(R.id.subtotal);
            image = (ImageView) view.findViewById(R.id.image);
            buttonDelete = (Button) view.findViewById(R.id.buttonPlus);
         }

    }

}