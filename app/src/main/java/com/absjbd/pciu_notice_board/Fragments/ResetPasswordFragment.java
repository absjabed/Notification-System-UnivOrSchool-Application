package com.absjbd.pciu_notice_board.Fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.absjbd.pciu_notice_board.Connectivity.Config_Ref;
import com.absjbd.pciu_notice_board.Interface.ApiInterface;
import com.absjbd.pciu_notice_board.Model.ServerRequest;
import com.absjbd.pciu_notice_board.Model.ServerResponse;
import com.absjbd.pciu_notice_board.R;
import com.absjbd.pciu_notice_board.Retrofit.RetrofitApiClient;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ResetPasswordFragment extends Fragment implements View.OnClickListener {

    SweetAlertDialog pDialog;
    ApiInterface apiInterface;
    Retrofit retrofit;
    private AppCompatButton btn_reset;
    private EditText et_email, et_code, et_password;
    private TextView tv_timer;
    private ProgressBar progress;
    private boolean isResetInitiated = false;
    private String email;
    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        btn_reset = (AppCompatButton) view.findViewById(R.id.btn_reset);
        tv_timer = (TextView) view.findViewById(R.id.timer);
        et_code = (EditText) view.findViewById(R.id.et_code);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_password = (EditText) view.findViewById(R.id.et_password);
        et_password.setVisibility(View.GONE);
        et_code.setVisibility(View.GONE);
        tv_timer.setVisibility(View.GONE);
        btn_reset.setOnClickListener(this);
        progress = (ProgressBar) view.findViewById(R.id.progress);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_reset:

                if (!isResetInitiated) {

                    email = et_email.getText().toString();
                    if (!email.isEmpty()) {
                        progress.setVisibility(View.VISIBLE);
                        initiateResetPasswordProcess(email);
                    } else {

                        Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }
                } else {

                    String code = et_code.getText().toString();
                    String password = et_password.getText().toString();

                    if (!code.isEmpty() && !password.isEmpty()) {

                        finishResetPasswordProcess(email, code, password);
                    } else {

                        Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }

                }

                break;
        }
    }

    private void initiateResetPasswordProcess(String email) {

        retrofit = RetrofitApiClient.getClient();

        apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponse> call = apiInterface.forgotPassword(
                Config_Ref.RESET_PASSWORD_INITIATE, //TODO: login operation for teacher
                email
        );

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if (resp.getResult().equals(Config_Ref.SUCCESS)) {

                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    et_email.setVisibility(View.GONE);
                    et_code.setVisibility(View.VISIBLE);
                    et_password.setVisibility(View.VISIBLE);
                    tv_timer.setVisibility(View.VISIBLE);
                    btn_reset.setText("Change Password");
                    isResetInitiated = true;
                    startCountdownTimer();

                } else {

                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                progress.setVisibility(View.INVISIBLE);
                Log.e(Config_Ref.TAG, "failedNotice");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        /*User user = new User();
        user.setEmail(email);
        ServerRequest request = new ServerRequest();
        request.setOperation(Config_Ref.RESET_PASSWORD_INITIATE);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
        @Override
        public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

        ServerResponse resp = response.body();
        Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

        if(resp.getResult().equals(Constants.SUCCESS)){

        Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
        et_email.setVisibility(View.GONE);
        et_code.setVisibility(View.VISIBLE);
        et_password.setVisibility(View.VISIBLE);
        tv_timer.setVisibility(View.VISIBLE);
        btn_reset.setText("Change Password");
        isResetInitiated = true;
        startCountdownTimer();

        } else {

        Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

        }
        progress.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onFailure(Call<ServerResponse> call, Throwable t) {

        progress.setVisibility(View.INVISIBLE);
        Log.d(Constants.TAG,"failed");
        Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

        }
        });*/
    }

    private void finishResetPasswordProcess(String email, String code, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config_Ref.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface requestInterface = retrofit.create(ApiInterface.class);

       /* User user = new User();
        user.setEmail(email);
        user.setCode(code);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Con_ref.RESET_PASSWORD_FINISH);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
        @Override
        public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

        ServerResponse resp = response.body();
        Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

        if(resp.getResult().equals(Constants.SUCCESS)){

        Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
        countDownTimer.cancel();
        isResetInitiated = false;
        goToLogin();

        } else {

        Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

        }
        progress.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onFailure(Call<ServerResponse> call, Throwable t) {

        progress.setVisibility(View.INVISIBLE);
        Log.d(Constants.TAG,"failed");
        Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

        }
        });*/
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText("Time remaining : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Snackbar.make(getView(), "Time Out ! Request again to reset password.", Snackbar.LENGTH_LONG).show();
                goToLogin();
            }
        }.start();
    }

    private void goToLogin() {

        /*Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();*/
    }
}