package com.example.matthew.lab_7;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.DialogFragment;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Dialog extends DialogFragment {
    Date mDate;
    final static public String DATE_ARGS="date";
    final static public String EVENT_ARGS="event";
    final static public String NAME_ARGS="name";

    public Fragment_Dialog(){
    }
    public static Fragment_Dialog newInstance(Date date) {
        Fragment_Dialog fragment = new Fragment_Dialog();
        Bundle args = new Bundle();
        args.putSerializable(DATE_ARGS, date);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mDate = (Date)getArguments().getSerializable(DATE_ARGS);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date,null);
        Button okay = (Button)v.findViewById(R.id.okayButton);
        Button cancel = (Button)v.findViewById(R.id.cancelButton);
        okay.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        final EditText event = (EditText) v.findViewById(R.id.eventEdit);
        final EditText name = (EditText) v.findViewById(R.id.nameEdit);
        datePicker.init(year,month,day,new DatePicker.OnDateChangedListener(){
            public void onDateChanged(DatePicker view, int year, int month, int day){
                mDate = new GregorianCalendar(year,month,day).getTime();
                getArguments().putSerializable(DATE_ARGS,mDate);
            }
        });
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder (getActivity());
        alertDialogBuilder.setView(v)
                .setTitle("Date Picker")
                .setMessage("Please select a date!")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which){
                                if (getTargetFragment() != null){
                                    Intent i = new Intent();
                                    i.putExtra(DATE_ARGS,mDate);
                                    i.putExtra(EVENT_ARGS,event.getText().toString());
                                    i.putExtra(NAME_ARGS,name.getText().toString());
                                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,i);
                                }
                                else{
                                    Toast.makeText(getActivity(),"no need to return results", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
        return alertDialogBuilder.create();
    }

}