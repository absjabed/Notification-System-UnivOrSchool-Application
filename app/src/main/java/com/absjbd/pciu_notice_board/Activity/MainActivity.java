package com.absjbd.pciu_notice_board.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.AdapterPackage.MainMenuListAdapter;
import com.absjbd.pciu_notice_board.Model.Student;
import com.absjbd.pciu_notice_board.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity
       /* implements NavigationView.OnNavigationItemSelectedListener */ {
    boolean doubleBackToExitPressedOnce = false;
    TextView userName, batchName;
    Student student;
    ListView list;
    String[] listStrings = {
            "Profile",
            "Notices",
            "To-Do List",
            "Official Phone No",
            "Enquiry",
            "Enquiry Replies",
            "Logout",
            "About"
    } ;
    private Typeface /*customTypefacePlainBold, customTypefaceItalicBold,*/ customTypefacePlain/*,customTypefacePlainItalic*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.content_main);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("LoginInfo", 0);
        // then you use
        Gson gson = new Gson();
        String studentJson = prefs.getString("studentObject", "");

        if(studentJson == "" && studentJson.length() == 0){
            Toast.makeText(this, "Please try to login again..", Toast.LENGTH_SHORT).show();
        }else{

            student = gson.fromJson(studentJson, Student.class);
            FirebaseMessaging.getInstance().subscribeToTopic(student.getBatchId());
            FirebaseMessaging.getInstance().subscribeToTopic(student.getDeptCode());
            FirebaseMessaging.getInstance().subscribeToTopic(student.getStudentId());
            //setProfileData(student);
        }

        userName = (TextView) findViewById(R.id.userNameS);
        batchName = (TextView) findViewById(R.id.batchNameS);
        customTypefacePlain = Typeface.createFromAsset(this.getAssets(), "fonts/Cambria.ttf");

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        MainMenuListAdapter adapter = new MainMenuListAdapter(this, listStrings);


        userName.setText(student.getStudentName().toUpperCase());
        userName.setTypeface(customTypefacePlain);
        batchName.setText("Batch: " + student.getBatchId().toUpperCase());
        batchName.setTypeface(customTypefacePlain);

        list=(ListView)findViewById(R.id.ListViewMain);
        list.setAdapter(adapter);


        /*new SweetAlertDialog(this)
                .setTitleText("Notice Board!")
                .setContentText("Port City International University!")
                .show();*/
    }




    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            /*super.onBackPressed();
            return;*/
            Intent i = new Intent();
            i.setAction(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            this.startActivity(i);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);

    }
}
