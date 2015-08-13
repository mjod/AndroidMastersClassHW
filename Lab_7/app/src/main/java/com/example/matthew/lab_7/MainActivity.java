package com.example.matthew.lab_7;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    private Toolbar mToolBar;
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private RelativeLayout mDrawer;
    private DrawerRecyclerViewAdapter mDrawerRecyclerViewAdapter;
    DrawerData drawerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerData = new DrawerData();
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageButton threeBar = (ImageButton) findViewById(R.id.threeBarButton);
        threeBar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mDrawerLayout.openDrawer(findViewById(R.id.drawer));
            }
        });

        mDrawer = (RelativeLayout) findViewById(R.id.drawer);
        mDrawerList = (RecyclerView) findViewById(R.id.drawer_list);
        mDrawerList.setLayoutManager(new LinearLayoutManager(this));
        mDrawerRecyclerViewAdapter = new DrawerRecyclerViewAdapter(this, drawerData.getDataList());
        mDrawerRecyclerViewAdapter.SetOnItemClickListener(new DrawerRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Map<String, ?> entry = drawerData.getItem(position);
                HashMap<String, Boolean> itemMap_bool = (HashMap<String, Boolean>) entry;
                if (entry.get("name").toString().contains("Home") && !itemMap_bool.get("selection")) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, PlaceholderFragment.newInstance(R.layout.fragment_main))
                            .commit();
                    mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
                } else if (entry.get("name").toString().contains("Movie List View") && !itemMap_bool.get("selection")) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, Fragment_RecyclerView.newInstance(0))
                            .commit();
                    mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
                } else if (entry.get("name").toString().contains("Movie Grid View") && !itemMap_bool.get("selection")) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, Fragment_RecyclerView.newInstance(1))
                            .commit();
                    mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
                } else if (entry.get("name").toString().contains("Simple Fragment 1") && !itemMap_bool.get("selection")) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, PlaceholderFragment.newInstance(R.layout.fragment_dummy_one))
                            .commit();
                    mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
                } else if (entry.get("name").toString().contains("Simple Fragment 2") && !itemMap_bool.get("selection")) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, PlaceholderFragment.newInstance(R.layout.fragment_dummy_two))
                            .commit();
                    mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
                } else if (entry.get("name").toString().contains("Simple Fragment 3") && !itemMap_bool.get("selection")) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, PlaceholderFragment.newInstance(R.layout.fragment_dummy_three))
                            .commit();
                    mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
                }
                mDrawerLayout.closeDrawer(findViewById(R.id.drawer));
                drawerData.makeFalse();
                entry = drawerData.getItem(position);
                itemMap_bool = (HashMap<String, Boolean>) entry;
                itemMap_bool.put("selection", true);
                mDrawerRecyclerViewAdapter.notifyDataSetChanged();

            }
        });
        mDrawerList.setAdapter(mDrawerRecyclerViewAdapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolBar, R.string.open, R.string.close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance(R.layout.fragment_main))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.threeBarButtons) {
            mDrawerLayout.openDrawer(findViewById(R.id.drawer));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public static final String ARG_NUM = "fragment_id";
        public static final int REQUEST_DATE = 10;
        public static final int PICK_CONTACT_REQUEST = 5;
        Button aboutMe, dialogDemo, activityDemo, contactDemo;
        private TextView text_view_result;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
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
                case R.layout.fragment_dummy_one:
                    rootView = inflater.inflate(R.layout.fragment_dummy_one, container, false);
                    break;
                case R.layout.fragment_dummy_two:
                    rootView = inflater.inflate(R.layout.fragment_dummy_two, container, false);
                    break;
                case R.layout.fragment_dummy_three:
                    rootView = inflater.inflate(R.layout.fragment_dummy_three, container, false);
                    break;
                default:
                    rootView = inflater.inflate(R.layout.fragment_main, container, false);
                    homePageView(rootView);

            }
            return rootView;
        }

        public void homePageView(View rootView) {
            aboutMe = (Button) rootView.findViewById(R.id.about_me);
            aboutMe.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, PlaceholderFragment.newInstance(R.layout.fragment_about_me))
                            .addToBackStack("about me")
                            .commit();
                }
            });
            dialogDemo = (Button) rootView.findViewById(R.id.dialogDemo);
            dialogDemo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Date date = new Date(System.currentTimeMillis());
                    Fragment_Dialog dialog = Fragment_Dialog.newInstance(date);
                    dialog.setTargetFragment(PlaceholderFragment.this, REQUEST_DATE);
                    dialog.show(getFragmentManager(), "DatePicker Dialog: Get Result");
                }
            });
            activityDemo = (Button) rootView.findViewById(R.id.actDemo);
            activityDemo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Date date = new Date(System.currentTimeMillis());
                    Intent i = new Intent(getActivity(), ActivityDemo.class);
                    i.putExtra(Fragment_Dialog.DATE_ARGS, date);
                    startActivityForResult(i, REQUEST_DATE);
                }
            });
            contactDemo = (Button) rootView.findViewById(R.id.contactDemo);
            contactDemo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    startActivityForResult(intent, PICK_CONTACT_REQUEST);
                }
            });
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            text_view_result = (TextView) getView().findViewById(R.id.result);
            if (resultCode != Activity.RESULT_OK) return;
            if (requestCode == REQUEST_DATE) {
                Date date = (Date) data.getSerializableExtra(Fragment_Dialog.DATE_ARGS);
                String event = (String) data.getSerializableExtra(Fragment_Dialog.EVENT_ARGS);
                String name = (String) data.getSerializableExtra(Fragment_Dialog.NAME_ARGS);
                text_view_result.setText(date.toString() + "\nEvent: " + event +
                        "\nName: " + name);
            }
            if (requestCode == PICK_CONTACT_REQUEST) {
                Uri contactUri = data.getData();

                String[] projection = {
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getActivity().getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(column);
                text_view_result.setText("Name: " + name + "\nTelephone: " +number);

            }
        }
    }
}