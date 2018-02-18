package com.absjbd.pciu_notice_board.Activity.TeacherRelatedActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.AdapterPackage.MainMenuListAdapterTeacher;
import com.absjbd.pciu_notice_board.Model.Student;
import com.absjbd.pciu_notice_board.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

public class MainActivityTeacher extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    TextView userName, designationName, departmentName;
    Student student;
    ListView list;
    String[] department_code, dept_names;
    String[] listStrings = {
            "Profile",
            "Notices",
            "To-Do List",
            "Post Notice",
            "See Enquiries",
            "Logout",
            "About"
    };
    private Typeface /*customTypefacePlainBold, customTypefaceItalicBold,*/ customTypefacePlain/*,customTypefacePlainItalic*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_teacher);

        userName = (TextView) findViewById(R.id.userNameTeacher);
        designationName = (TextView) findViewById(R.id.designationNameTeacher);
        departmentName = (TextView) findViewById(R.id.departmentNameTeacher);
        department_code = getResources().getStringArray(R.array.dept_code);
        dept_names = getResources().getStringArray(R.array.dept_name);
        customTypefacePlain = Typeface.createFromAsset(this.getAssets(), "fonts/Cambria.ttf");

        //TODO: to uncommented while teacher login is ready
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("LoginInfo", 0);
        // then you use
        Gson gson = new Gson();
        String studentJson = prefs.getString("teacherObject", "");

        if (studentJson == "" && studentJson.length() == 0) {
            Toast.makeText(this, "Please try to login again..", Toast.LENGTH_SHORT).show();
        } else {

            student = gson.fromJson(studentJson, Student.class); //todo: this is actually a teacher object
            //FirebaseMessaging.getInstance().subscribeToTopic(student.getBatchId());
            FirebaseMessaging.getInstance().subscribeToTopic(student.getDeptCode());
            //FirebaseMessaging.getInstance().subscribeToTopic(student.getStudentId());
            //setProfileData(student);
        }
        MainMenuListAdapterTeacher adapter = new MainMenuListAdapterTeacher(this, listStrings);

        userName.setText(student.getStudentName().toUpperCase());
        userName.setTypeface(customTypefacePlain);
        designationName.setText(student.getBatchId().toUpperCase() + ", PCIU");
        designationName.setTypeface(customTypefacePlain);
        departmentName.setText(dept_names[deptFullName(student.getDeptCode())]);
        departmentName.setTypeface(customTypefacePlain);

        list = (ListView) findViewById(R.id.ListViewMainTeacher);
        list.setAdapter(adapter);

    }

    private int deptFullName(String deptCode) {

        for (int i = 0; i < department_code.length; i++) {
            if (deptCode.equals(department_code[i])) {
                return i;
            }
        }
        return 0;
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
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);

    }
}
