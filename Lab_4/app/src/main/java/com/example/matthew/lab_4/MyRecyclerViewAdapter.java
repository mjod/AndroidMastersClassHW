package com.example.matthew.lab_4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 2/8/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<Map<String, ?>> mDataSet;
    private Context mContext;
    OnItemClickListener mItemClickListener;

    public MyRecyclerViewAdapter (Context myContext, List<Map<String,?>> myDataSet){
        mContext = myContext;
        mDataSet = myDataSet;
    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
    @Override
    public int getItemViewType(int position) {
        Map<String, ?> movie = mDataSet.get(position);
        double rate = (Double) movie.get("rating");
        if (rate >= 8){
            return 1;
        }
        else{
            return 0;
        }
    }

    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View v;

        if (viewType == 0) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview, parent, false);
        }
        else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview2, parent, false);
        }


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public void onBindViewHolder (ViewHolder holder, int position){
        Map<String, ?> movie = mDataSet.get(position);
        holder.bindMovieData (movie);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public CheckBox vCheckbox;
        public TextView vYear;
        public TextView vLength;
        public TextView vRating;



        public ViewHolder (View v){
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.image);
            vTitle = (TextView) v.findViewById(R.id.name);
            vDescription = (TextView) v.findViewById(R.id.description);
            vCheckbox = (CheckBox) v.findViewById(R.id.selection);
            vYear = (TextView) v.findViewById(R.id.year);
            vLength = (TextView) v.findViewById(R.id.time);
            vRating = (TextView) v.findViewById(R.id.rating);

            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (mItemClickListener != null){
                        mItemClickListener.onItemClick(v, getPosition());
                    }
                }
            });
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemLongClick(v, getPosition());
                    }
                    return true;
                }
            });
            vCheckbox.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (mItemClickListener != null){
                        mItemClickListener.onItemCheckBoxClick(v, getPosition());
                    }
                }
            });

        }
        public void bindMovieData(Map<String,?> entry){
            vIcon.setImageResource((Integer) entry.get("image"));
            vTitle.setText((String) entry.get("name"));
            vDescription.setText((String) entry.get("description"));
            vCheckbox.setChecked((Boolean) entry.get("selection"));
            vYear.setText((String) entry.get("year"));
            vRating.setText((Double) entry.get("rating")+ "/10");
            vLength.setText((String) entry.get("length"));
        }
    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongClick (View view, int position);
        public void onItemCheckBoxClick(View view, int position);
    }

    public void SetOnItemClickListener (final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
