package com.absjbd.pciu_notice_board.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.Model.NoticeModel;
import com.absjbd.pciu_notice_board.R;

public class NoticeViewActivity extends AppCompatActivity {

    private Typeface customTypefacePlainBold,/* customTypefaceItalicBold,*/
            customTypefacePlain/*,customTypefacePlainItalic*/;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_notice_view);


        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        TextView noticeView_body = (TextView) findViewById(R.id.noticeView_body);
        TextView noticeView_subject = (TextView) findViewById(R.id.noticeView_subject);
        TextView noticeView_from = (TextView) findViewById(R.id.noticeView_noticeFrom);
        TextView noticeView_to = (TextView) findViewById(R.id.noticeView_noticeTo);
        TextView noticeView_date = (TextView) findViewById(R.id.noticeView_date);
        TextView noticeView_refNo = (TextView) findViewById(R.id.noticeView_refNo);
        customTypefacePlain = Typeface.createFromAsset(this.getAssets(), "fonts/Cambria.ttf");
        //customTypefacePlainBold = Typeface.createFromAsset(this.getAssets(),"fonts/cambriab.ttf");

        Intent intent = getIntent();

        if(intent != null){
            NoticeModel notice = intent.getParcelableExtra("notice_obj");
            noticeView_subject.setText(notice.getNoticeTitle());
            noticeView_subject.setTypeface(customTypefacePlain);
            noticeView_from.setText(notice.getNoticeFrom().toUpperCase());
            noticeView_from.setTypeface(customTypefacePlain);
            noticeView_to.setText(notice.getNoticeTo().toUpperCase());
            noticeView_to.setTypeface(customTypefacePlain);
            noticeView_date.setText(notice.getNoticeDate());
            noticeView_date.setTypeface(customTypefacePlain);
            noticeView_refNo.setText(notice.getNoticeRefNo());
            noticeView_refNo.setTypeface(customTypefacePlain);
            noticeView_body.setText(notice.getNoticeBody());
            noticeView_body.setTypeface(customTypefacePlain);
        }else{
            Toast.makeText(this, "Internet Connection problem", Toast.LENGTH_SHORT).show();
            noticeView_body.setText("Failed to get notice data.");
            noticeView_subject.setText("Failed to get notice data.");
            noticeView_from.setText("Failed to get notice data.");
            noticeView_to.setText("Failed to get notice data.");
            noticeView_date.setText("Failed to get notice data.");
            noticeView_refNo.setText("Failed to get notice data.");
        }



    }


/*    @Override
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
            Intent intent = new Intent(NoticeViewActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }*/

}
