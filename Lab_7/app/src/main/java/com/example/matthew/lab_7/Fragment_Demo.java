package com.example.matthew.lab_7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Created by Matthew on 3/3/2015.
 */
public class Fragment_Demo extends Fragment {
    final static public String DATE_ARGS="date";
    static Date date;
    public Fragment_Demo() {
    }

    public static Fragment_Demo newInstance(Date date1) {
        Fragment_Demo fragment = new Fragment_Demo();
        Bundle args = new Bundle();
        args.putSerializable(DATE_ARGS, date1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        date = (Date) getArguments().getSerializable(DATE_ARGS);
        View rootView = inflater.inflate(R.layout.dialog_date, container, false);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePicker datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
        final EditText event = (EditText) rootView.findViewById(R.id.eventEdit);
        final EditText name = (EditText) rootView.findViewById(R.id.nameEdit);
        Button okay = (Button)rootView.findViewById(R.id.okayButton);
        Button cancel = (Button)rootView.findViewById(R.id.cancelButton);
        datePicker.init(year,month,day,new DatePicker.OnDateChangedListener(){
            public void onDateChanged(DatePicker view, int year, int month, int day){
                date = new GregorianCalendar(year,month,day).getTime();
                getArguments().putSerializable(DATE_ARGS,date);
            }
        });
        okay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent();
                i.putExtra(DATE_ARGS, date);
                i.putExtra(Fragment_Dialog.NAME_ARGS, name.getText().toString());
                i.putExtra(Fragment_Dialog.EVENT_ARGS, event.getText().toString());
                getActivity().setResult(Activity.RESULT_OK,i);
                getActivity().finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent();
                getActivity().setResult(Activity.RESULT_CANCELED,i);
                getActivity().finish();
            }
        });
        return rootView;
    }


    }