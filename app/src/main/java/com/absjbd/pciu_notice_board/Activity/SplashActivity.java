package com.absjbd.pciu_notice_board.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);

        FirebaseMessaging.getInstance().subscribeToTopic("PCIU");

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                //frameAnimation.stop();
                try {
                    SharedPreferences prefs = getApplicationContext().getSharedPreferences("LoginInfo", 0);
// then you use
                    boolean isLogin = prefs.getBoolean("login", false);

                    if(isLogin){

                        // Go to the Main Activity
                        Intent i = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(i);

                    }else{

                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                        // Go to the Registration Activity

                    }

                }catch (Exception ex){
                    Toast.makeText(SplashActivity.this, "There is a problem with Mobile Settings.", Toast.LENGTH_SHORT).show();
                }


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
