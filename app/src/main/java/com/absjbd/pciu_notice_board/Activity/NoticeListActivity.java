package com.absjbd.pciu_notice_board.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.AdapterPackage.NoticeListAdapter;
import com.absjbd.pciu_notice_board.Interface.ApiInterface;
import com.absjbd.pciu_notice_board.Model.NoticeModel;
import com.absjbd.pciu_notice_board.Model.Student;
import com.absjbd.pciu_notice_board.R;
import com.absjbd.pciu_notice_board.Retrofit.RetrofitApiClient;
import com.google.gson.Gson;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeListActivity extends AppCompatActivity {

    ListView list;
    NoticeListAdapter adapter;
    ArrayList<NoticeModel> notices;
    SharedPreferences prefs;
    boolean isLogin_s;
    boolean isLogin_t;
    Student student;
    Gson gson;
    String studentJson;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_notice_list);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        try {
            prefs = getApplicationContext().getSharedPreferences("LoginInfo", 0);
// then you use
            isLogin_s = prefs.getBoolean("login_s", false);
            isLogin_t = prefs.getBoolean("login_t", false);

            if (isLogin_s) {
                gson = new Gson();
                studentJson = prefs.getString("studentObject", "");
                Student student = gson.fromJson(studentJson, Student.class);
                this.student = student;
            }

            if (isLogin_t) {
                gson = new Gson();
                studentJson = prefs.getString("teacherObject", "");
                Student student = gson.fromJson(studentJson, Student.class);
                this.student = student;
            }

            if (!isLogin_s && !isLogin_t) {

                Toast.makeText(NoticeListActivity.this, "Try to login properly, then try again.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText(NoticeListActivity.this, "There is a problem with Mobile Settings. " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        list = (ListView) findViewById(R.id.notice_list);

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading....");
        pDialog.setCancelable(false);
        pDialog.show();

        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        Call call = apiInterface.getNotices();

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                notices = (ArrayList<NoticeModel>) response.body();

                // To filter enquery replies on studentID.........
                ArrayList<NoticeModel> filtered = new ArrayList<>();
                for (NoticeModel m : notices) {
                    if (m.getNoticeTo().trim().equals("PCIU") ||
                            m.getNoticeTo().toLowerCase().trim().equals(student.getDeptCode().toLowerCase().trim()) ||
                            m.getNoticeTo().toLowerCase().trim().equals(student.getBatchId().toLowerCase().trim()) ||
                            m.getNoticeTo().toLowerCase().trim().equals(student.getStudentId().toLowerCase().trim())) { //  checks if id matches.
                        filtered.add(m);
                    }
                }

                adapter = new NoticeListAdapter(NoticeListActivity.this, filtered);
                list.setAdapter(adapter);

                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(NoticeListActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        /*for (int i = 1; i <= 15; i++) {
            // creating new HashMap
            NoticeModel nm = new NoticeModel();

            nm.setId(""+i);
            nm.setNoticeTitle("Auto generated notice title with multiple lines also "+i);
            nm.setNoticeTo("CSE"+i);
            nm.setNoticeDate(i+"-Sep-0"+i);
            // adding each child node to HashMap key => value

            // adding HashList to ArrayList
            notices.add(nm);
        }*/


       /* list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/



    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_homeBack) {
            Intent intent = new Intent(NoticeListActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }*/
}
