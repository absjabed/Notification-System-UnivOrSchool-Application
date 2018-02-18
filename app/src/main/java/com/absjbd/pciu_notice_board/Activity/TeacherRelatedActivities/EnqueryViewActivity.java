package com.absjbd.pciu_notice_board.Activity.TeacherRelatedActivities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.Model.EnqueryModel;
import com.absjbd.pciu_notice_board.R;

public class EnqueryViewActivity extends AppCompatActivity {

    String[] department_code, dept_names;
    Intent intent;
    TextView enqTopic, enqFull, enqStudentName, enqStudentID, enqStudentPhone, enqStudentDept, enqDateTime;
    private Typeface customTypefacePlainBold,/* customTypefaceItalicBold,*/
            customTypefacePlain/*,customTypefacePlainItalic*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_enquery_view);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        initViews();

        getIntntAndSetViewData();

    }

    private void getIntntAndSetViewData() {
        intent = getIntent();

        if (intent != null) {
            EnqueryModel enqueryModel = intent.getParcelableExtra("enquery_obj"); //todo enquery obj
            enqTopic.setText(enqueryModel.getStudentEnqueryTopic());
            enqTopic.setTypeface(customTypefacePlain);
            enqFull.setText(enqueryModel.getStudentEnqueryDescription());
            enqFull.setTypeface(customTypefacePlain);
            enqStudentName.setText(enqueryModel.getStudentName());
            enqStudentName.setTypeface(customTypefacePlain);
            enqStudentID.setText(enqueryModel.getStudentId());
            enqStudentID.setTypeface(customTypefacePlain);
            enqStudentPhone.setText(enqueryModel.getStudentPhone());
            enqStudentPhone.setTypeface(customTypefacePlain);
            enqStudentDept.setText(dept_names[deptFullName(enqueryModel.getDeptCode())]);
            enqStudentDept.setTypeface(customTypefacePlain);
            enqDateTime.setText(enqueryModel.getDateTimeStmp());
            enqDateTime.setTypeface(customTypefacePlain);
        } else {
            Toast.makeText(this, "Internet Connection problem", Toast.LENGTH_SHORT).show();
            enqStudentID.setText("Failed to get Enquery data.");
            enqTopic.setText("Failed to get Enquery data.");
            enqFull.setText("Failed to get Enquery data.");
            enqStudentName.setText("Failed to get Enquery data.");
            enqStudentDept.setText("Failed to get Enquery data.");
            enqDateTime.setText("Failed to get Enquery data.");
            enqStudentPhone.setText("Failed to get Enquery data.");
        }

    }


    private void initViews() {
        enqTopic = (TextView) findViewById(R.id.enqTopicTV);
        enqFull = (TextView) findViewById(R.id.enqFullTV);
        enqStudentName = (TextView) findViewById(R.id.enqStudentNameTV);
        enqStudentID = (TextView) findViewById(R.id.enqStudentIdTV);
        enqStudentPhone = (TextView) findViewById(R.id.enqStudentPhoneTV);
        enqStudentDept = (TextView) findViewById(R.id.enqDepartmentTV);
        enqDateTime = (TextView) findViewById(R.id.enqDateTimeTV);
        customTypefacePlain = Typeface.createFromAsset(this.getAssets(), "fonts/Cambria.ttf");
        department_code = getResources().getStringArray(R.array.dept_code);
        dept_names = getResources().getStringArray(R.array.dept_name);
    }

    private int deptFullName(String deptCode) {

        for (int i = 0; i < department_code.length; i++) {
            if (deptCode.equals(department_code[i])) {
                return i;
            }
        }
        return 0;
    }
}
