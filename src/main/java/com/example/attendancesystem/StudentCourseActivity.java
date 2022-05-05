package com.example.attendancesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.AttendanceSystem.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StudentCourseActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    JSONArray jsonArray;
    String[] course_name;
    String[] course_id;
    String itemCheckedName;
    String itemCheckedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course);

        Bundle objet_Bundle = this.getIntent().getExtras();
        String string = objet_Bundle.getString("username");

        TextView textView = findViewById(R.id.TextViewName);
        textView.setText(string);


        listView = findViewById(R.id.listview1);
        getJSON("http://10.0.2.2/student/getStudCourseList.php");

        getItemSelectedFromListView();

    }
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
         jsonArray = new JSONArray(json);
         course_name = new String[jsonArray.length()];
         course_id = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            course_name[i] = obj.getString("course_name");
            course_id[i] = obj.getString("course_id");
        }
         arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, course_name);
        listView.setAdapter(arrayAdapter);
        listView.getSelectedItem();
    }


    public void openMeetingActivity(View view) {


        Intent intent = new Intent(StudentCourseActivity.this,StudentMeetingActivity.class);


        intent.putExtra("courseId", itemCheckedId);
        intent.putExtra("courseName", itemCheckedName);

        startActivity(intent);




    }


    public void AddCourse(View view) {

    }

    public void AddFieldToAddCourse(View view) {
        TableLayout tableLayout = findViewById(R.id.TableLayout);
        tableLayout.setVisibility(view.VISIBLE);

    }

    public void openAttendanceActivity(View view) {
    }

    public void getItemSelectedFromListView(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemCheckedId = course_id[position];
                itemCheckedName = course_name[position];
                Toast.makeText(StudentCourseActivity.this, itemCheckedName, Toast.LENGTH_LONG).show();

            }
        });
    }
}