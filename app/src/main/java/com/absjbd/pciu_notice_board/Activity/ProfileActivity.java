package com.absjbd.pciu_notice_board.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.Model.Student;
import com.absjbd.pciu_notice_board.R;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.google.gson.Gson;

public class ProfileActivity extends AppCompatActivity {

    BootstrapLabel studentNameLbl, studentAddressLbl, studentPhoneLbl, studentEmailLbl, studentDepartmentLbl, studentBatchLbl, studentIdLbl,
    studentUniqueIdLbl, studentDateTimeLbl, studentStatusLbl;
    String[] department_code, dept_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_profile);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        initViews();


        SharedPreferences prefs = getApplicationContext().getSharedPreferences("LoginInfo", 0);
        // then you use
        Gson gson = new Gson();
        String studentJson = prefs.getString("studentObject", "");

        if(studentJson == "" && studentJson.length() == 0){
            Toast.makeText(this, "Please try to login again..", Toast.LENGTH_SHORT).show();
        }else{

            Student student = gson.fromJson(studentJson, Student.class);
            setProfileData(student);
        }




    }

    private void setProfileData(Student student) {
        studentNameLbl.setText(student.getStudentName());
        studentAddressLbl.setText(student.getStudentAddress());
        studentPhoneLbl.setText(student.getStudentPhone());
        studentEmailLbl.setText(student.getStudentEmail());
        studentDepartmentLbl.setText(dept_names[deptFullName(student.getDeptCode())]);
        studentBatchLbl.setText(student.getBatchId());
        studentIdLbl.setText(student.getStudentId());
        studentUniqueIdLbl.setText(student.getUniqueId());;
        studentDateTimeLbl.setText(student.getDateTime());
        studentStatusLbl.setText(getActivatedStatus(student.getStatus()));
    }

    private String getActivatedStatus(String status) {
        return status.equals("1")? "Active" : "Deactivated";
    }

    private int deptFullName(String deptCode) {

        for(int i=0; i<department_code.length; i++){
            if(deptCode.equals(department_code[i])){
                return i;
            }
        }
        return 0;
    }

    private void initViews() {
        studentNameLbl = (BootstrapLabel) findViewById(R.id.profile_name);
        studentAddressLbl = (BootstrapLabel) findViewById(R.id.profile_address);
        studentPhoneLbl = (BootstrapLabel) findViewById(R.id.profile_phone);
        studentEmailLbl = (BootstrapLabel) findViewById(R.id.profile_email);
        studentDepartmentLbl = (BootstrapLabel) findViewById(R.id.profile_department);
        studentBatchLbl = (BootstrapLabel) findViewById(R.id.profile_batchId);
        studentIdLbl = (BootstrapLabel) findViewById(R.id.profile_studentId);
        studentUniqueIdLbl = (BootstrapLabel) findViewById(R.id.profile_uniqueId);
        studentDateTimeLbl = (BootstrapLabel) findViewById(R.id.profile_dateTime);
        studentStatusLbl = (BootstrapLabel) findViewById(R.id.profile_status);

        department_code = getResources().getStringArray(R.array.dept_code);
        dept_names = getResources().getStringArray(R.array.dept_name);
    }


    @Override
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
            Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void OnClickEditProfileBtn(View view) {
        Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show();
    }
}
