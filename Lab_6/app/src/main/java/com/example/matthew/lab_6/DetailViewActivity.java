package com.example.matthew.lab_6;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Toast;

import java.util.HashMap;


public class DetailViewActivity extends ActionBarActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_settings) {
                    Toast.makeText(getApplicationContext(), "Selected: Dummy Settings", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if (menuItem.getItemId() == R.id.front_page_star) {
                    Toast.makeText(getApplicationContext(), "Selected: Dummy Star", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        HashMap<String,?> movie = (HashMap<String,?>) getIntent().getSerializableExtra("movie");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, Fragment_DetailView.newInstance(movie))
                    .commit();
        }


    }
}
