package com.absjbd.pciu_notice_board.Activity.TeacherRelatedActivities;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.Activity.RegisterActivity;
import com.absjbd.pciu_notice_board.Connectivity.Config_Ref;
import com.absjbd.pciu_notice_board.Interface.ApiInterface;
import com.absjbd.pciu_notice_board.Model.ServerResponse;
import com.absjbd.pciu_notice_board.R;
import com.absjbd.pciu_notice_board.Retrofit.RetrofitApiClient;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.TypefaceProvider;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivityTeacher extends AppCompatActivity {
    private static final String TAG = "RegisterTeacherActivityREG";
    BootstrapEditText studentNameET, studentAddressET, studentEmailET,
            studentPhoneET, batch_idET, student_idET, newPasswordET, rePasswordET;
    BootstrapDropDown department_namesDD;
    BootstrapButton submitBtn;
    String[] department_code, dept_names;
    String studentName, studentAddress, studentEmail, studentPhone,
            batch_id, student_id, newPassword, rePassword, department = "";
    SweetAlertDialog pDialog;
    ApiInterface apiInterface;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_register_teacher);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initViews();
    }

    private void initViews() {

        studentNameET = (BootstrapEditText) findViewById(R.id.teacherNameET);
        studentAddressET = (BootstrapEditText) findViewById(R.id.teacherAddressET);
        studentEmailET = (BootstrapEditText) findViewById(R.id.teacherEmailET);
        studentPhoneET = (BootstrapEditText) findViewById(R.id.teacherPhoneET);
        batch_idET = (BootstrapEditText) findViewById(R.id.teacherDesignationET);
        student_idET = (BootstrapEditText) findViewById(R.id.teacherIdET);
        newPasswordET = (BootstrapEditText) findViewById(R.id.newPasswordTeacherET);
        rePasswordET = (BootstrapEditText) findViewById(R.id.rePasswordTeacherET);
        department_namesDD = (BootstrapDropDown) findViewById(R.id.teacherDepartmentDDL);
        submitBtn = (BootstrapButton) findViewById(R.id.teacherRegisterBtn);
        department_code = getResources().getStringArray(R.array.dept_code3);
        dept_names = getResources().getStringArray(R.array.dept_name3);


        //progress dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Registering teacher...");
        pDialog.setCancelable(false);


        department_namesDD.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View v, int id) {

                department_namesDD.setText(dept_names[id]);
                department = department_code[id];
            }
        });
    }

    public void onClickTeacherRegisterBtn(View view) {

        studentName = studentNameET.getText().toString().toLowerCase();
        studentAddress = studentAddressET.getText().toString().toLowerCase();
        studentEmail = studentEmailET.getText().toString().toLowerCase();
        studentPhone = studentPhoneET.getText().toString().toLowerCase();
        batch_id = batch_idET.getText().toString().toLowerCase();
        student_id = student_idET.getText().toString().toLowerCase();
        newPassword = newPasswordET.getText().toString().toLowerCase();
        rePassword = rePasswordET.getText().toString().toLowerCase();

        if (!studentName.isEmpty() && !studentEmail.isEmpty() && !studentAddress.isEmpty() && !studentPhone.isEmpty()
                && !batch_id.isEmpty() && !student_id.isEmpty() && !newPassword.isEmpty() && !rePassword.isEmpty()) {

            if (department.length() == 0 && !department_namesDD.isSelected()) {

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Warning !")
                        .setContentText("Select your department !")
                        .show();
            } else {

                if (newPassword.length() == rePassword.length() && rePassword.equals(newPassword)) {


                    if (rePassword.length() < 4) {

                        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Password length must be at least 4")
                                .show();

                    } else {
                        pDialog.show();
                        registerProcess(studentName, studentAddress, studentEmail, studentPhone, department, batch_id, student_id, rePassword);

                    }
                } else {

                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Warning !")
                            .setContentText("Password doesn't match !")
                            .show();
                }
            }

        } else {

            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Warning !")
                    .setContentText("Some fields are empty !")
                    .show();
        }
    }

    private void registerProcess(String studentName, String studentAddress, String studentEmail,
                                 String studentPhone, String department, String batch_id, String student_id, String rePassword) {

        retrofit = RetrofitApiClient.getClient();

        apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponse> call = apiInterface.insertStudent(

                //Passing the values by getting it from editTexts
                Config_Ref.REGISTER_OPERATION_TEACHER,
                studentName, //todo: this serves as teachers Name
                studentAddress, //todo: teachers address
                studentPhone, // todo: teachers phone
                studentEmail, //todo: teachers email
                department.toLowerCase(), //todo: teachers dept.
                batch_id, // todo: teachers designation
                student_id, //todo: teachers ID
                rePassword
        );

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();

                if (response.isSuccessful()) {
                    studentNameET.setText(null);
                    studentAddressET.setText(null);
                    studentEmailET.setText(null);
                    studentPhoneET.setText(null);
                    batch_idET.setText(null);
                    student_idET.setText(null);
                    newPasswordET.setText(null);
                    rePasswordET.setText(null);
                }
                pDialog.dismiss();
                if (resp != null) {
                    new SweetAlertDialog(RegisterActivityTeacher.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(resp.getResult())
                            .setContentText(resp.getMessage())
                            .show();
                }
                Toast.makeText(RegisterActivityTeacher.this, "success, Message: " + resp.getMessage() + ", result: " + resp.getResult(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                pDialog.dismiss();

                new SweetAlertDialog(RegisterActivityTeacher.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Failed!")
                        .setContentText("Unable to register this teacher.")
                        .show();

                studentNameET.setText(null);
                studentAddressET.setText(null);
                studentEmailET.setText(null);
                studentPhoneET.setText(null);
                batch_idET.setText(null);
                student_idET.setText(null);
                newPasswordET.setText(null);
                rePasswordET.setText(null);
                //Todo: temp test ends

                //Toast.makeText(RegisterActivityTeacher.this, "Localized: "+t.getLocalizedMessage()+"\n Message: "+t.getMessage()+"\nstackTrace: "+t.getStackTrace(), Toast.LENGTH_SHORT).show();
                Log.e("register", "Localized: " + t.getLocalizedMessage() + "\n Message: " + t.getMessage() + "\nstackTrace: " + t.getStackTrace());
            }
        });


    }

}
