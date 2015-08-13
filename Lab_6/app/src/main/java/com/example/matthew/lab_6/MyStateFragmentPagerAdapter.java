package com.example.matthew.lab_6;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Locale;


/**
 * Created by Matthew on 2/18/2015.
 */
public class MyStateFragmentPagerAdapter extends FragmentStatePagerAdapter {
    int count;
    MovieDataJson movieData;

    public MyStateFragmentPagerAdapter(FragmentManager fm, int size, Context c){
        super(fm);
        count = size;
        try {
            movieData = new MovieDataJson(c);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = Fragment_RecyclerView.newInstance(0);
                break;
            case 1:
                fragment = Fragment_RecyclerView.newInstance(1);
                break;
            default:
                fragment = Fragment_RecyclerView.newInstance(0);
        }
        return fragment;
    }
    @Override
    public int getCount(){ return count;}

    @Override
    public CharSequence getPageTitle(int position){
        Locale l = Locale.getDefault();
        String name;
        switch (position) {
            case 0:
                name = "Most Popular";
                break;
            case 1:
                name = "Top Selling";
                break;
            default:
                name = "Home";
                break;
        }
        return name.toUpperCase(l);
    }

}
