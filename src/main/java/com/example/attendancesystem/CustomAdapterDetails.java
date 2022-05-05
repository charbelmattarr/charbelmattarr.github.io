package com.example.attendancesystem;

import static android.graphics.Color.parseColor;
import static android.graphics.Color.rgb;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.AttendanceSystem.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class CustomAdapterDetails extends BaseAdapter implements ListAdapter{
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> list2 = new ArrayList<>();
    private final PublishSubject<String> onItemClick = PublishSubject.create();
    public RadioButton items;
    public  Integer positionSelected;
    public  String selectedItem;
    public Boolean isSelectedItem = false;
    TableRow tr;
    TableLayout tble;
    TextView sum;
    // private ArrayList<String>notes = new ArrayList<String>();
    private Context context;
    public CustomAdapterDetails( Context context,ArrayList<String> list,ArrayList<String> list2) {
        this.list = list;
        this.list2 = list2;
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

    public Observable<String> getObservable(){
        return onItemClick;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {

        View view = convertview;
        if(view == null){
            LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.customlayoutdetails,null);

        }
        tr=(TableRow)view.findViewById(R.id.rowData) ;
        items =(RadioButton) view.findViewById(R.id.desc);
        items.setText(list.get(i));
        sum =(TextView)view.findViewById(R.id.summary);
        sum.setText(list2.get(i));





        return view;
    }


    public Boolean findchecked(ListView listview){
        Boolean res = false;
        for(int i = 0;i<getCount(); i++){
            if(items.isChecked()){

                positionSelected=i;
                selectedItem= list.get(i);
                isSelectedItem=true;
                listview.setSelection(i);
                items.setTextColor(0xFFBB86FC);
                tr.setBackgroundColor(0xFFBB86FC);
                res=true;
                i=0;

            }
        }
        return res;
    }
}

