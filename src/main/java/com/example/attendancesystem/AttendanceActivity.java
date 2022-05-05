package com.example.attendancesystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.AttendanceSystem.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
    //Intent i = getIntent();
    //String meeting_id=i.getStringExtra("chosenCourseID");
    String meeting_id = "1";
    private ProgressDialog progressDialog;
    ListView listview;
    JSONParser jParser = new JSONParser();
    private static final String url_all_students= "http://10.0.2.2/login/getStudentList.php";
    private static final String TAG_SUCCESS= "success";
    private static final String TAG_STUDENTS="student";
    private static final String TAG_STUDENTS_ID="user_id";
    private static final String TAG_ATTENDANCE_ID="attendance_id";
    private static final String TAG_MEETING_ID="meeting_id";
    private static final String TAG_STUDENTS_EMAIL="student_email";
    JSONArray students = null;
    ArrayList<String> studentList=new ArrayList<>();
    ArrayList<String> attendanceid=new ArrayList<>();
    ArrayList<String> attendanceValue=new ArrayList<>();
    //String[] notes = {"...."};

    //they are in millisecond
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        studentList = new ArrayList<String>();
        listview = (ListView)findViewById(R.id.listview);
        new LoadStudentList().execute();

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

    private class LoadStudentList extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AttendanceActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }



        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_MEETING_ID,meeting_id));
            JSONObject json = jParser.makeHttpRequest(url_all_students,"POST",params);
            Log.d("All students :",json.toString() );
            try {
                int success = json.getInt(TAG_SUCCESS);
                if(success == 1){
                    //successfully got the query
                    students = json.getJSONArray(TAG_STUDENTS);
                    for(int i = 0; i<students.length();i++){
                        JSONObject c = students.getJSONObject(i);
                        String student_id = c.getString(TAG_STUDENTS_ID);
                        String attendance_id = c.getString(TAG_ATTENDANCE_ID);
                        attendanceid.add(attendance_id);
                        studentList.add(student_id);


                    }
                }else {
                    ////NO STUDENTS FOUNDD
                    Toast.makeText(AttendanceActivity.this,"no students are enrolled in this class",Toast.LENGTH_LONG).show();
                }
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
                    ArrayAdapter<String> adapter = new ArrayAdapter(AttendanceActivity.this,android.R.layout.simple_list_item_multiple_choice,studentList);
                    listview.setAdapter(adapter);



                }
            });



        }
    }
}

    // METHODS THAT DIDNT WORK CON MI
    /*   SECOND METHOD DIDNT WORK
   public class GetUserDataRequest extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            URL  url;
            try {
               url = new URL(apiURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            requestBody = new FormEncodingBuilder().build();
            request = new Request.Builder().url(apiURL).build();

            try{
                 response = client.newCall(request).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

       @Override
       protected void onProgressUpdate(Void... values) {
           super.onProgressUpdate(values);
       }

       @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);


            try {
                if(response != null){
                strJson = response.body().string();
                updateUserData(strJson);
                }else {Toast.makeText(MainActivity.this,"response is empty",Toast.LENGTH_LONG).show();}
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String[] updateUserData(String strJson)  {
        JSONArray parent ;
        try {
            if(strJson!=null){
            parent = new JSONArray(strJson);

            JSONObject child = parent.getJSONObject(1);
            studentList[1] = child.getString("student_id");

            progressDialog.hide();}else {
                Toast.makeText(MainActivity.this,"strjson is empty",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

 return studentList;
    }*/
/*  FIRST METHOD DIDNT WORK
    private void getData(){
        try{
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            inputStream= new BufferedInputStream(conn.getInputStream());


        } catch (IOException e) {
            e.printStackTrace();
        }// READ INPUT STREAM INTO A STRING

        try{
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
         StringBuilder builder = new StringBuilder();
         while((line = bufferedReader.readLine()) != null){
             builder.append(line + "\n");
         }inputStream.close();
          result=builder.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        ////// PARSE JSON DATA
        try{
            JSONArray js = new JSONArray(result);
            JSONObject jsonobject ;
            studentList = new String[js.length()];
            for(int i = 4 ; i<js.length(); i++){
                jsonobject = js.getJSONObject(i);
                studentList[i] = jsonobject.getString("student_id");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
*/
