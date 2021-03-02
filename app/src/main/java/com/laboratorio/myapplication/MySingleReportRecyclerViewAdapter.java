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

import com.laboratorio.myapplication.model.Report;

public class MySingleReportRecyclerViewAdapter extends RecyclerView.Adapter<MySingleReportRecyclerViewAdapter.ViewHolder> {

    private Context context;

    @Override
    public MySingleReportRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new MySingleReportRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MySingleReportRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(holder.mItem.getTitle());
        holder.subtitle.setText(holder.mItem.getSubtitle());
        holder.text.setText(holder.mItem.getText());
        holder.url.setText(holder.mItem.getUrl());
        String s = "data:image/jpeg;base64,";
        byte[] decodedString = Base64.decode(holder.mItem.getImage().getValue().replace(s, ""), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
        holder.image.setImageBitmap(decodedByte);
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private Long id;
        private ImageView image;
        private TextView title;
        private TextView subtitle;
        private TextView description;
        private TextView text;
        private TextView url;
        public Report mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.titleText);
            subtitle = (TextView) view.findViewById(R.id.subtitleText);
            image = (ImageView) view.findViewById(R.id.producerImageView);
            text = (TextView) view.findViewById(R.id.contentText);
            url = (TextView) view.findViewById(R.id.url);
        }

    }

}
