package com.example.matthew.lab_6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Matthew on 2/24/2015.
 */
public class Fragment_DetailView extends Fragment {

    public static final String ARG_MOVIE = "movie";
    private HashMap<String, ?> movie;
    ShareActionProvider mShareActionProvider;
    private int total = 0;

    public static Fragment_DetailView newInstance(HashMap<String,?> input) {
        Fragment_DetailView fragment = new Fragment_DetailView();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOVIE, input);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            movie = (HashMap<String, ?>) getArguments().getSerializable(ARG_MOVIE);
        }
        if (savedInstanceState != null){
            total = savedInstanceState.getInt("Total");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail_view, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, movie.get("name") + "\n" +  movie.get("description"));
        mShareActionProvider.setShareIntent(intentShare);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Total",total);
    }





    public Fragment_DetailView() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.fragment_movie, container, false);

        TextView description = (TextView) rootView.findViewById(R.id.description);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView rating = (TextView) rootView.findViewById(R.id.starRating);
        TextView director = (TextView) rootView.findViewById(R.id.director);
        TextView actors = (TextView) rootView.findViewById(R.id.actors);
        TextView timeLength = (TextView) rootView.findViewById(R.id.length);
        RatingBar stars = (RatingBar) rootView.findViewById(R.id.stars);
        ImageView poster = (ImageView) rootView.findViewById(R.id.movieImage);

        title.setText(movie.get("name").toString() + "(" + movie.get("year").toString() + ")");
        description.setText(movie.get("description").toString());
        stars.setRating(Float.parseFloat(movie.get("rating").toString()) / 2);
        rating.setText((Float.parseFloat(movie.get("rating").toString()) / 2) + "");
        director.setText(movie.get("director").toString());
        actors.setText(movie.get("stars").toString());
        timeLength.setText(movie.get("length").toString());
        //poster.setImageDrawable(getResources().getDrawable(Integer.parseInt(movie.get("image").toString())));
        MyRecyclerViewAdapter.MyDownloadImageAsyncTask task = new MyRecyclerViewAdapter.MyDownloadImageAsyncTask(poster);
        task.execute(new String[]{(String) movie.get("url")});

        return rootView;
    }

}