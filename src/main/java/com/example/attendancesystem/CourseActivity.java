package com.example.attendancesystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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
import java.util.Calendar;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    //u should get teacher id from another page not this one oke?
    Bundle objet_Bundle;
    public String teacher_username ;

    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog2;
    ListView listview2;

    int successCREATE;
    static int selectedPos;

    String selectedItem;
    String messageCreate;
    JSONParser jParser1 = new JSONParser();
    JSONParser jParser2 = new JSONParser();
    JSONParser jParser3 = new JSONParser();
    JSONParser jParser4 = new JSONParser();
    public String editable="0";
    public Boolean isSelectedItem = false;
    private static final String url_all_courses= "http://10.0.2.2/login/getCourseList.php";
    private static final String url_create_course= "http://10.0.2.2/login/createCourse.php";
    private static final String url_edit_course= "http://10.0.2.2/login/EditCourse.php";
    private static final String url_delete_course= "http://10.0.2.2/login/deleteCourse.php";
    private static final String TAG_SUCCESS= "success";
    private static final String TAG_SUCCESS_CREATE= "successCREATE";
    private static final String TAG_COURSES="course";
    private static final String TAG_COURSE_ID="course_id";
    private static final String TAG_COURSE_SUMMARY="course_summary";
    private static final String TAG_COURSE_DESC="courseName_desc";//COURSENAME
    private static final String TAG_COURSE_EDITABLE="editable";
    private static final String TAG_COURSE_SEM="schedule_desc";
    private static final String TAG_COURSE_YEAR="schedule_year";
    private static final String TAG_COURSE_CHAPNUMBER="course_chapterNumber";
    private static final String TAG_COURSE_PRICE="course_price";
    private static final String TAG_TEACHER_USERNAME="user_username";
    private static final String TAG_MESSAGE="messageCREATE";
    ArrayList<Integer> year = new ArrayList<Integer>();
    ArrayAdapter<String> adapter;
    //ArrayAdapter adapter;

    ///
    Button EditCourse;
    Button deleteCourse;
    Button ViewMeeting;
    Button CreateCourse;
    TextView selectedCourseSum;
    TextView selectedcourse;
    JSONArray courses = null;
    ArrayList<String> coursedesc= new ArrayList<>();
    //String[] coursedesc;
    ArrayList<String> courseid= new ArrayList<>();
    ArrayList<String> coursesummary= new ArrayList<>();
    ArrayList<String> courseName = new ArrayList<>();
    String chosenCourse_id;
    Calendar calendar = Calendar.getInstance();
    Integer currentYear=calendar.get(Calendar.YEAR);
    TableLayout tableLayout;
    ///// to get info from table
    LinearLayout highlightCourse;
    EditText ETcourseName;
    EditText ETcourseSum;
    EditText ETcoursePrice;
    EditText ETchapterNumber;

    RadioButton radioFall;
    RadioButton radioSummer;
    RadioButton radioSpring;

    RadioButton radioCurYear;
    RadioButton radioNextYear;

    String courseNamePicked;
    String courseSumPicked;
    String coursePricePicked;
    String chapterNumberPicked;
    String yearPicked;
    String SemesterPicked;


   EditText etEmail;
   EditText etPassword ;

   String email;
   String password;





    //String[] notes = {"...."};

    //they are in millisecond
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        objet_Bundle = this.getIntent().getExtras();
        teacher_username = objet_Bundle.getString("username");
        ////////BUTTONS
        highlightCourse = (LinearLayout)findViewById(R.id.highlightCourse);
        EditCourse = (Button)findViewById(R.id.Edit);
        deleteCourse = (Button)findViewById(R.id.Delete);
        ViewMeeting = (Button)findViewById(R.id.viewMeeting);
        CreateCourse =(Button)findViewById(R.id.SaveCourse);
        listview2 = (ListView)findViewById(R.id.listview2);
        tableLayout = findViewById(R.id.TableLayout);
        selectedcourse =(TextView)findViewById(R.id.TextViewName);
        selectedCourseSum = (TextView)findViewById(R.id.TextViewSum);
        //chooseCourse = (TextView)findViewById(R.id.courseName);
        etEmail = (EditText) findViewById(R.id.courseName);
        etPassword = (EditText) findViewById(R.id.course_Summary);
        // ETcourseName = (EditText) findViewById(R.id.courseName);
        //ETcourseSum = (EditText) findViewById(R.id.course_Summary);
        ETcoursePrice = (EditText) findViewById(R.id.course_category);
        ETchapterNumber = (EditText) findViewById(R.id.chapterNumber);
        radioFall = (RadioButton) findViewById(R.id.fall);
        radioSpring = (RadioButton) findViewById(R.id.spring);
        radioSummer = (RadioButton) findViewById(R.id.summer);

        EditCourse.setBackgroundColor(Color.GREEN);

        new LoadCoursesList().execute();
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                adapterView.setSelection(i);
                adapterView.setSelected(true);
                adapter.notifyDataSetChanged();
                chosenCourse_id=courseid.get(i);

                isSelectedItem=true;
                selectedcourse.setText(courseid.get(i));
                selectedCourseSum.setText(coursesummary.get(i));
                highlightCourse.setBackgroundColor(0xFFBB86FC);
                // ETcourseName.setText(coursedesc.get(i));
                //ETcourseSum.setText(coursesummary.get(i));
            }
        });

        CreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editable="0";
                AddCourse(view);
            }
        });
        EditCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelectedItem){

                    Toast.makeText(CourseActivity.this,"It's a VIP Feature",Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(CourseActivity.this,"please make sure to select a course before choosing a method",Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteCourse.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if( isSelectedItem){
                    // do something

                    new DeleteCourse().execute();
                }else{
                    Toast.makeText(CourseActivity.this,"please make sure to select a course before choosing a method",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ViewMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(  isSelectedItem ){
                    // do something

                    gotoMeeting();
                }else{
                    Toast.makeText(CourseActivity.this,"please make sure to select a course before choosing a method",Toast.LENGTH_SHORT).show();
                }
            }
        });}

    public  void AddFieldToAddCourse(View view) {
        tableLayout.setVisibility(View.VISIBLE);

    }
    //second version is for edited attributes
    public  void AddFieldToAddCourse2(View view,int pos) {

        tableLayout.setVisibility(View.VISIBLE);
        editable="1";

    }



        // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds



        public String userRole = "student";



        // Triggers when LOGIN Button clicked
        public void checkLogin(View arg0) {

            // Get text from email and passord field
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();




        }

        private class AsyncCreate extends AsyncTask<String, String, String> {
            ProgressDialog pdLoading = new ProgressDialog(com.example.attendancesystem.CourseActivity.this);
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
                try {

                    // Enter URL address where your php file resides
                    url = new URL("http://10.0.2.2/login/saveCourse.php");

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
                            .appendQueryParameter("password", params[1])
                            .appendQueryParameter("user_username", params[2]);
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
                        return (result.toString());

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

                if(result.equalsIgnoreCase("1"))
                {// 1 = create
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                    Toast.makeText(com.example.attendancesystem.CourseActivity.this, "Course Created", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CourseActivity.this, CourseActivity.class);

                    intent.putExtra("username", teacher_username);
                    startActivity(intent);
                }else if(result.equalsIgnoreCase("0")) {
                    Toast.makeText(com.example.attendancesystem.CourseActivity.this, "Course Not Created", Toast.LENGTH_LONG).show();

                }else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                    Toast.makeText(com.example.attendancesystem.CourseActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                }
            }

        }



//    private class Save extends AsyncTask<String,String,String>{
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog2 = new ProgressDialog(CourseActivity.this);
//            progressDialog2.setMessage("saving info...");
//            progressDialog2.setIndeterminate(false);
//            progressDialog2.setCancelable(false);
//            progressDialog2.show();
//
//
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            courseNamePicked = ETcourseName.getText().toString().trim();
//            courseSumPicked = ETcourseSum.getText().toString().trim();
//            coursePricePicked = ETcoursePrice.getText().toString().trim();
//            chapterNumberPicked = ETchapterNumber.getText().toString().trim();
//            if(radioFall.isChecked()){
//                SemesterPicked = "fall";
//            }else  if(radioSummer.isChecked()){
//                SemesterPicked = "summer";
//            }else  if(radioSpring.isChecked()){
//                SemesterPicked = "spring";
//            }
//
//            radioCurYear = (RadioButton) findViewById(R.id.curYear);
//            radioNextYear = (RadioButton) findViewById(R.id.nextYear);
//            if(radioCurYear.isChecked()){
//                yearPicked = "2022";
//            }else  if(radioNextYear.isChecked()){
//                yearPicked = "2023";
//            }
//
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            //if(editable.equals("1") ){
//            //  params.add(new BasicNameValuePair(TAG_COURSE_ID,chosenCourse_id));
//            //}
//            //params.add(new BasicNameValuePair(TAG_COURSE_EDITABLE,editable));
//            params.add(new BasicNameValuePair(TAG_COURSE_SUMMARY,courseSumPicked));
//            params.add(new BasicNameValuePair(TAG_COURSE_DESC,courseNamePicked));
//            params.add(new BasicNameValuePair(TAG_COURSE_PRICE,coursePricePicked));
//            params.add(new BasicNameValuePair(TAG_COURSE_CHAPNUMBER,chapterNumberPicked));
//            params.add(new BasicNameValuePair(TAG_COURSE_YEAR,yearPicked));
//            params.add(new BasicNameValuePair(TAG_COURSE_SEM,SemesterPicked));
//            params.add(new BasicNameValuePair(TAG_TEACHER_ID,teacher_id));
//            if(editable.equals("0")){
//                JSONObject jsonObject2 = jParser2.makeHttpRequest(url_create_course,"POST",params);
//                Log.d("create response",jsonObject2.toString());
//                try{
//
//                    int success = jsonObject2.getInt(TAG_SUCCESS_CREATE);
//                    successCREATE = success;
//
//                    messageCreate = jsonObject2.getString(TAG_MESSAGE);
//                    if(successCREATE == 1){
//                        //  selectedcourse.setText("success");
//                        Toast.makeText(CourseActivity.this,"course Created successfully!,refresh to see it here",Toast.LENGTH_LONG).show();
//
//                    }else if(successCREATE == 0){
//                        //  selectedcourse.setText("failure");
//                        Toast.makeText(CourseActivity.this,"message :" + messageCreate,Toast.LENGTH_LONG).show();
//
//                    }
//                    if(messageCreate == null){
//                        Toast.makeText(CourseActivity.this,"message null",Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }}else if(editable.equals("1")){JSONObject jsonObject = jParser3.makeHttpRequest(url_edit_course,"POST",params);
//                Log.d("edit response",jsonObject.toString());
//                try{
//
//                    int success = jsonObject.getInt(TAG_SUCCESS_CREATE);
//                    successCREATE = success;
//
//                    messageCreate = jsonObject.getString(TAG_MESSAGE);
//                    if(successCREATE == 1){
//                        //  selectedcourse.setText("success");
//                        Toast.makeText(CourseActivity.this,"course Created successfully!,refresh to see it here",Toast.LENGTH_LONG).show();
//
//                    }else if(successCREATE == 0){
//                        //  selectedcourse.setText("failure");
//                        Toast.makeText(CourseActivity.this,"message :" + messageCreate,Toast.LENGTH_LONG).show();
//
//                    }
//                    if(messageCreate == null){
//                        Toast.makeText(CourseActivity.this,"message null",Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }}
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            progressDialog2.dismiss();
//
//            if(successCREATE == 1){
//                selectedcourse.setText("success");
//                Toast.makeText(CourseActivity.this,"course Created successfully!,refresh to see it here",Toast.LENGTH_LONG).show();
//
//            }else if(successCREATE == 0){
//                selectedcourse.setText("failure");
//                Toast.makeText(CourseActivity.this,"message :" + messageCreate,Toast.LENGTH_LONG).show();
//
//            }
//        }
//    }


        //ALLOW NETWORK IN MAIN THREAD
        //    getData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void AddCourse(View view) {
        editable="0";
        // Get text from email and passord field
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        // Initialize  AsyncLogin() class with email and password
       new CourseActivity.AsyncCreate().execute(email,password,teacher_username);
        //new Save().execute();
    }

    public void gotoMeeting() {
        Intent intent = new Intent(CourseActivity.this,MeetingActivity.class);

        intent.putExtra("chosenCourseID",chosenCourse_id);
        // MainActivity.this.finish();
        startActivity(intent);

    }

    private class LoadCoursesList extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CourseActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }



        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("teacher_id",teacher_username));
            JSONObject json = jParser1.makeHttpRequest(url_all_courses,"POST",params);
            Log.d("All courses :",json.toString() );
            try {
                int success = json.getInt(TAG_SUCCESS);
                if(success == 1){
                    //successfully got the query
                    courses = json.getJSONArray(TAG_COURSES);
                    for(int i = 0; i<courses.length();i++){
                        JSONObject c = courses.getJSONObject(i);
                        String course_name = c.getString("course_name");
                        courseName.add(course_name);
                        String course_desc = c.getString(TAG_COURSE_DESC);
                        coursedesc.add(course_desc);
                        String course_id = c.getString(TAG_COURSE_ID);
                        courseid.add(course_id);
                        String course_summary = c.getString(TAG_COURSE_SUMMARY);
                        coursesummary.add(course_summary);

                    }
                }else {
                    ////NO STUDENTS FOUNDD
                    Toast.makeText(CourseActivity.this,"no students are enrolled in this class",Toast.LENGTH_LONG).show();
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

                    adapter = new ArrayAdapter<String>(CourseActivity.this, android.R.layout.simple_list_item_single_choice,courseName);
                    listview2.setAdapter(adapter);

                }

            });



        }
    }




    class DeleteCourse extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CourseActivity.this);
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
                params.add(new BasicNameValuePair(TAG_COURSE_ID,chosenCourse_id));
                JSONObject json = jParser4.makeHttpRequest(url_delete_course,"POST",params);
                Log.d("deleteCourse",json.toString());
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
                Toast.makeText(CourseActivity.this,"course DELETED successfully!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CourseActivity.this, CourseActivity.class);
                intent.putExtra("username", teacher_username);
                startActivity(intent);

            }else if(success == 0){
                Toast.makeText(CourseActivity.this,"course NOT Deleted successfully!,refresh to see it here",Toast.LENGTH_LONG).show();
            }}
    }





}
