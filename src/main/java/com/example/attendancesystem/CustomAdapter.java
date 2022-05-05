package com.example.attendancesystem;


import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.AttendanceSystem.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<String> list = new ArrayList<String>();
    // private ArrayList<String>notes = new ArrayList<String>();
    private Context context;
    public CustomAdapter(Context context, ArrayList<String> list) {
        this.list = list;
        //  this.notes=list2;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {

        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.indexOf(list.get(i));
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        View view = convertview;
        if(view == null){
            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customlayout,null);
        }
        TextView students =(TextView)view.findViewById(R.id.studentName);
        students.setText(list.get(i));
        //  EditText note =(EditText) view.findViewById(R.id.note);
        //  note.setText(notes.get(i));
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        return view;
    }
}
