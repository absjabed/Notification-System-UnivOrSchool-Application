package com.absjbd.pciu_notice_board.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

import com.absjbd.pciu_notice_board.Connectivity.Config_Ref;
import com.absjbd.pciu_notice_board.Fragments.ResetPasswordFragment;
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

public class LoginActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_login);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initViews();
    }

    private void initViews() {
        studentIdET = (BootstrapEditText) findViewById(R.id.studentIdET);
        passwordET = (BootstrapEditText) findViewById(R.id.passwordET);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);
    }

    public void ForgotPasswordClick(View view) {
        //Toast.makeText(this, "Forgot password Working", Toast.LENGTH_SHORT).show();
        //pDialog.show();
        Intent intent = new Intent(LoginActivity.this, Forgot_Password.class);
        startActivity(intent);
        //finish();
    }

    public void RegisterBtnClick(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void LoginBtnClick(View view) {

        String studentID = studentIdET.getText().toString().toLowerCase();
        String password = passwordET.getText().toString().toLowerCase();

        if(studentID.isEmpty() || studentID.length() == 0 || password.isEmpty() || password.length() == 0){
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Provide Student ID and Password!")
                    .show();

        }else{

            if(password.length() < 4){

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Password length must be at least 4")
                        .show();

            }else{
                pDialog.setTitleText("Checking Credentials.....");
                pDialog.setCancelable(false);
                loginPrecess(studentID, password);
            }

        }


    }

    private void loginPrecess(String studentId, String password) {
        pDialog.show();
        retrofit = RetrofitApiClient.getClient();

        apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponse> call = apiInterface.studentLogin(
                Config_Ref.LOGIN_OPERATION,
                studentId,
                password
        );

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Student student = resp.getStudent();

                if(student != null){
                    //Converting student object to Gson to be stored in SharedPref.
                    Gson gson = new Gson();
                    String studentJson = gson.toJson(student); // myObject - instance of MyObject

                    //Shared Preference settings
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginInfo", 0);
                    SharedPreferences.Editor editor;
                    editor = pref.edit();
                    editor.putString("studentObject", studentJson); //storing student as string
                    editor.putBoolean("login_s", true);
                    editor.apply();

                    //change activity
                    pDialog.dismiss();
                    Toast.makeText(LoginActivity.this, " "+resp.getMessage()/*+", Name: "+student.getStudentName()+", address: "+student.getStudentAddress()*/, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    // Closing all the Activities
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    pDialog.dismiss();
                    Toast.makeText(LoginActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(LoginActivity.this, "failed, "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("stack69",Arrays.toString(t.getStackTrace())+"Message: "+t.getMessage()+", Local message: "+t.getLocalizedMessage()+", Cause:"+t.getCause());
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
