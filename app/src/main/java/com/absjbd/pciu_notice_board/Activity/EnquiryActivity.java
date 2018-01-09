package com.absjbd.pciu_notice_board.Activity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.Connectivity.Config_Ref;
import com.absjbd.pciu_notice_board.Interface.ApiInterface;
import com.absjbd.pciu_notice_board.Model.ServerResponse;
import com.absjbd.pciu_notice_board.Model.Student;
import com.absjbd.pciu_notice_board.R;
import com.absjbd.pciu_notice_board.Retrofit.RetrofitApiClient;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EnquiryActivity extends AppCompatActivity {


    BootstrapEditText enqueryDescriptionET, enqueryTopicET;
    Student student;

    SweetAlertDialog pDialog;
    ApiInterface apiInterface;
    Retrofit retrofit;
    SharedPreferences prefs;
    Gson gson;
    String studentJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_enquiry);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();

        prefs = getApplicationContext().getSharedPreferences("LoginInfo", 0);
        // then you use
        gson = new Gson();
        studentJson = prefs.getString("studentObject", "");

        if(studentJson == "" && studentJson.length() == 0){
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Try to login, properly....")
                    .show();
        }else{
            Student student = gson.fromJson(studentJson, Student.class);
            this.student = student;
        }

    }

    private void initViews() {

        enqueryTopicET = (BootstrapEditText) findViewById(R.id.studentEnqTopicET);
        enqueryDescriptionET = (BootstrapEditText) findViewById(R.id.studentEnqDescriptionET);

        //progress dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Submitting your query ...");
        pDialog.setCancelable(false);
    }


    public void onClickSubmitBtn(View view) {

        String topic = enqueryTopicET.getText().toString();
        String description = enqueryDescriptionET.getText().toString();

        if(!topic.isEmpty() && topic.length() != 0 && !description.isEmpty() && description.length() != 0){

            pDialog.show();

            //get data from shared pref.....

            enqueryProcess(student.getStudentName(),topic,description,student.getStudentPhone(),
                    student.getStudentEmail(), student.getDeptCode(),student.getStudentId());
            /*
            *  @Field("operation") String operation,
            @Field("studentName") String studentName,
            @Field("studentEnqueryTopic") String studentEnqueryTopic,
            @Field("studentEnqueryDescription") String studentEnqueryDescription,
            @Field("studentPhone") String studentPhone,
            @Field("deptCode") String deptCode,
            @Field("studentId") String studentId
            * */
            //process request
        }else{
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("you have to provide both info's")
                    .show();
        }

    }

    private void enqueryProcess(String studentName, String studentEnqueryTopic, String studentEnqueryDescription,
                                String studentPhone, String studentEmail, String deptCode, String studentId) {

        retrofit = RetrofitApiClient.getClient();

        apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponse> call = apiInterface.submitEnquiry(
                Config_Ref.SUBMIT_ENQUERY_OPERATION,
                studentName,
                studentEnqueryTopic,
                studentEnqueryDescription,
                studentPhone+" \n "+studentEmail,
                deptCode,
                studentId
        );

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if(response.isSuccessful()){
                    enqueryTopicET.setText(null);
                    enqueryDescriptionET.setText(null);
                }

                pDialog.dismiss();
                if (resp != null) {
                    new SweetAlertDialog(EnquiryActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(resp.getResult())
                            .setContentText(resp.getMessage())
                            .show();
                }
                Toast.makeText(EnquiryActivity.this, "success, Message: "+resp.getMessage()+", result: "+resp.getResult(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                pDialog.dismiss();
                new SweetAlertDialog(EnquiryActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Failed!")
                        .setContentText("Unable to submit your query.")
                        .show();
                Toast.makeText(EnquiryActivity.this, "Localized: "+t.getLocalizedMessage()+"\n Message: "+t.getMessage()+"\nstackTrace: "+t.getStackTrace(), Toast.LENGTH_SHORT).show();
                Log.e("EnquiryActivity","Localized: "+t.getLocalizedMessage()+"\n Message: "+t.getMessage()+"\nstackTrace: "+t.getStackTrace());

            }
        });

    }
}
