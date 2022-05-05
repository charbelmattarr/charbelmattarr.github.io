package com.example.attendancesystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.AttendanceSystem.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

import java.util.List;

public class MeetingActivity extends AppCompatActivity {

    //u should get teacher id from another page not this one oke?
    Intent i =getIntent();
    public String course_id;
    int successCREATE;
    String messageCreate;
    private ProgressDialog progressDialog;
    ListView listview3;
    TextView selectedMeeting;

    JSONParser jParser1 = new JSONParser();
    JSONParser jParser2 = new JSONParser();
    JSONParser jParser3 = new JSONParser();
    JSONParser jParser4 = new JSONParser();

    public String editable="0";


    private static final String url_all_meetings= "http://10.0.2.2/login/getMeetingsList.php";
    private static final String url_create_meeting= "http://10.0.2.2/login/createMeeting.php";
    private static final String url_edit_meeting= "http://10.0.2.2/login/EditMeeting.php";
    private static final String url_delete_Meeting= "http://10.0.2.2/login/deleteMeeting.php";
    private static final String TAG_SUCCESS= "success";
    private static final String TAG_SUCCESS_CREATE= "successCREATE";
    private static final String TAG_MEETINGS="meeting";
    private static final String TAG_MEETING_ID="meeting_id";
    private static final String TAG_MEETING_DATE="meeting_date";
    private static final String TAG_MEETING_DURATION="meeting_duration";
    // private static final String TAG_COURSE_EDITABLE="editable";
    private static final String TAG_MEETING_PRESENCE_HOUR="meeting_presenceHour";
    private static final String TAG_MEETING_SUBJECT="meeting_subject";
    private static final String TAG_PLACE_DESC="place_desc";
    private static final String TAG_MESSAGE="message";

    //CustomAdapterDetails adapter;
    ArrayAdapter<String> adapter;

    ///
    Button EditMeeting;
    Button deleteMeeting;
    Button ViewAttendance;
    Button CreateMeeting;
    Button SaveMeeting;
    TextView chooseMeeting;

    JSONArray meetings = null;
    String[] meetingDates;
    ArrayList<String> meetingid= new ArrayList<>();
    String chosenMeeting_id;


    TableLayout tableLayout;
    ///// to get info from table

    EditText ETmeetingDate;
    EditText ETmeetingDuration;
    EditText ETpresenceHour;
    EditText ETmeetingSubject;


    String meetingdateSet;
    String meetingDurationSet;
    String meetingPresenceHourSet;
    String meetingSubjectSet;
    boolean isSelected=false;

    String[] meetingDatess = {"2022/01/01", "2022/01/03", "2022/01/10", "2022/02/01"};


    //String[] notes = {"...."};

