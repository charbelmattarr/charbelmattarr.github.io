package com.example.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.AttendanceSystem.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TableLayout;
        import android.widget.TextView;
import android.widget.Toast;

import com.example.AttendanceSystem.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
        import java.util.List;

public class StudentMeetingActivity extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    ListView listView;
    ListView listView2;
    ListView listView3;

    ArrayAdapter arrayAdapter;
    ArrayAdapter arrayAdapter2;
    ArrayAdapter arrayAdapter3;

    ArrayAdapter arrayAdapter4;
    ArrayAdapter arrayAdapter5;
    ArrayAdapter arrayAdapter6;

    ListView listView4;
    ListView listView5;
    ListView listView6;


    String courseName;

    String courseId;
    String enrollment = "1";

    ArrayList<String> meetingDate = new ArrayList();
    ArrayList<String> meetingSubject = new ArrayList();
    ArrayList<String> attendanceValue = new ArrayList();

    ArrayList<String> meetingFutureDuration = new ArrayList();
    ArrayList<String> meetingFutureSubject = new ArrayList();
    ArrayList<String> meetingFutureDate = new ArrayList();

    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;

    JSONParser jParser1 = new JSONParser();
    JSONArray meetings;

    JSONParser jParser2 = new JSONParser();

    int buttonFutureClicked = 0;


    JSONArray meetings2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_meeting);

        Bundle objet_Bundle = this.getIntent().getExtras();
        courseName = objet_Bundle.getString("courseName");
        courseId = objet_Bundle.getString("courseId");
        TextView textView = findViewById(R.id.TextViewName);
        textView.setText(courseName);

        listView = findViewById(R.id.listview1);
        listView2 = findViewById(R.id.listview2);
         listView3 = findViewById(R.id.listview3);

        new LoadCoursesList().execute();

        listView4 = findViewById(R.id.listview4);
        listView5 = findViewById(R.id.listview5);
        listView6 = findViewById(R.id.listview6);


    }

    public void futureMeetings(View view) {
        if(buttonFutureClicked == 0) {
            new LoadFutureMeeting().execute();
            buttonFutureClicked ++;
        }
    }

    public void AddFieldToAddMeeting(View view) {

    }

    private class LoadCoursesList extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(StudentMeetingActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }



        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("course_id", courseId));
            params.add(new BasicNameValuePair("enrollment_id", enrollment));
            JSONObject json = jParser1.makeHttpRequest("http://10.0.2.2/student/getMeeting.php", "POST", params);
            Log.d("All courses :", json.toString());
            try {
                int success = json.getInt("success");
                if(success == 1){

                //successfully got the query
                meetings = json.getJSONArray("meeting");
                for (int i = 0; i < meetings.length(); i++) {
                    JSONObject c = meetings.getJSONObject(i);
                    String meeting_date = c.getString("meeting_date");
                    meetingDate.add(meeting_date);
                    String meeting_subject = c.getString("meeting_subject");
                    meetingSubject.add(meeting_subject);
                    String attendance_value = c.getString("attendance_value");
                    attendanceValue.add(attendance_value);
                }
                }else {
                    ////NO STUDENTS FOUNDD
                    Toast.makeText(StudentMeetingActivity.this,"no students are enrolled in this class",Toast.LENGTH_LONG).show();
                }

                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

            @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();

            runOnUiThread(new Runnable(){
                public void run(){

                    arrayAdapter = new ArrayAdapter(StudentMeetingActivity.this, android.R.layout.simple_list_item_1,meetingDate);


                    listView.setAdapter(arrayAdapter);


                    arrayAdapter2 = new ArrayAdapter(StudentMeetingActivity.this, android.R.layout.simple_list_item_1,meetingSubject);
                    listView2.setAdapter(arrayAdapter2);



                    arrayAdapter3 = new ArrayAdapter(StudentMeetingActivity.this, android.R.layout.simple_list_item_1,attendanceValue);
                    listView3.setAdapter(arrayAdapter3);

                }

            });



        }
    }

    private class LoadFutureMeeting extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog2 = new ProgressDialog(StudentMeetingActivity.this);
            progressDialog2.setMessage("Please wait...");
            progressDialog2.setIndeterminate(false);
            progressDialog2.setCancelable(false);
            progressDialog2.show();
        }



        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("course_id", courseId));
            params.add(new BasicNameValuePair("enrollment_id", enrollment));
            JSONObject json2 = jParser2.makeHttpRequest("http://10.0.2.2/student/getFutureMeeting.php", "POST", params);
            Log.d("All Future courses :", json2.toString());
            try {
                int success = json2.getInt("success");
                if(success == 1){

                    //successfully got the query
                    meetings2 = json2.getJSONArray("meeting");
                    for (int i = 0; i < meetings2.length(); i++) {
                        JSONObject c2 = meetings2.getJSONObject(i);
                        String meeting_date2 = c2.getString("meeting_date");
                        meetingFutureDate.add(meeting_date2);
                        String meeting_subject2 = c2.getString("meeting_subject");
                        meetingFutureSubject.add(meeting_subject2);
                        String meeting_duration2 = c2.getString("meeting_duration");
                        meetingFutureDuration.add(meeting_duration2);
                    }
                }else {
                    ////NO STUDENTS FOUNDD
                    Toast.makeText(StudentMeetingActivity.this,"no students are enrolled in this class",Toast.LENGTH_LONG).show();
                }

                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog2.dismiss();

            runOnUiThread(new Runnable(){
                public void run(){

                    arrayAdapter4 = new ArrayAdapter(StudentMeetingActivity.this, android.R.layout.simple_list_item_1,meetingFutureDate);


                    listView4.setAdapter(arrayAdapter4);


                    arrayAdapter5 = new ArrayAdapter(StudentMeetingActivity.this, android.R.layout.simple_list_item_1,meetingFutureSubject);
                    listView5.setAdapter(arrayAdapter5);



                    arrayAdapter6 = new ArrayAdapter(StudentMeetingActivity.this, android.R.layout.simple_list_item_1,meetingFutureDuration);
                    listView6.setAdapter(arrayAdapter6);

                }

            });



        }
    }




    // Triggers when LOGIN Button clicked
    public void checkLogin() {

        // Get text from email and passord field
        final String email = courseId;
        final String password = "1";

        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(email,password);

    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(StudentMeetingActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {

            List <NameValuePair> list = new ArrayList<>();
            try {

                // Enter URL address where your php file resides
                url = new URL("http://10.0.2.2/student/getMeeting.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString().trim());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            try {
                loadIntoListView(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            if(result.equalsIgnoreCase("true"))
//            {
//                /* Here launching another activity when login successful. If you persist login state
//                use sharedPreferences of Android. and logout button to clear sharedPreferences.
//                 */
//
//                if(true) {
//                    Intent intent = new Intent(StudentMeetingActivity.this, CourseActivity.class);
//
//                    String name = "string from login page";
//                    intent.putExtra("name", name);
//                    startActivity(intent);
//                }
//                else {
//                    Intent intent = new Intent(StudentMeetingActivity.this, StudentCourseActivity.class);
//
//                    String name = "string from login page";
//                    intent.putExtra("name", name);
//                    startActivity(intent);
//                }
//
//            }else if (result.equalsIgnoreCase("false")){
//
//                // If username and password does not match display a error message
//                Toast.makeText(StudentMeetingActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
//
//            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
//
//                Toast.makeText(StudentMeetingActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
//
//            }
//        }

    }

}

    private void loadIntoListView(String json) throws JSONException {
        ArrayAdapter<String> arrayAdapter;
        JSONArray jsonArray;
        String[] course_name;
        String[] course_id;
        jsonArray = new JSONArray(json);
        course_name = new String[jsonArray.length()];
        course_id = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            course_name[i] = obj.getString("meeting_id");
            course_id[i] = obj.getString("course_id");
        }
        Toast.makeText(StudentMeetingActivity.this, course_name[0], Toast.LENGTH_LONG).show();
        Toast.makeText(StudentMeetingActivity.this, course_name[0], Toast.LENGTH_LONG).show();
        Toast.makeText(StudentMeetingActivity.this, course_name[0], Toast.LENGTH_LONG).show();
        Toast.makeText(StudentMeetingActivity.this, course_name[0], Toast.LENGTH_LONG).show();
    }


}

