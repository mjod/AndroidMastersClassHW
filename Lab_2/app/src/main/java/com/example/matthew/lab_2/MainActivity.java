package com.example.matthew.lab_2;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.HashMap;

public class MainActivity extends ActionBarActivity {
    static int current_movie = 0;
    static MovieData movie = new MovieData();
    static HashMap movieSet;
    static int height = 400;
    static int width = 225;
    static int heightLand = 340;
    static int widthLand = 191;

    static float mult = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance(R.layout.fragment_main))
                    .commit();
        }
        movieSet = movie.getItem(current_movie);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        int id = item.getItemId();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(id))
                .commit();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public static final String ARG_NUM = "fragment_id";

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
                case R.id.home:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    break;
                case R.id.about_me:
                    rootView = inflater.inflate(R.layout.fragment_about_me, container, false);
                    break;
                case R.id.relative_layout:
                    rootView = inflater.inflate(R.layout.fragment_relative_layout, container, false);
                    break;
                case R.id.linear_layout:
                    rootView = inflater.inflate(R.layout.fragment_linear_layout, container, false);
                    break;
                case R.id.grid_layout:
                    rootView = inflater.inflate(R.layout.fragment_grid_layout, container, false);
                    break;
                case R.id.seek_layout:
                    rootView = inflater.inflate(R.layout.fragment_seekbar, container, false);
                    final ImageButton img = (ImageButton) rootView.findViewById(R.id.movingImage);
                    TextView seekTitle = (TextView) rootView.findViewById(R.id.seekBarTitle);
                    android.view.ViewGroup.LayoutParams params = img.getLayoutParams();
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        params.height = height;
                        params.width = width;
                    }
                    else {
                        params.height = heightLand;
                        params.width = widthLand;
                    }

                    img.setLayoutParams(params);
                    SeekBar sbar = (SeekBar) rootView.findViewById(R.id.seekBar1);
                    sbar.setOnSeekBarChangeListener(customSeekBarListener);
                    img.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            android.view.ViewGroup.LayoutParams params = img.getLayoutParams();
                            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                params.height = height;
                                params.width = width;
                            }
                            else {
                                params.height = heightLand;
                                params.width = widthLand;
                            }
                            SeekBar sbar = (SeekBar) getView().findViewById(R.id.seekBar1);
                            sbar.setProgress(50);
                            img.setLayoutParams(params);
                            return true;
                        }
                    });
                    break;

                case R.id.movie_layout:
                    rootView = inflater.inflate(R.layout.fragment_movie, container, false);
                    TextView description = (TextView) rootView.findViewById(R.id.description);
                    TextView title = (TextView) rootView.findViewById(R.id.title);
                    TextView rating = (TextView) rootView.findViewById(R.id.starRating);
                    TextView director = (TextView) rootView.findViewById(R.id.director);
                    TextView actors = (TextView) rootView.findViewById(R.id.actors);
                    TextView timeLength = (TextView) rootView.findViewById(R.id.length);
                    RatingBar stars = (RatingBar) rootView.findViewById(R.id.stars);
                    ImageView poster = (ImageView) rootView.findViewById(R.id.movieImage);

                    title.setText(movieSet.get("name").toString() + "(" + movieSet.get("year").toString() + ")");
                    description.setText(movieSet.get("description").toString());
                    stars.setRating(Float.parseFloat(movieSet.get("rating").toString()) / 2);
                    rating.setText((Float.parseFloat(movieSet.get("rating").toString()) / 2) + "");
                    director.setText(movieSet.get("director").toString());
                    actors.setText(movieSet.get("stars").toString());
                    timeLength.setText(movieSet.get("length").toString());
                    poster.setImageDrawable(getResources().getDrawable(Integer.parseInt(movieSet.get("image").toString())));

                    final Button button = (Button) rootView.findViewById(R.id.movie_button);
                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            current_movie = movie.getSize()-1 > current_movie ? current_movie + 1 : 0;
                            TextView title = (TextView) getView().findViewById(R.id.title);
                            movieSet = movie.getItem(current_movie);
                            TextView description = (TextView) getView().findViewById(R.id.description);
                            TextView rating = (TextView) getView().findViewById(R.id.starRating);
                            TextView director = (TextView) getView().findViewById(R.id.director);
                            TextView actors = (TextView) getView().findViewById(R.id.actors);
                            TextView timeLength = (TextView) getView().findViewById(R.id.length);
                            RatingBar stars = (RatingBar) getView().findViewById(R.id.stars);
                            ImageView poster = (ImageView) getView().findViewById(R.id.movieImage);

                            timeLength.setText(movieSet.get("length").toString());
                            title.setText(movieSet.get("name").toString()+ "(" + movieSet.get("year").toString() + ")");
                            description.setText(movieSet.get("description").toString());
                            stars.setRating(Float.parseFloat(movieSet.get("rating").toString()) / 2);
                            rating.setText((Float.parseFloat(movieSet.get("rating").toString()) / 2) + "");
                            director.setText(movieSet.get("director").toString());
                            actors.setText(movieSet.get("stars").toString());
                            poster.setImageDrawable(getResources().getDrawable(Integer.parseInt(movieSet.get("image").toString())));
                        }
                    });
                    break;
                default:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
            }
            return rootView;
        }

        private SeekBar.OnSeekBarChangeListener customSeekBarListener =
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        ImageButton img = (ImageButton) getView().findViewById(R.id.movingImage);
                        TextView values = (TextView) getView().findViewById(R.id.seekBarTitle);
                        android.view.ViewGroup.LayoutParams params = img.getLayoutParams();
                        int tempHeight = 0;
                        int tempWidth = 0;
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                            tempHeight = height;
                            tempWidth = width;
                        }
                        else {
                            tempHeight = heightLand;
                            tempWidth = heightLand;
                        }

                        params.height = Math.round(((float)tempHeight*(mult+(float)(progress-50)/100)));
                        params.width = Math.round(((float)tempWidth*(mult+(float)(progress-50)/100)));
                        img.setLayoutParams(params);
                        //values.setText(((float)((progress-50)/100)) + ":" + ((float)((progress-50)/100)));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
        };
    }
}
