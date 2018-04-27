package com.absjbd.pciu_notice_board.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.AdapterPackage.EnquiryReplyAdapter;
import com.absjbd.pciu_notice_board.Interface.ApiInterface;
import com.absjbd.pciu_notice_board.Model.EnqueryReplyModel;
import com.absjbd.pciu_notice_board.R;
import com.absjbd.pciu_notice_board.Retrofit.RetrofitApiClient;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiryReplyActivity extends AppCompatActivity {

    ListView list;
    Intent intent;
    String dept;
    EnquiryReplyAdapter adapter;
    ArrayList<EnqueryReplyModel> enqueryModels;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_enquiry_reply);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        intent = getIntent();
        dept = intent.getStringExtra("studentID"); //student id to filter
        list = (ListView) findViewById(R.id.enquery_reply_list);

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading....");
        pDialog.setCancelable(false);
        pDialog.show();

        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        Call call = apiInterface.getReplies();

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                enqueryModels = (ArrayList<EnqueryReplyModel>) response.body();

                // To filter enquery replies on studentID.........
                ArrayList<EnqueryReplyModel> filtered = new ArrayList<>();
                for (EnqueryReplyModel m : enqueryModels) {
                    if (m.getStudentID().toLowerCase().trim().equals(dept.toLowerCase().trim())) { // checks if id matches.
                        filtered.add(m);
                    }
                }

                adapter = new EnquiryReplyAdapter(EnquiryReplyActivity.this, filtered);
                list.setAdapter(adapter);
                list.setEmptyView(findViewById(R.id.emptyElement));


                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(EnquiryReplyActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

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
            Intent intent = new Intent(EnquiryReplyActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
