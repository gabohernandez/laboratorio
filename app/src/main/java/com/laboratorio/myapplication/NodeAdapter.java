package com.laboratorio.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.laboratorio.myapplication.model.Node;

import java.util.ArrayList;

public class NodeAdapter extends ArrayAdapter<Node> {

    public NodeAdapter(Context context, ArrayList<Node> node) {
        super(context, 0, node);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Node node = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.node, parent, false);

        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.nodeName);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.nodeAddress);

        // Populate the data into the template view using the data object
        tvName.setText(node.getName());
        tvAddress.setText(".hometown");

        // Return the completed view to render on screen

        return convertView;

    }


}
