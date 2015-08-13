package com.example.matthew.lab_6;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/**
 * Created by Matthew on 2/8/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<Map<String, ?>> mDataSet;
    private Context mContext;
    OnItemClickListener mItemClickListener;
    private int type;

    public MyRecyclerViewAdapter(Context myContext, List<Map<String, ?>> myDataSet, int myType){
        mContext = myContext;
        mDataSet = myDataSet;
        type = myType;
    }
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View v;
        if (type == 0) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardview, parent, false);
        }
        else{
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cardviewfour, parent, false);
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
        public ImageButton vImageMenu;
        public TextView vYear;
        public TextView vLength;
        public TextView vRating;



        public ViewHolder (View v) {
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.image);
            vTitle = (TextView) v.findViewById(R.id.name);
            if (type == 0){
                vDescription = (TextView) v.findViewById(R.id.description);
                vImageMenu = (ImageButton) v.findViewById(R.id.selection);
                vYear = (TextView) v.findViewById(R.id.year);
                vLength = (TextView) v.findViewById(R.id.time);
                vRating = (TextView) v.findViewById(R.id.rating);
                vImageMenu.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if (mItemClickListener != null){
                            mItemClickListener.onItemCheckBoxClick(v, getPosition());
                        }
                    }
                });
                }
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


        }
        public void bindMovieData(Map<String,?> entry){
            //vIcon.setImageResource((Integer) entry.get("image"));
            MyDownloadImageAsyncTask task = new MyDownloadImageAsyncTask(vIcon);
            task.execute(new String[]{(String) entry.get("url")});
            vTitle.setText((String) entry.get("name"));
            if (type == 0) {
                vDescription.setText((String) entry.get("description"));
                vYear.setText((String) entry.get("year"));
                vRating.setText((Double) entry.get("rating") + "/10");
                vLength.setText((String) entry.get("length"));
            }
        }
    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
        public void onItemCheckBoxClick(View view, int position);
    }

    public void SetOnItemClickListener (final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public static class MyDownloadImageAsyncTask extends AsyncTask<String,Void,Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public MyDownloadImageAsyncTask(ImageView imv) {
            imageViewReference = new WeakReference<ImageView>(imv);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            for (String url : urls) {
                bitmap = MyUtility.downloadImage(url);
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            if (imageViewReference != null && bitmap != null){
                final ImageView imageView = imageViewReference.get();
                if (imageView != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }

    }
}
