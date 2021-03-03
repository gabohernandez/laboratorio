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


import com.laboratorio.myapplication.model.Report;

public class SingleReportFragment extends Fragment {

    public Report report;

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SingleReportFragment newInstance(int columnCount) {
        SingleReportFragment fragment = new SingleReportFragment();
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
        View view = inflater.inflate(R.layout.single_report, container, false);
        ((TextView)view.findViewById(R.id.reportTitle)).setText(this.report.getTitle());
        ((TextView) view.findViewById(R.id.reportSubtitle)).setText(this.report.getSubtitle());
        ((TextView) view.findViewById(R.id.contentText)).setText(this.report.getText());
        ((TextView) view.findViewById(R.id.url)).setText(this.report.getUrl());
        String s = "data:image/jpeg;base64,";
        byte[] decodedString = Base64.decode(this.report.getImage().getValue().replace(s, ""), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
        ((ImageView)view.findViewById(R.id.reportImage)).setImageBitmap(decodedByte);
        return view;
    }

}
