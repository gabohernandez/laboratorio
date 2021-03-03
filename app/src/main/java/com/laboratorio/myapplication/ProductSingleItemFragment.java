package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laboratorio.myapplication.model.Product;

import java.util.List;

public class ProductSingleItemFragment extends Fragment {


    public Product product;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductSingleItemFragment newInstance(int columnCount) {
        ProductSingleItemFragment fragment = new ProductSingleItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_single_item, container, false);
        ((TextView)view.findViewById(R.id.producttitle)).setText(this.product.getTitle());
        ((TextView) view.findViewById(R.id.productDescription)).setText(this.product.getDescription());
        ((TextView)view.findViewById(R.id.productPrice)).setText(String.valueOf("$" + this.product.getPrice()));

        String s = "data:image/jpeg;base64,";
        byte[] decodedString = Base64.decode(this.product.getImages().get(0).getValue().replace(s, ""), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
        ((ImageView)view.findViewById(R.id.imageView2)).setImageBitmap(decodedByte);
        return view;
    }


}