    //they are in millisecond
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    public  void AddFieldToAddMeeting(View view) {
        tableLayout.setVisibility(View.VISIBLE);
        editable="0";
    }
    //second version is for edited attributes
    public  void AddFieldToAddMeeting2(View view,int pos) {
        chosenMeeting_id=meetingid.get(pos);
        tableLayout.setVisibility(View.VISIBLE);
        editable="1";

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        ////////BUTTONS
        EditMeeting = (Button)findViewById(R.id.EditMeeting);
        deleteMeeting = (Button)findViewById(R.id.DeleteMeeting);
        ViewAttendance = (Button)findViewById(R.id.viewAttendance);
        CreateMeeting =(Button)findViewById(R.id.SaveMeeting);
        listview3 = (ListView)findViewById(R.id.listview3);
        tableLayout = findViewById(R.id.TableLayout);
        selectedMeeting =(TextView)findViewById(R.id.selectedMeeting);

        chooseMeeting = (TextView)findViewById(R.id.courseName);

        ETmeetingDate = (EditText) findViewById(R.id.meetingdate);
        ETmeetingDuration= (EditText) findViewById(R.id.meeting_duration);
        ETmeetingSubject = (EditText) findViewById(R.id.meetingSubject);
        ETpresenceHour = (EditText) findViewById(R.id.presence_hour);

        adapter = new ArrayAdapter<String>(MeetingActivity.this,android.R.layout.simple_list_item_single_choice,meetingDatess);
        listview3.setAdapter(adapter);
        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isSelected =true;
            }
        });
        //new LoadMeetingsList().execute();
        CreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editable="0";
                AddMeeting(view);
            }
        });
        EditMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listview3.isSelected()){

                    AddFieldToAddMeeting2(view,listview3.getSelectedItemPosition());
                    selectedMeeting.setText("meeting_id:"+chosenMeeting_id );
                }else{
                    Toast.makeText(MeetingActivity.this,"please make sure to select a course before choosing a method",Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteMeeting.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if(  listview3.isSelected()){
                    // do something
                    chosenMeeting_id=meetingid.get(listview3.getSelectedItemPosition());
                    new DeleteCourse().execute();
                }else{
                    Toast.makeText(MeetingActivity.this,"please make sure to select a course before choosing a method",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( isSelected ){
                    // do something

                    gotoAttendance();
                }else{
                    Toast.makeText(MeetingActivity.this,"please make sure to select a meeting before choosing a method",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //ALLOW NETWORK IN MAIN THREAD
        //    getData();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void AddMeeting(View view) {
        editable="0";
        new Save().execute();
    }

    public void gotoAttendance() {
        Intent intent = new Intent(MeetingActivity.this,AttendanceActivity.class);
        //String chosenCourseID= intent.getStringExtra(chosenMeeting_id);
        // MainActivity.this.finish();
        startActivity(intent);
        this.finish();
    }

    private class LoadMeetingsList extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MeetingActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }



        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jParser1.makeHttpRequest(url_all_meetings,"GET",params);
            Log.d("All courses :",json.toString() );
            try {
                int success = json.getInt(TAG_SUCCESS);
                if(success == 1){
                    //successfully got the query
                    meetings = json.getJSONArray(TAG_MEETINGS);
                    for(int i = 0; i<meetings.length();i++){
                        JSONObject c = meetings.getJSONObject(i);
                        String meeting_date = (c.getString(TAG_MEETING_DATE));
                        meetingDates[i]=meeting_date;
                        String meeting_id = c.getString(TAG_MEETING_ID);
                        meetingid.add(meeting_id);


                    }
                }else {
                    ////NO STUDENTS FOUNDD
                    Toast.makeText(MeetingActivity.this,"no meetings are scheduled!",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            json = null;

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();

            runOnUiThread(new Runnable(){
                public void run(){

                    //  adapter = new CustomAdapterDetails(Courses.this,coursedesc,coursesummary);
                    adapter = new ArrayAdapter<String>(MeetingActivity.this,R.layout.activity_meeting,meetingDatess);
                    listview3.setAdapter(adapter);


                }

            });



        }
    }

    private class Save extends AsyncTask<String,String,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MeetingActivity.this);
            progressDialog.setMessage("saving info...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... strings) {

            meetingdateSet = String.valueOf(ETmeetingDate.getText());
            meetingDurationSet = String.valueOf(ETmeetingDuration.getText());
            meetingPresenceHourSet = ETpresenceHour.getText().toString().trim();
            meetingSubjectSet = ETmeetingSubject.getText().toString().trim();


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //if(editable.equals("1") ){
            //  params.add(new BasicNameValuePair(TAG_COURSE_ID,chosenCourse_id));
            //}
            //params.add(new BasicNameValuePair(TAG_COURSE_EDITABLE,editable));
            params.add(new BasicNameValuePair(TAG_MEETING_DATE,meetingdateSet));
            params.add(new BasicNameValuePair(TAG_MEETING_DURATION,meetingDurationSet));
            params.add(new BasicNameValuePair(TAG_MEETING_PRESENCE_HOUR,meetingPresenceHourSet));
            params.add(new BasicNameValuePair(TAG_MEETING_SUBJECT,meetingSubjectSet));
            params.add(new BasicNameValuePair(TAG_MEETING_ID,chosenMeeting_id));

            if(editable.equals("0")){
                JSONObject jsonObject = jParser2.makeHttpRequest(url_create_meeting,"POST",params);
                Log.d("create response",jsonObject.toString());
                try{

                    int success = jsonObject.getInt(TAG_SUCCESS_CREATE);
                    successCREATE = success;

                    messageCreate = jsonObject.getString(TAG_MESSAGE);
                    if(successCREATE == 1){
                        //  selectedcourse.setText("success");
                        Toast.makeText(MeetingActivity.this,"meeting Created successfully!,refresh to see it here",Toast.LENGTH_LONG).show();

                    }else if(successCREATE == 0){
                        //  selectedcourse.setText("failure");
                        Toast.makeText(MeetingActivity.this,"message :" + messageCreate,Toast.LENGTH_LONG).show();

                    }
                    if(messageCreate == null){
                        Toast.makeText(MeetingActivity.this,"message null",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }}else if(editable.equals("1")){JSONObject jsonObject = jParser3.makeHttpRequest(url_edit_meeting,"POST",params);
                Log.d("edit response",jsonObject.toString());
                try{

                    int success = jsonObject.getInt(TAG_SUCCESS_CREATE);
                    successCREATE = success;

                    messageCreate = jsonObject.getString(TAG_MESSAGE);
                    if(successCREATE == 1){
                        //  selectedcourse.setText("success");
                        Toast.makeText(MeetingActivity.this,"meeting Created successfully!,refresh to see it here",Toast.LENGTH_LONG).show();

                    }else if(successCREATE == 0){
                        //  selectedcourse.setText("failure");
                        Toast.makeText(MeetingActivity.this,"message :" + messageCreate,Toast.LENGTH_LONG).show();

                    }
                    if(messageCreate == null){
                        Toast.makeText(MeetingActivity.this,"message null",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }}
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();

            if(successCREATE == 1){
                selectedMeeting.setText("success");
                Toast.makeText(MeetingActivity.this,"course Created successfully!,refresh to see it here",Toast.LENGTH_LONG).show();

            }else if(successCREATE == 0){
                selectedMeeting.setText("failure");
                Toast.makeText(MeetingActivity.this,"message :" + messageCreate,Toast.LENGTH_LONG).show();

            }
        }
    }


    class DeleteCourse extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MeetingActivity.this);
            progressDialog.setMessage("deleting course!");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        int success;

        @Override
        protected String doInBackground(String... strings) {

            try{
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(TAG_MEETING_ID,chosenMeeting_id));
                JSONObject json = jParser4.makeHttpRequest(url_delete_Meeting,"POST",params);
                Log.d("deleteMeeting",json.toString());
                success = json.getInt(TAG_SUCCESS);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(success ==1){
                Toast.makeText(MeetingActivity.this,"course DELETED successfully!,refresh to see it here",Toast.LENGTH_LONG).show();
            }else if(success == 0){
                Toast.makeText(MeetingActivity.this,"course NOT Deleted successfully!,refresh to see it here",Toast.LENGTH_LONG).show();
            }}
    }





}
