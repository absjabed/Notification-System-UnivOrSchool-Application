package com.absjbd.pciu_notice_board.AdapterPackage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.Activity.TeachersPhoneActivity;
import com.absjbd.pciu_notice_board.Model.TeacherModel;
import com.absjbd.pciu_notice_board.R;

import java.util.ArrayList;

/**
 * Created by abs pc1 on 2018-02-14.
 */

public class TeachersDataAdapter extends BaseAdapter {

    private static final int REQUEST_CALL = 1;
    private static LayoutInflater inflater = null;
    Context context;
    String[] department_code, dept_names;
    Intent callIntent;
    private ArrayList<TeacherModel> data;
    private Typeface customTypefacePlainBold, /*customTypefaceItalicBold,*/
            customTypefacePlain, customTypefacePlainItalic;

    public TeachersDataAdapter(TeachersPhoneActivity context, ArrayList<TeacherModel> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        customTypefacePlain = Typeface.createFromAsset(context.getAssets(), "fonts/Cambria.ttf");
        //customTypefacePlainBold = Typeface.createFromAsset(context.getAssets(),"fonts/cambriab.ttf");
        //customTypefaceItalicBold = Typeface.createFromAsset(context.getAssets(),"fonts/CAMBRIAZ.TTF");
        customTypefacePlainItalic = Typeface.createFromAsset(context.getAssets(), "fonts/CAMBRIAI.TTF");
        department_code = context.getResources().getStringArray(R.array.dept_code);
        dept_names = context.getResources().getStringArray(R.array.dept_name);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int deptFullName(String deptCode) {

        for (int i = 0; i < department_code.length; i++) {
            if (deptCode.equals(department_code[i])) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        View rowView = convertView;
        if (convertView == null)
            rowView = inflater.inflate(R.layout.row_item_teacher, null);

        holder.tvTeacherName = (TextView) rowView.findViewById(R.id.teacherName);
        holder.tvTeacherDepartment = (TextView) rowView.findViewById(R.id.teacherDepartment);
        holder.tvTeacherDesignation = (TextView) rowView.findViewById(R.id.teacherDesignation);
        holder.tvTeacherPhone = (TextView) rowView.findViewById(R.id.teacherNumber);

        //HashMap<String, String> notice = new HashMap<String, String>();
        final TeacherModel teacherModel;
        teacherModel = data.get(position);

        holder.tvTeacherName.setText(teacherModel.getTeacherName().toUpperCase());
        holder.tvTeacherDepartment.setText(dept_names[deptFullName(teacherModel.getDeptCode())]);
        holder.tvTeacherDesignation.setText(teacherModel.getDesignation());
        holder.tvTeacherPhone.setText(teacherModel.getTeacherPhone());

        holder.tvTeacherName.setTypeface(customTypefacePlain);
        holder.tvTeacherDepartment.setTypeface(customTypefacePlainItalic);
        holder.tvTeacherDesignation.setTypeface(customTypefacePlainItalic);
        holder.tvTeacherPhone.setTypeface(customTypefacePlain);
        //holder.tv.setTypeface(customTypeface);
        // holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        // holder.tv.setText(result[position]);
        // holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Call teacher
                //Toast.makeText(context, "item: "+position, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder
                        .setMessage("Do you want to make a phone call to " + teacherModel.getTeacherName().toUpperCase() + " ?")
                        .setTitle("Call Teacher")
                        .setCancelable(true)
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int id) {
                                        callIntent = new Intent(Intent.ACTION_CALL);
                                        callIntent.setData(Uri.parse("tel:" + teacherModel.getTeacherPhone()));
                                        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                        } else {
                                            context.startActivity(callIntent);
                                        }
                                    }
                                });

                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //finish();
                    }
                });
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
                /*Intent intent = new Intent(context, NoticeViewActivity.class);
                intent.putExtra("notice_obj", notice);
                context.startActivity(intent);*/
                // TODO Auto-generated method stub

            }
        });
        return rowView;
    }

    public class Holder {
        TextView tvTeacherName, tvTeacherDepartment, tvTeacherDesignation, tvTeacherPhone;
    }
}
