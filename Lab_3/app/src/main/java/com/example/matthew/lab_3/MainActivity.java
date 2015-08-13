package com.example.matthew.lab_3;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.HashMap;


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

        //noinspection SimplifiableIfStatement
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
        MovieData movieDataGrid;
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
            movieData = new MovieData();
            movieDataGrid = new MovieData();
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
                case R.id.home:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    break;
                case R.id.about_me:
                    rootView = inflater.inflate(R.layout.fragment_about_me, container, false);
                    break;
                case R.id.listview:
                    rootView = inflater.inflate(R.layout.fragment_listview, container, false);
                    ListView listView = (ListView) rootView.findViewById(R.id.listView);

                    final MyBaseAdapter myBaseAdapter = new MyBaseAdapter(getActivity(), movieData.getMoviesList());
                    listView.setAdapter(myBaseAdapter);
                    int[] colors = {0, 0xFF0000FF, 0};
                    listView.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
                    listView.setDividerHeight(2);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final HashMap<String,?> itemMap = (HashMap<String,?>) parent.getItemAtPosition(position);
                            CheckBox checkBox = (CheckBox) view.findViewById(R.id.selection);
                            final HashMap<String,Boolean> itemMap_bool = (HashMap<String,Boolean>) itemMap;
                            itemMap_bool.put("selection",!checkBox.isChecked());
                            checkBox.setChecked(!checkBox.isChecked());
                            if (checkBox.isChecked())
                                Toast.makeText(getActivity(), "Selected: " + (String)itemMap.get("name"),Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getActivity(), "Deselected: " + (String)itemMap.get("name"),Toast.LENGTH_SHORT).show();
                            int count = 0;
                            for(int i=0; i < myBaseAdapter.getCount(); i++){
                                HashMap<String,Boolean> item =
                                        (HashMap<String,Boolean>) myBaseAdapter.getItem(i);
                                if ((Boolean)item.get("selection") == true){
                                    count++;
                                }
                            }
                        }
                    });
                    listView.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
                        public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                            myBaseAdapter.duplicateItem(position);
                            myBaseAdapter.notifyDataSetChanged();

                            return true;
                        }
                    });

                    Button selectAll = (Button) rootView.findViewById(R.id.selectAll);
                    selectAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            for(int i=0; i < myBaseAdapter.getCount(); i++){
                                if (myBaseAdapter.isEnabled(i)) {
                                    HashMap<String, Boolean> item =
                                            (HashMap<String, Boolean>) myBaseAdapter.getItem(i);
                                    item.put("selection", true);
                                }
                            }
                            myBaseAdapter.notifyDataSetChanged();
                        }
                    });

                    Button clearAll = (Button) rootView.findViewById(R.id.clearAll);
                    clearAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for(int i=0; i < myBaseAdapter.getCount(); i++){
                                HashMap<String,Boolean> item =
                                        (HashMap<String,Boolean>) myBaseAdapter.getItem(i);
                                item.put("selection",false);
                            }
                            myBaseAdapter.notifyDataSetChanged();
                        }
                    });
                    Button delete = (Button) rootView.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for(int i=myBaseAdapter.getCount()-1; i >= 0; i--){
                                HashMap<String,Boolean> item =
                                        (HashMap<String,Boolean>) myBaseAdapter.getItem(i);
                                if (item.get("selection") == true){
                                    myBaseAdapter.removeItem(i);
                                }
                            }
                            myBaseAdapter.notifyDataSetChanged();

                        }
                    });
                    break;
                case R.id.gridview:
                    rootView = inflater.inflate(R.layout.fragment_gridview, container, false);
                    final GridView gridView = (GridView) rootView.findViewById(R.id.gridView);

                    final MyGridAdapter myGridAdapter = new MyGridAdapter(getActivity(), movieDataGrid.getMoviesList());
                    gridView.setAdapter(myGridAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final HashMap<String,?> itemMap = (HashMap<String,?>) parent.getItemAtPosition(position);
                            Toast.makeText(getActivity(), "Selected: " + (String)itemMap.get("name"),Toast.LENGTH_SHORT).show();
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
