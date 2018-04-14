package com.absjbd.pciu_notice_board.AdapterPackage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.Activity.AboutActivity;
import com.absjbd.pciu_notice_board.Activity.NoticeListActivity;
import com.absjbd.pciu_notice_board.Activity.TeacherRelatedActivities.MainActivityTeacher;
import com.absjbd.pciu_notice_board.Activity.TeacherRelatedActivities.ProfileActivityTeacher;
import com.absjbd.pciu_notice_board.Activity.TeacherRelatedActivities.SeeEnquiriesActivityTeacher;
import com.absjbd.pciu_notice_board.Activity.TeacherRelatedActivities.SendNotoficationTActivity;
import com.absjbd.pciu_notice_board.Activity.TeachersPhoneActivity;
import com.absjbd.pciu_notice_board.Activity.ToDoActivity;
import com.absjbd.pciu_notice_board.Activity.VersionSelectActivity;
import com.absjbd.pciu_notice_board.Connectivity.Config_Ref;
import com.absjbd.pciu_notice_board.Model.Student;
import com.absjbd.pciu_notice_board.R;
import com.absjbd.pciu_notice_board.Sqlite.SqliteHelper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by abs pc1 on 2017-12-07.
 */

public class MainMenuListAdapterTeacher extends BaseAdapter {
    private static LayoutInflater inflater = null;
    SqliteHelper mysqlite;
    private String[] result;
    private Context context;
    private Typeface customTypeface;

    public MainMenuListAdapterTeacher(MainActivityTeacher mainActivity, String[] prgmNameList) {
        // TODO Auto-generated constructor stub
        result = prgmNameList;
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        customTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/Aquino-Demo.ttf");
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.row_item, null);
        holder.tv = (TextView) rowView.findViewById(R.id.list_text);
        holder.tv.setTypeface(customTypeface);
        // holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result[position]);
        // holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (position == Config_Ref._PROFILE_t) {
                    // TODO:------------------------------------------Profile & Profile Edit
                    SharedPreferences prefs = context.getApplicationContext().getSharedPreferences("LoginInfo", 0);

                    String studentJson = prefs.getString("teacherObject", "");
                    if (studentJson == "" && studentJson.length() == 0) {
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Try to login, properly....")
                                .show();
                    } else {
                        Intent intent = new Intent(context, ProfileActivityTeacher.class);
                        context.startActivity(intent);
                    }

                    Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();

                } else if (position == Config_Ref._NOTICE_LIST_t) {
                    SharedPreferences prefs = context.getApplicationContext().getSharedPreferences("LoginInfo", 0);

                    String studentJson = prefs.getString("teacherObject", "");
                    if (studentJson == "" && studentJson.length() == 0) {
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Try to login, properly....")
                                .show();
                    } else {
                        Intent intent = new Intent(context, NoticeListActivity.class);
                        context.startActivity(intent);
                    }

                    Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();

                } else if (position == Config_Ref._PHONE_NUMBERS_t) { //this will become teachers mobile no

                    // TODO: All registred teaches phone numbers will be available here

                    Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, TeachersPhoneActivity.class);
                    context.startActivity(intent);

                } else if (position == Config_Ref._TODO_LIST_t) {

                    Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, ToDoActivity.class);
                    context.startActivity(intent);

                } else if (position == Config_Ref._SEND_NOTICE_t) {

                    //TODO: Send push notification & save it to server.......

                    Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, SendNotoficationTActivity.class);
                    context.startActivity(intent);

                } else if (position == Config_Ref._SEE_ENQUIRIES_t) {
                    SharedPreferences prefs = context.getApplicationContext().getSharedPreferences("LoginInfo", 0);

                    String studentJson = prefs.getString("teacherObject", "");
                    if (studentJson == "" && studentJson.length() == 0) {
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Try to login, properly....")
                                .show();
                    } else {

                        Gson gson = new Gson();
                        Student student = gson.fromJson(studentJson, Student.class);
                        // TODO: See all enquiries......

                        Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, SeeEnquiriesActivityTeacher.class);
                        intent.putExtra("teacherDept", student.getDeptCode().toLowerCase());
                        context.startActivity(intent);
                    }

                } else if (position == Config_Ref._LOGOUT_t) {


                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("Want to Logout?")
                            .setConfirmText("Yes")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    SharedPreferences prefs = context.getSharedPreferences("LoginInfo", 0);
                                    Gson gson = new Gson();
                                    String studentJson = prefs.getString("teacherObject", "");
                                    Student student = gson.fromJson(studentJson, Student.class);

                                    // Unsubscribe from topic
                                    FirebaseMessaging.getInstance().unsubscribeFromTopic(student.getDeptCode());

                                    //remove Local todo database
                                    mysqlite = new SqliteHelper(context.getApplicationContext());
                                    mysqlite.DeleteLocalToDoData();

                                    // remove student from shared preference
                                    SharedPreferences.Editor editor;
                                    editor = prefs.edit();
                                    editor.remove("login_t"); // will delete key
                                    editor.remove("teacherObject");
                                    editor.apply();

                                    Intent i = new Intent(context, VersionSelectActivity.class);
                                    // Closing all the Activities
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    // Add new Flag to start new Activity
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(i);
                                }
                            })
                            .show();

                    Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();

                } else if (position == Config_Ref._ABOUT_t) {

                    Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, AboutActivity.class/*AboutActivity.class*/); // Todo: About activity....
                    context.startActivity(intent);

                }

            }
        });
        return rowView;
    }

    public class Holder {
        TextView tv;
    }

}