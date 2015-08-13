package com.example.matthew.lab_6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if(menu.findItem(R.id.action_search)==null)
            inflater.inflate(R.menu.menu_recycler_view, menu);
        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if(search!=null){
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

                @Override
                public boolean onQueryTextSubmit(String query) {
                    int position = movieData.findItem(query);
                    if(position>=0){
                        mRecyclerView.scrollToPosition(position);
                    }
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String query) {
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
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
                getActivity().startActionMode(new ActionBarCallBack(position));
            }

            @Override
            public void onItemClick(View v, int position) {
                Map<String,?> entry = (Map<String,?>) movieData.getItem(position);
                Toast.makeText(getActivity(), "Selected: " + entry.get("name"), Toast.LENGTH_SHORT).show();
                if (getActivity().getLocalClassName().contains("ViewPagerActivity")){
                    Intent intent = new Intent(getActivity(), DetailViewActivity.class);
                    intent.putExtra("movie",(HashMap<String, ?>) movieData.getItem(position));
                    startActivity(intent);
                }
                else {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, Fragment_DetailView.newInstance((HashMap<String, ?>) movieData.getItem(position)))
                            .addToBackStack("fragmentMovie")
                            .commit();
                }
            }
            @Override
            public void onItemCheckBoxClick(View v, int position) {
                final int spot = position;
                ImageButton button1 = (ImageButton) v.findViewById(R.id.selection);
                final Map<String,?> entry = (Map<String,?>) movieData.getItem(position);
                PopupMenu popup = new PopupMenu(getActivity(),button1);
                popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getActivity(),entry.get("name") + ": " + item.getTitle() + "d",Toast.LENGTH_SHORT).show();
                        if (item.getItemId() == R.id.duplicate){
                            movieData.duplicateItem(spot);
                            myRecyclerViewAdapter.notifyItemInserted(spot + 1);
                        }
                        else if (item.getItemId() == R.id.delete){
                            movieData.removeItem(spot);
                            myRecyclerViewAdapter.notifyItemRemoved(spot);
                        }
                        return true;
                    }
                });

                popup.show();
            }

        });
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
        return rootView;
    }
   public static class ActionBarCallBack implements ActionMode.Callback {
       int position;

       public  ActionBarCallBack (int position){
           this.position = position;
       }
       @Override
       public boolean onActionItemClicked(ActionMode mode, MenuItem item){
           int id = item.getItemId();
           switch (id) {
               case R.id.delete:
                   movieData.removeItem(position);
                   myRecyclerViewAdapter.notifyItemRemoved(position);
                   mode.finish();
                   break;
               case R.id.duplicate:
                   movieData.duplicateItem(position);
                   myRecyclerViewAdapter.notifyItemInserted(position+1);
                   mode.finish();
                   break;
               default:
                   break;
           }
           return false;
       }
       @Override
       public boolean onCreateActionMode(ActionMode mode, Menu menu) {
           mode.getMenuInflater().inflate(R.menu.menu_popup, menu);
           return true;
       }
       @Override
       public void onDestroyActionMode(ActionMode mode){

       }
       @Override
       public boolean onPrepareActionMode(ActionMode mode, Menu menu){
           HashMap hm = movieData.getItem(position);
           mode.setTitle((String)hm.get("name"));
           return false;
       }
   }
}