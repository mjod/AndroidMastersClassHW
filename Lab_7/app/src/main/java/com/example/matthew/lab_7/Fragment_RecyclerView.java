package com.example.matthew.lab_7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class Fragment_RecyclerView extends Fragment {

    public static final String ARG_NUM = "fragment_id";
    static MovieDataJson movieData;
    static MyRecyclerViewAdapter myRecyclerViewAdapter;
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            movieData = new MovieDataJson(getActivity());
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        setRetainInstance(true);
    }

    public static Fragment_RecyclerView newInstance(int num) {
        Fragment_RecyclerView fragment = new Fragment_RecyclerView();
        Bundle args = new Bundle();
        args.putInt(ARG_NUM, num);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_RecyclerView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final int id = getArguments().getInt(ARG_NUM);
        View rootView;
        RecyclerView.LayoutManager mLayoutManager;
        rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        if (id == 0) {
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieData.getMoviesList(),0);
        }
        else {
            mLayoutManager = new GridLayoutManager(getActivity(),4);
            mRecyclerView.setLayoutManager(mLayoutManager);
            myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieData.getMoviesList(),1);
        }

        myRecyclerViewAdapter.SetOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
            }

            @Override
            public void onItemClick(View v, int position) {
                Map<String,?> entry = (Map<String,?>) movieData.getItem(position);
                Toast.makeText(getActivity(), "Selected: " + entry.get("name"), Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onItemCheckBoxClick(View v, int position) {

            }

        });
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
        return rootView;
    }

}