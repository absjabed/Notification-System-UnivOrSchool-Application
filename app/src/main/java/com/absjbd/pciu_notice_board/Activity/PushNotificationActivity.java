package com.absjbd.pciu_notice_board.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.absjbd.pciu_notice_board.R;

public class PushNotificationActivity extends AppCompatActivity {
    String title, message,extraString;
    TextView titleTV, bodyTV, extraTV;

    private String TAG = "PushNotificationActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notification);

        titleTV = (TextView) findViewById(R.id.textTitle);
        bodyTV = (TextView) findViewById(R.id.textMessage);
        extraTV = (TextView) findViewById(R.id.textExtra);


        if (getIntent().getExtras() != null) {

            title = getIntent().getExtras().getString("Title");
            message = getIntent().getExtras().getString("Body");
            extraString = getIntent().getExtras().getString("Extra");

        }else{
            title = getIntent().getStringExtra("Title");
            message = getIntent().getStringExtra("Body");
            extraString = getIntent().getStringExtra("Extra");
        }


        titleTV.setText(title);
        bodyTV.setText(message);
        extraTV.setText(extraString);
    }
}
