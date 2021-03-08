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
import com.laboratorio.myapplication.model.Report;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyReportRecyclerViewAdapter extends RecyclerView.Adapter<MyReportRecyclerViewAdapter.ViewHolder> {

    private List<Report> mValues;
    private Context context;

    public MyReportRecyclerViewAdapter(List<Report> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getTitle());
        holder.subtitle.setText(mValues.get(position).getSubtitle());
        String s = "data:image/jpeg;base64,";
        byte[] decodedString = Base64.decode(mValues.get(position).getImage().getValue(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.image.setImageBitmap(decodedByte);

        holder.title.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    ((MainActivity) context).changeFragmentToSingleReport(mValues.get(position).getId());
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
        public final ImageView image;
        public final TextView title;
        public final TextView subtitle;
        public Report mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            image = (ImageView) view.findViewById(R.id.reportImage);
            title = (TextView) view.findViewById(R.id.reportTitle);
            subtitle = (TextView) view.findViewById(R.id.reportSubtitle);
        }

    }

}