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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 2/3/2015.
 */
public class MyBaseAdapter extends BaseAdapter {
    private final Context context;
    private final List<Map<String,?>> movieList;

    public MyBaseAdapter(Context context, List<Map<String,?>> movieList){
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public boolean isEnabled(int position){
        if (position < 2){
            return false;
        }
        else{
            return true;
        }

    }

        @Override
        public int getCount() {
            return movieList.size();
        }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    public void removeItem(int position){
        movieList.remove(position);
    }

    public void duplicateItem(int position){
        Map<String,?> dupItem;
        dupItem = (Map<String, ?>) ((HashMap<String, ?>) getItem(position)).clone();
        movieList.add(position+1,dupItem);

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
            rowView = inflater.inflate(R.layout.list_row, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) rowView.findViewById(R.id.icon);
            holder.name = (TextView) rowView.findViewById(R.id.title);
            holder.description = (TextView) rowView.findViewById(R.id.description);
            holder.selection = (CheckBox) rowView.findViewById(R.id.selection);
            holder.year = (TextView) rowView.findViewById(R.id.year);
            holder.time = (TextView) rowView.findViewById(R.id.time);
            holder.rating = (TextView) rowView.findViewById(R.id.rating);
            rowView.setTag(holder);

        }
        else{
            rowView =view;
            holder = (ViewHolder) view.getTag();
        }

        if (position % 2 == 0){
            rowView.setBackgroundColor(Color.parseColor("#4DB8FF"));
        }
        else{
            rowView.setBackgroundColor(Color.parseColor("#CCCCFF"));
        }


        Map <String, ?> entry = movieList.get(position);
        holder.image.setImageResource((Integer) entry.get("image"));
        holder.name.setText((String) entry.get("name"));
        holder.description.setText((String) entry.get("description"));
        holder.selection.setChecked((Boolean) entry.get("selection"));
        holder.year.setText((String) entry.get("year"));
        holder.rating.setText((Double) entry.get("rating")+ "/10");
        holder.time.setText((String) entry.get("length"));


        return rowView;
    }

    class ViewHolder{
        TextView name;
        ImageView image;
        TextView time;
        TextView year;
        TextView rating;
        TextView description;
        CheckBox selection;
    }
}
