package com.absjbd.pciu_notice_board.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class EditProfileActivity extends AppCompatActivity {

    BootstrapEditText editName, editAddress, editPhone, editEmail;
    SweetAlertDialog pDialog;
    ApiInterface apiInterface;
    Retrofit retrofit;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Gson gson;
    String studentJson;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_feedback);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        initViews();
        setTextFields(student);
    }

    private void initViews() {

        editName = (BootstrapEditText) findViewById(R.id.editStudentNameET);
        editAddress = (BootstrapEditText) findViewById(R.id.editStudentAddressET);
        editPhone = (BootstrapEditText) findViewById(R.id.editStudentPhoneET);
        editEmail = (BootstrapEditText) findViewById(R.id.editStudentEmailET);

        //progress dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Updating Info ...");
        pDialog.setCancelable(false);

        //Shared preference related settings.........
        prefs = getApplicationContext().getSharedPreferences("LoginInfo", 0);
        editor = prefs.edit();
        gson = new Gson();
        studentJson = prefs.getString("studentObject", "");
        student = gson.fromJson(studentJson, Student.class);
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
            Intent intent = new Intent(EditProfileActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickUpdateProfileBtn(View view) {


        if(editName.getText().length() != 0 && editAddress.getText().length() != 0 &&
                editPhone.getText().length() != 0 && editEmail.getText().length() != 0){

            pDialog.show();

            editProfileProcess(
                    editName.getText().toString(),
                    editAddress.getText().toString(),
                    editPhone.getText().toString(),
                    editEmail.getText().toString());

            pDialog.dismiss();

        }else{
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error o.O !")
                    .setContentText("All fields should be filled....")
                    .show();
        }

    }

    private void editProfileProcess(final String studentName, final String studentAddress, final String studentPhone, final String studentEmail) {
        retrofit = RetrofitApiClient.getClient();

        apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponse> call = apiInterface.updateProfile(
                Config_Ref.EDIT_PROFILE_OPERATION,
                student.getStudentId(),
                studentName,
                studentAddress,
                studentPhone,
                studentEmail
        );

       /* student.setStudentName(studentName);
        student.setStudentAddress(studentAddress);
        student.setStudentPhone(studentPhone);
        student.setStudentEmail(studentEmail);

        studentJson = gson.toJson(student);
        editor.putString("studentObject", studentJson); //storing student as string
        editor.apply();

        Toast.makeText(this, "Student Data updated", Toast.LENGTH_SHORT).show();*/

        //uncomment..... below code

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                pDialog.dismiss();
                if (resp != null) {
                    new SweetAlertDialog(EditProfileActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(resp.getResult())
                            .setContentText(resp.getMessage())
                            .show();

                    updateSharedPreference();


                    Toast.makeText(EditProfileActivity.this, "success, Message: "+resp.getMessage()+", result: "+resp.getResult(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditProfileActivity.this, "Null response from server, Message: "+resp.getMessage()+", result: "+resp.getResult(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(EditProfileActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Failed!")
                        .setContentText("Unable to update your profile.")
                        .show();
                Toast.makeText(EditProfileActivity.this, "Localized: "+t.getLocalizedMessage()+"\n Message: "+t.getMessage()+"\nstackTrace: "+t.getStackTrace(), Toast.LENGTH_SHORT).show();
                Log.e("update","Localized: "+t.getLocalizedMessage()+"\n Message: "+t.getMessage()+"\nstackTrace: "+t.getStackTrace());
            }

            private void updateSharedPreference() {
                student.setStudentName(studentName);
                student.setStudentAddress(studentAddress);
                student.setStudentPhone(studentPhone);
                student.setStudentEmail(studentEmail);

                studentJson = gson.toJson(student);
                editor.putString("studentObject", studentJson); //storing student as string
                editor.apply();

            }

        });


        //updating Shared pref..........
        /* Gson gson = new Gson();
                    String studentJson = gson.toJson(student); // myObject - instance of MyObject

                    //Shared Preference settings
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginInfo", 0);
                    SharedPreferences.Editor editor;
                    editor = pref.edit();
                    editor.putString("studentObject", studentJson); //storing student as string
                    editor.putBoolean("login",true);
                    editor.apply();*/
    }


    private void setTextFields(Student student) {
        editName.setText(student.getStudentName());
        editAddress.setText(student.getStudentAddress());
        editPhone.setText(student.getStudentPhone());
        editEmail.setText(student.getStudentEmail());
    }
}
