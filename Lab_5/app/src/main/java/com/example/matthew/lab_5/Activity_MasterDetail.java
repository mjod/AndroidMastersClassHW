package com.example.matthew.lab_5;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class Activity_MasterDetail extends ActionBarActivity{

    private static boolean mTwoPane = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_list, PlaceholderFragment.newInstance(1))
                    .commit();
        }
        if (findViewById(R.id.item_detail_container) != null){
            mTwoPane = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_master_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public static final String ARG_NUM = "fragment_id";
        public static final String ARG_MOVIE = "movie";

        MovieData movieData;
        HashMap<String, ?> movie;
        Button aboutMe, viewPager, masterDetail;
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            movieData = new MovieData();
            setRetainInstance(true);
        }



        public static PlaceholderFragment newInstance(int num) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_NUM, num);
            fragment.setArguments(args);
            return fragment;
        }
        public static PlaceholderFragment newInstance(HashMap<String, ?> movie) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putSerializable(ARG_MOVIE, movie);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;


            final int id = getArguments().getInt(ARG_NUM);
            HashMap<String, ?> foundMovie = (HashMap<String,?>) getArguments().getSerializable(ARG_MOVIE);
            if (id == 1) {
                rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
                masterDetailView(rootView);
            }
            else if (foundMovie != null){
                    rootView = inflater.inflate(R.layout.fragment_movie, container, false);
                    TextView description = (TextView) rootView.findViewById(R.id.description);
                    TextView title = (TextView) rootView.findViewById(R.id.title);
                    TextView rating = (TextView) rootView.findViewById(R.id.starRating);
                    TextView director = (TextView) rootView.findViewById(R.id.director);
                    TextView actors = (TextView) rootView.findViewById(R.id.actors);
                    TextView timeLength = (TextView) rootView.findViewById(R.id.length);
                    RatingBar stars = (RatingBar) rootView.findViewById(R.id.stars);
                    ImageView poster = (ImageView) rootView.findViewById(R.id.movieImage);

                    title.setText(foundMovie.get("name").toString() + "(" + foundMovie.get("year").toString() + ")");
                    description.setText(foundMovie.get("description").toString());
                    stars.setRating(Float.parseFloat(foundMovie.get("rating").toString()) / 2);
                    rating.setText((Float.parseFloat(foundMovie.get("rating").toString()) / 2) + "");
                    director.setText(foundMovie.get("director").toString());
                    actors.setText(foundMovie.get("stars").toString());
                    timeLength.setText(foundMovie.get("length").toString());
                    poster.setImageDrawable(getResources().getDrawable(Integer.parseInt(foundMovie.get("image").toString())));
            }
            return rootView;
        }

        public void masterDetailView(View rootView){
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            final MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieData.getMoviesList());
            myRecyclerViewAdapter.SetOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    movie = movieData.getItem(position);
                    Toast.makeText(getActivity(), "Selected: " + (String) movie.get("name"), Toast.LENGTH_SHORT).show();
                    if (mTwoPane) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, PlaceholderFragment.newInstance(movie))
                                .addToBackStack(null)
                                .commit();
                    }
                    else{
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_list, PlaceholderFragment.newInstance(movie))
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
            mRecyclerView.setAdapter(myRecyclerViewAdapter);
        }
    }
}
