package com.absjbd.pciu_notice_board.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.Connectivity.Config_Ref;
import com.absjbd.pciu_notice_board.Interface.ApiInterface;
import com.absjbd.pciu_notice_board.Model.ServerResponse;
import com.absjbd.pciu_notice_board.R;
import com.absjbd.pciu_notice_board.Retrofit.RetrofitApiClient;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Forgot_Password extends AppCompatActivity implements View.OnClickListener {


    SweetAlertDialog pDialog;
    ApiInterface apiInterface;
    Retrofit retrofit;
    private BootstrapButton btn_reset;
    private BootstrapEditText et_email, et_code, et_password;
    private TextView tv_timer;
    private ProgressBar progress;
    private boolean isResetInitiated = false;
    private String email;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        initViews();
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
    }

    private void initViews() {

        btn_reset = (BootstrapButton) findViewById(R.id.btn_reset);
        tv_timer = (TextView) findViewById(R.id.timer);
        et_code = (BootstrapEditText) findViewById(R.id.et_code);
        et_email = (BootstrapEditText) findViewById(R.id.et_email);
        et_password = (BootstrapEditText) findViewById(R.id.et_password);
        et_password.setVisibility(View.GONE);
        et_code.setVisibility(View.GONE);
        tv_timer.setVisibility(View.GONE);
        btn_reset.setOnClickListener(this);
        progress = (ProgressBar) findViewById(R.id.progress);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);

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
            Intent intent = new Intent(Forgot_Password.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_reset:

                if (!isResetInitiated) {

                    email = et_email.getText().toString();
                    if (!email.isEmpty()) {
                        progress.setVisibility(View.VISIBLE);
                        pDialog.setTitleText("Please wait.....");
                        pDialog.setCancelable(false);
                        initiateResetPasswordProcess(email);
                    } else {
                        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Fields are empty !")
                                .show();
                    }
                } else {

                    String code = et_code.getText().toString();
                    String password = et_password.getText().toString();

                    if (!code.isEmpty() && !password.isEmpty()) {
                        pDialog.setTitleText("Please wait.....");
                        pDialog.setCancelable(false);
                        finishResetPasswordProcess(email, code, password);
                    } else {

                        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Fields are empty !")
                                .show();
                    }

                }

                break;
        }
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText("Time remaining : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Toast.makeText(Forgot_Password.this, "Time Out ! Request again to reset password.", Toast.LENGTH_LONG).show();
                goToLogin();
            }
        }.start();
    }

    private void goToLogin() {
        Intent intent = new Intent(Forgot_Password.this, LoginActivity.class);
        startActivity(intent);
        //finish();
    }

    private void finishResetPasswordProcess(String email, String code, String password) {
        pDialog.show();
        retrofit = RetrofitApiClient.getClient();

        apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponse> call = apiInterface.changePassword(
                Config_Ref.RESET_PASSWORD_FINISH,
                email,
                code,
                password
        );

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                Toast.makeText(Forgot_Password.this, resp.getMessage(), Toast.LENGTH_LONG).show();

                if (resp.getResult().equals(Config_Ref.SUCCESS)) {

                    //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(Forgot_Password.this, resp.getMessage(), Toast.LENGTH_LONG).show();
                    countDownTimer.cancel();
                    isResetInitiated = false;
                    pDialog.dismiss();
                    goToLogin();

                } else {

                    //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    pDialog.dismiss();
                    Toast.makeText(Forgot_Password.this, resp.getMessage(), Toast.LENGTH_LONG).show();

                }

                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                pDialog.dismiss();
                progress.setVisibility(View.INVISIBLE);
                Log.d(Config_Ref.TAG, "failed");
                Toast.makeText(Forgot_Password.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                //Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void initiateResetPasswordProcess(String email) {
        pDialog.setTitleText("Reset Password");
        pDialog.setContentText("Initiating password reset process.");
        pDialog.show();
        retrofit = RetrofitApiClient.getClient();

        apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponse> call = apiInterface.forgotPassword(
                Config_Ref.RESET_PASSWORD_INITIATE,
                email
        );
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Toast.makeText(Forgot_Password.this, resp.getMessage(), Toast.LENGTH_LONG).show();
                //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if (resp.getResult().equals(Config_Ref.SUCCESS)) {

                    //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(Forgot_Password.this, resp.getMessage(), Toast.LENGTH_LONG).show();
                    et_email.setVisibility(View.GONE);
                    et_code.setVisibility(View.VISIBLE);
                    et_password.setVisibility(View.VISIBLE);
                    tv_timer.setVisibility(View.VISIBLE);
                    btn_reset.setText("Change Password");
                    btn_reset.setTextColor(Color.WHITE);
                    btn_reset.setBackgroundColor(Color.BLUE);
                    isResetInitiated = true;
                    startCountdownTimer();

                } else {

                    //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(Forgot_Password.this, resp.getMessage(), Toast.LENGTH_LONG).show();

                }
                pDialog.dismiss();
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                pDialog.dismiss();
                progress.setVisibility(View.INVISIBLE);
                Log.e(Config_Ref.TAG, "failedNotice");
                //Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                Toast.makeText(Forgot_Password.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
