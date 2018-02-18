package com.absjbd.pciu_notice_board.AdapterPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.absjbd.pciu_notice_board.Activity.TeacherRelatedActivities.EnqueryViewActivity;
import com.absjbd.pciu_notice_board.Model.EnqueryModel;
import com.absjbd.pciu_notice_board.R;

import java.util.ArrayList;

/**
 * Created by abs pc1 on 2018-02-16.
 */

public class EnqueryListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    String[] department_code, dept_names;
    private ArrayList<EnqueryModel> data;
    private Typeface customTypefacePlain, customTypefacePlainItalic;

    public EnqueryListAdapter(Context context, ArrayList<EnqueryModel> enqueries) {
        this.context = context;
        data = enqueries;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        customTypefacePlain = Typeface.createFromAsset(context.getAssets(), "fonts/Cambria.ttf");
        customTypefacePlainItalic = Typeface.createFromAsset(context.getAssets(), "fonts/CAMBRIAI.TTF");
        department_code = context.getResources().getStringArray(R.array.dept_code);
        dept_names = context.getResources().getStringArray(R.array.dept_name);
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        View rowView = convertView;
        if (convertView == null)
            rowView = inflater.inflate(R.layout.row_item_enquery, null);

        holder.tvEnqTopic = (TextView) rowView.findViewById(R.id.enqStudentEnqueryTopic);
        holder.tvEnqStudentName = (TextView) rowView.findViewById(R.id.enqStudentName);
        holder.tvEnqStudentDepartment = (TextView) rowView.findViewById(R.id.enqDeptCode);
        holder.tvEnqDateTime = (TextView) rowView.findViewById(R.id.enqDateTimeStmp);

        //HashMap<String, String> notice = new HashMap<String, String>();
        final EnqueryModel enqueryModel;
        enqueryModel = data.get(position);

        holder.tvEnqTopic.setText(enqueryModel.getStudentEnqueryTopic());
        holder.tvEnqStudentName.setText(enqueryModel.getStudentName());
        holder.tvEnqStudentDepartment.setText(dept_names[deptFullName(enqueryModel.getDeptCode())]);
        holder.tvEnqDateTime.setText(enqueryModel.getDateTimeStmp());

        holder.tvEnqTopic.setTypeface(customTypefacePlain);
        holder.tvEnqStudentName.setTypeface(customTypefacePlainItalic);
        holder.tvEnqStudentDepartment.setTypeface(customTypefacePlainItalic);
        holder.tvEnqDateTime.setTypeface(customTypefacePlain);
        //holder.tv.setTypeface(customTypeface);
        // holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        // holder.tv.setText(result[position]);
        // holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "item: "+position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, EnqueryViewActivity.class);
                intent.putExtra("enquery_obj", enqueryModel);
                context.startActivity(intent);
                // TODO Auto-generated method stub

            }
        });
        return rowView;
    }

    public class Holder {
        TextView tvEnqTopic, tvEnqStudentName, tvEnqStudentDepartment, tvEnqDateTime;
    }
}
