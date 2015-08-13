package com.example.matthew.lab_5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Locale;


/**
 * Created by Matthew on 2/18/2015.
 */
public class MyStateFragmentPagerAdapter extends FragmentStatePagerAdapter {
    int count;
    MovieData movieData = new MovieData();

    public MyStateFragmentPagerAdapter(FragmentManager fm, int size){
        super(fm);
        count = size;
    }
    @Override
    public Fragment getItem(int position) {
        return Fragment_DetailView.newInstance((HashMap<String,?>)movieData.getItem(position));
    }
    @Override
    public int getCount(){ return count;}

    @Override
    public CharSequence getPageTitle(int position){
        Locale l = Locale.getDefault();
        HashMap<String, ?> movie = (HashMap<String,?>) movieData.getItem(position);
        String name = (String) movie.get("name");
        return name.toUpperCase(l);
    }

    public static class Fragment_DetailView extends Fragment {

        public static final String ARG_MOVIE = "movie";
        private HashMap<String, ?> movie;

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
            final TextView counterView = (TextView) rootView.findViewById(R.id.textClicked);
            counterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    total++;
                    counterView.setText("Total clicked is " +total);
                }
            });

            title.setText(movie.get("name").toString() + "(" + movie.get("year").toString() + ")");
            description.setText(movie.get("description").toString());
            stars.setRating(Float.parseFloat(movie.get("rating").toString()) / 2);
            rating.setText((Float.parseFloat(movie.get("rating").toString()) / 2) + "");
            director.setText(movie.get("director").toString());
            actors.setText(movie.get("stars").toString());
            timeLength.setText(movie.get("length").toString());
            poster.setImageDrawable(getResources().getDrawable(Integer.parseInt(movie.get("image").toString())));

            return rootView;
        }

    }

}
