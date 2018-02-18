package com.absjbd.pciu_notice_board.Activity.TeacherRelatedActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.Connectivity.Config_Ref;
import com.absjbd.pciu_notice_board.Interface.ApiInterface;
import com.absjbd.pciu_notice_board.Model.ServerResponse;
import com.absjbd.pciu_notice_board.Model.Student;
import com.absjbd.pciu_notice_board.R;
import com.absjbd.pciu_notice_board.Retrofit.RetrofitApiClient;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;

public class SendNotoficationTActivity extends AppCompatActivity {

    BootstrapDropDown department_namesDD;
    BootstrapEditText batchCodeET, noticeTitleET, fullNoticeET;
    BootstrapButton sendNotificationBtn;
    SweetAlertDialog pDialog;
    String[] department_code, dept_names;
    String batchCode, noticeTitle, fullNotice, code, shortTitle, department = "";
    boolean fcmTag = false;

    Date curDate = new Date();
    String DateToStr = DateFormat.getDateInstance().format(curDate);

    ApiInterface apiInterface;
    Retrofit retrofit;
    SharedPreferences prefs;
    Gson gson;
    Student student;
    String teacherJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_send_notofication_t);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initViews();

        prefs = getApplicationContext().getSharedPreferences("LoginInfo", 0);
        // then you use
        gson = new Gson();
        teacherJson = prefs.getString("teacherObject", "");

        if (teacherJson == "" && teacherJson.length() == 0) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Try to login, properly....")
                    .show();
        } else {
            Student student = gson.fromJson(teacherJson, Student.class);
            this.student = student;
        }
    }

    private void initViews() {
        department_namesDD = (BootstrapDropDown) findViewById(R.id.noticeDepartmentT);
        batchCodeET = (BootstrapEditText) findViewById(R.id.noticeBatchT);
        noticeTitleET = (BootstrapEditText) findViewById(R.id.noticeTitleT);
        fullNoticeET = (BootstrapEditText) findViewById(R.id.noticeFullT);
        sendNotificationBtn = (BootstrapButton) findViewById(R.id.sendNotificationBtn);

        department_code = getResources().getStringArray(R.array.dept_code2);
        dept_names = getResources().getStringArray(R.array.dept_name2);

        //progress dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Sending FCM request \nPlease wait....");
        pDialog.setCancelable(false);

        department_namesDD.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View v, int id) {


                department_namesDD.setText(dept_names[id]);
                department = department_code[id];
                if (department.equals("PCIU")) {
                    batchCodeET.setEnabled(false);
                }
            }
        });
    }

    public void onClickSendNotification(View view) {

        code = batchCodeET.getText().toString().trim().toLowerCase();

        if (code.length() != 0) {
            batchCode = department + "" + code;
        } else {
            batchCode = department;
        }
        noticeTitle = noticeTitleET.getText().toString().toLowerCase();
        fullNotice = fullNoticeET.getText().toString().toLowerCase();

        if (noticeTitle.length() == 0 || fullNotice.length() == 0) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Notice title, body, department required!")
                    .show();
        } else {

            shortTitle = "Click to see full notice!";
            pDialog.show();
            sendFcmNotice(noticeTitle, shortTitle, fullNotice, batchCode);
            saveNoticeToDB(noticeTitle, shortTitle, fullNotice, batchCode);
        }

        Toast.makeText(this, "ALL DONE!", Toast.LENGTH_SHORT).show();

    }

    private void saveNoticeToDB(String noticeTitle, String shortTitle, String fullNotice, String batchCode) {

        /*pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Storing to DB\nPlease wait....");
        pDialog.setCancelable(false);
        pDialog.show();*/

        retrofit = RetrofitApiClient.getClient();

        apiInterface = retrofit.create(ApiInterface.class);
//TODO: server call to save notice to db
        retrofit2.Call<ServerResponse> call = apiInterface.postNotice(
                Config_Ref.NOTICE_INSERT,
                "FCM Notice",
                student.getStudentName(),
                batchCode,
                DateToStr,
                noticeTitle,
                shortTitle,
                fullNotice,
                student.getStudentPhone(),//teacherPhone - optional
                student.getStudentEmail(),  // teacherEmail - optional
                student.getStudentId(), // teacherID - optional
                student.getBatchId() // teacher designation - optional
        );

        call.enqueue(new retrofit2.Callback<ServerResponse>() {
            @Override
            public void onResponse(retrofit2.Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                final ServerResponse resp = response.body();
                pDialog.dismiss();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SendNotoficationTActivity.this, resp.getMessage() + "\nNotice to DB Success :) ->"/*+resp*/, Toast.LENGTH_SHORT).show();
                        sendNotificationBtn.setEnabled(false);
                    }
                });
            }

            @Override
            public void onFailure(retrofit2.Call<ServerResponse> call, Throwable t) {
                pDialog.dismiss();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SendNotoficationTActivity.this, "Notice to DB Failed! :) ->"/*+resp*/, Toast.LENGTH_SHORT).show();
                        sendNotificationBtn.setEnabled(false);
                    }
                });
            }
        });

        /* @Field("operation") String operation,
            @Field("notice_ref_no") String notice_ref_no,
            @Field("notice_from") String notice_from,
            @Field("notice_to") String notice_to,
            @Field("notice_date") String notice_date,
            @Field("notice_title") String notice_title,
            @Field("notice_sort_title") String notice_sort_title,
            @Field("notice_body") String notice_body);*/

    }


    private void sendFcmNotice(final String noticeTitle, final String shortTitle, final String fullNotice, final String batchCode) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        /*new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {*/
        OkHttpClient client = new OkHttpClient();
        JSONObject data = new JSONObject();
        JSONObject notification_data = new JSONObject();

        try {
            data.put("title", noticeTitle);
            data.put("text", shortTitle);
            data.put("extra_information", fullNotice);
            data.put("click_action", "NOTIFICATION");

            notification_data.put("to", "/topics/" + batchCode.trim());
            notification_data.put("data", data);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(SendNotoficationTActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        RequestBody body = RequestBody.create(JSON, notification_data.toString());
        Request request = new Request.Builder()
                .header("Authorization", getString(R.string.SERVER_KEY))
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .build();

        Callback responseCallBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //fcmTag = false;
                //pDialog.dismiss();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SendNotoficationTActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                            /*final String resp = response.toString();*/
                //fcmTag = true;
                //pDialog.dismiss();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SendNotoficationTActivity.this, "Success :) ->"/*+resp*/, Toast.LENGTH_SHORT).show();
                        sendNotificationBtn.setEnabled(false);
                    }
                });
                            /**/


            }
        };
        okhttp3.Call call = client.newCall(request);
        call.enqueue(responseCallBack);
        department_namesDD.setText(null);
        batchCodeET.setText(null);
        noticeTitleET.setText(null);
        fullNoticeET.setText(null);
        department = "";
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
            Intent intent = new Intent(SendNotoficationTActivity.this, MainActivityTeacher.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
