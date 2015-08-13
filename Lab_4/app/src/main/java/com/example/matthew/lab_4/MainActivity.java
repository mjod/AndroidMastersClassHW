package com.example.matthew.lab_4;

import android.support.v7.app.ActionBarActivity;
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
import android.widget.CheckBox;
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
        MovieData movieData;

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
            RecyclerView mRecyclerView;
            RecyclerView.LayoutManager mLayoutManager;

            final int id = getArguments().getInt(ARG_NUM);
            switch (id) {
                case R.id.home:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    break;
                case R.id.about_me:
                    rootView = inflater.inflate(R.layout.fragment_about_me, container, false);
                    break;
                case R.id.recyclerview:
                    rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
                    mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    final MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieData.getMoviesList());
                    myRecyclerViewAdapter.SetOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemLongClick(View v, int position) {
                            movieData.duplicateItem(position);
                            myRecyclerViewAdapter.notifyItemInserted(position+1);
                        }

                        @Override
                        public void onItemClick(View v, int position) {
                            Map<String,?> entry = movieData.getItem(position);
                            Toast.makeText(getActivity(), "Selected: " + (String)entry.get("name"),Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onItemCheckBoxClick(View v, int position) {
                            Map<String,?> entry = movieData.getItem(position);
                            CheckBox checkBox = (CheckBox) v.findViewById(R.id.selection);
                            final HashMap<String,Boolean> itemMap_bool = (HashMap<String,Boolean>) entry;
                            itemMap_bool.put("selection",checkBox.isChecked());
                        }

                    });
                    mRecyclerView.setAdapter(myRecyclerViewAdapter);
                    Button selectAll = (Button) rootView.findViewById(R.id.selectAll);
                    selectAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            for(int i=0; i < myRecyclerViewAdapter.getItemCount(); i++){
                                HashMap<String, Boolean> item =
                                            (HashMap<String, Boolean>) movieData.getItem(i);
                                item.put("selection", true);

                            }
                            myRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    });
                    Button clearAll = (Button) rootView.findViewById(R.id.clearAll);
                    clearAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for(int i=0; i < myRecyclerViewAdapter.getItemCount(); i++){
                                HashMap<String,Boolean> item =
                                        (HashMap<String,Boolean>) movieData.getItem(i);
                                item.put("selection",false);
                            }
                            myRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    });
                    Button delete = (Button) rootView.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for(int i=myRecyclerViewAdapter.getItemCount()-1; i >= 0; i--){
                                HashMap<String,Boolean> item =
                                        (HashMap<String,Boolean>) movieData.getItem(i);
                                if (item.get("selection") == true){
                                    movieData.removeItem(i);
                                    myRecyclerViewAdapter.notifyItemRemoved(i);
                                }

                            }

                        }
                    });
                    break;
                default:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);

            }
            return rootView;
        }
    }
}
