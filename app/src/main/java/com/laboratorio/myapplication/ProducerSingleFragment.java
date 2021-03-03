package com.laboratorio.myapplication;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laboratorio.myapplication.model.Producer;

public class ProducerSingleFragment extends Fragment {

    public Producer producer;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProducerSingleFragment newInstance(int columnCount) {
        ProducerSingleFragment fragment = new ProducerSingleFragment();
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
        View view = inflater.inflate(R.layout.producer_single, container, false);
        ((TextView)view.findViewById(R.id.producerName)).setText(this.producer.getName());
        ((TextView) view.findViewById(R.id.producerDescription)).setText(this.producer.getDescription());
        ((TextView) view.findViewById(R.id.producerPhone)).setText((this.producer.getPhone()));
        ((TextView) view.findViewById(R.id.producerEmail)).setText((this.producer.getPhone()));
        ((TextView) view.findViewById(R.id.producerOrigin)).setText((this.producer.getOrigin()));
        if (this.producer.getImages().size() > 0) {
            String s = "data:image/jpeg;base64,";
            byte[] decodedString = Base64.decode(this.producer.getImages().get(0).getValue().replace(s, ""), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ((ImageView) view.findViewById(R.id.imageView3)).setImageBitmap(decodedByte);
        }
        return view;
    }


}
