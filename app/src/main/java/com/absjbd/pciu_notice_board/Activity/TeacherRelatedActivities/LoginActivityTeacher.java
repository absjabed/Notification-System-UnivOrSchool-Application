package com.absjbd.pciu_notice_board.Activity.TeacherRelatedActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.Activity.LoginActivity;
import com.absjbd.pciu_notice_board.Activity.MainActivity;
import com.absjbd.pciu_notice_board.Connectivity.Config_Ref;
import com.absjbd.pciu_notice_board.Interface.ApiInterface;
import com.absjbd.pciu_notice_board.Model.ServerResponse;
import com.absjbd.pciu_notice_board.Model.Student;
import com.absjbd.pciu_notice_board.R;
import com.absjbd.pciu_notice_board.Retrofit.RetrofitApiClient;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.google.gson.Gson;

import java.util.Arrays;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivityTeacher extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    BootstrapEditText studentIdET, passwordET;

    SweetAlertDialog pDialog;
    ApiInterface apiInterface;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login_teacher);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initViews();
    }

    private void initViews() {
        studentIdET = (BootstrapEditText) findViewById(R.id.teacherIdET);
        passwordET = (BootstrapEditText) findViewById(R.id.teacherPasswordET);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);
    }

    public void TeacherForgotPasswordClick(View view) {
        Intent intent = new Intent(LoginActivityTeacher.this, Forgot_Password_Teacher.class);
        startActivity(intent);
    }

    public void TeacherRegisterBtnClick(View view) {
        Intent intent = new Intent(LoginActivityTeacher.this, RegisterActivityTeacher.class);
        startActivity(intent);
    }

    public void TeacherLoginBtnClick(View view) {
        String studentID = studentIdET.getText().toString().toLowerCase(); //Todo: <-- this is mainly teacherID
        String password = passwordET.getText().toString().toLowerCase();

        if (studentID.isEmpty() || studentID.length() == 0 || password.isEmpty() || password.length() == 0) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Error!...")
                    .setContentText("Provide Your ID and Password!")
                    .show();

        } else {

            if (password.length() < 4) {

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("(-_-) ...")
                        .setContentText("Password length must be at least 4")
                        .show();

            } else {
                pDialog.setTitleText("Checking Credentials.....");
                pDialog.setCancelable(false);
                loginPrecess(studentID, password);
                /*Intent i = new Intent(LoginActivityTeacher.this, MainActivityTeacher.class);
                startActivity(i);*/
            }

        }

    }

    private void loginPrecess(String studentId, String password) {
        pDialog.show();
        retrofit = RetrofitApiClient.getClient();

        apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponse> call = apiInterface.studentLogin(
                Config_Ref.LOGIN_OPERATION_TEACHER, //TODO: login operation for teacher
                studentId,
                password
        );

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Student student = resp.getStudent(); // todo: got teacher object
                boolean b = response.isSuccessful();
                //response.
                Log.e("res", b ? "ok" : "not");
                if (student != null) {
                    //Converting teacher object to Gson to be stored in SharedPref.
                    Gson gson = new Gson();
                    String teacherJson = gson.toJson(student); // myObject - instance of MyObject

                    //Shared Preference settings // TODO: teacher object
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginInfo", 0);
                    SharedPreferences.Editor editor;
                    editor = pref.edit();
                    editor.putString("teacherObject", teacherJson); //storing student as string
                    editor.putBoolean("login_t", true);
                    editor.apply();

                    //change activity
                    pDialog.dismiss();
                    Toast.makeText(LoginActivityTeacher.this, " " + resp.getMessage()/*+", Name: "+student.getStudentName()+", address: "+student.getStudentAddress()*/, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivityTeacher.this, MainActivityTeacher.class);
                    // Closing all the Activities
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(LoginActivityTeacher.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.e("res", "error");
                pDialog.dismiss();
                Toast.makeText(LoginActivityTeacher.this, "failed, " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("stack69", Arrays.toString(t.getStackTrace()) + "Message: " + t.getMessage() + ", Local message: " + t.getLocalizedMessage() + ", Cause:" + t.getCause());
            }
        });


    }

    @Override
    public void onBackPressed() {
        /*if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            finish();
            //return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);*/
    }

}
