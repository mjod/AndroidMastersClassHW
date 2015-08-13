package com.example.matthew.lab_3;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 2/5/2015.
 */
public class MyGridAdapter extends BaseAdapter {
    private final Context context;
    private final List<Map<String,?>> movieList;

    public MyGridAdapter(Context context, List<Map<String,?>> movieList){
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView;
        ViewHolder holder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.grid_row, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) rowView.findViewById(R.id.gridImage);
            holder.name = (TextView) rowView.findViewById(R.id.gridTitle);
            holder.length = (TextView) rowView.findViewById(R.id.gridLength);
            holder.year = (TextView) rowView.findViewById(R.id.gridYear);
            rowView.setTag(holder);

        }
        else{
            rowView =view;
            holder = (ViewHolder) view.getTag();
        }

        Map <String, ?> entry = movieList.get(position);
        holder.image.setImageResource((Integer) entry.get("image"));
        holder.name.setText((String) entry.get("name"));
        holder.length.setText((String) entry.get("length"));
        holder.year.setText((String) entry.get("year"));

        return rowView;
    }

    class ViewHolder{
        TextView name;
        TextView year;
        TextView length;
        ImageView image;
    }
}
