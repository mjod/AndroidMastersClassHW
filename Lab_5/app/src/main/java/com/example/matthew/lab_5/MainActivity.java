package com.example.matthew.lab_5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance(R.layout.fragment_main))
                    .commit();
        }

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public static final String ARG_NUM = "fragment_id";
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;


            final int id = getArguments().getInt(ARG_NUM);
            switch (id) {
                case R.layout.fragment_main:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    homePageView(rootView);
                    break;
                case R.layout.fragment_about_me:
                    rootView = inflater.inflate(R.layout.fragment_about_me, container, false);
                    break;
                case R.layout.fragment_recyclerview:
                    rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
                    masterDetailView(rootView);
                    break;
                case R.layout.fragment_movie:
                    rootView = inflater.inflate(R.layout.fragment_movie, container, false);
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
                    poster.setImageDrawable(getResources().getDrawable(Integer.parseInt(movie.get("image").toString())));
                    break;
                default:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);

            }
            return rootView;
        }
        public void homePageView(View rootView){
            aboutMe = (Button) rootView.findViewById(R.id.about_me);
            viewPager = (Button) rootView.findViewById(R.id.view_page);
            masterDetail = (Button) rootView.findViewById(R.id.master_detail_flow);
            aboutMe.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container,PlaceholderFragment.newInstance(R.layout.fragment_about_me))
                            .addToBackStack("about me")
                            .commit();
                }
            });
            viewPager.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
                    startActivity(intent);
                }
            });
            masterDetail.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), Activity_MasterDetail.class);
                    startActivity(intent);
                }
            });
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
                    int width = getActivity().getResources().getDisplayMetrics().widthPixels;
                    int height = getActivity().getResources().getDisplayMetrics().heightPixels;
                    Toast.makeText(getActivity(), width + "   " + height, Toast.LENGTH_SHORT).show();
                    if (width > height) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.movieInfo, PlaceholderFragment.newInstance(R.layout.fragment_movie))
                                .addToBackStack("fragmentMovie")
                                .commit();
                    }
                    else{
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, PlaceholderFragment.newInstance(R.layout.fragment_movie))
                                .addToBackStack("fragmentMovie")
                                .commit();
                    }
                }
            });
            mRecyclerView.setAdapter(myRecyclerViewAdapter);
        }
    }
}
