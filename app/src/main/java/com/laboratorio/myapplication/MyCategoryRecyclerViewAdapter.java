package com.laboratorio.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.laboratorio.myapplication.dummy.DummyContent.DummyItem;
import com.laboratorio.myapplication.model.Category;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCategoryRecyclerViewAdapter extends RecyclerView.Adapter<MyCategoryRecyclerViewAdapter.ViewHolder> {

    private final List<Category> mValues;
    private Context context;

    public MyCategoryRecyclerViewAdapter(List<Category> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(mValues.get(position).getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    ((MainActivity) context).changeFragmentToProductsWithCategory(mValues.get(position).getId());
                }
            }
        });

        if (mValues.get(position).getImage() != null) {
            String s = "data:image/jpeg;base64,";
            byte[] decodedString = Base64.decode(mValues.get(position).getImage().getValue().replace(s, ""), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.image.setImageBitmap(decodedByte);
        }//TODO: Mostrar una no-image en el else
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final ImageView image;

        public Category mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.name);
            image = (ImageView) view.findViewById(R.id.categoryImageView);


        }

    }
}