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

import com.absjbd.pciu_notice_board.Activity.NoticeListActivity;
import com.absjbd.pciu_notice_board.Activity.NoticeViewActivity;
import com.absjbd.pciu_notice_board.Model.NoticeModel;
import com.absjbd.pciu_notice_board.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by abs pc1 on 2017-12-09.
 */

public class NoticeListAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    private ArrayList<NoticeModel> data;
    private Typeface customTypefacePlain, customTypefacePlainItalic;

    public NoticeListAdapter(NoticeListActivity Activity, ArrayList<NoticeModel> notices) {
        // TODO Auto-generated constructor stub
        data = notices;
        context=Activity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        customTypefacePlain = Typeface.createFromAsset(context.getAssets(), "fonts/Cambria.ttf");
        // customTypefacePlainBold = Typeface.createFromAsset(context.getAssets(),"fonts/cambriab.ttf");
        //customTypefaceItalicBold = Typeface.createFromAsset(context.getAssets(),"fonts/CAMBRIAZ.TTF");
        customTypefacePlainItalic = Typeface.createFromAsset(context.getAssets(), "fonts/CAMBRIAI.TTF");
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();

        View rowView=convertView;
        if(convertView==null)
            rowView = inflater.inflate(R.layout.notice_item, null);

        holder.tvNumber=(TextView) rowView.findViewById(R.id.notice_number);
        holder.tvTitle=(TextView) rowView.findViewById(R.id.notice_title);
        holder.tvDate=(TextView) rowView.findViewById(R.id.notice_date);
        holder.tvNoticeTo = (TextView) rowView.findViewById(R.id.notice_to);

        //HashMap<String, String> notice = new HashMap<String, String>();
        final NoticeModel notice;
        notice = data.get(position);

        holder.tvNumber.setText(notice.getId());
        holder.tvTitle.setText(notice.getNoticeTitle());
        holder.tvDate.setText(notice.getNoticeDate());
        holder.tvNoticeTo.setText(notice.getNoticeTo());

        holder.tvNumber.setTypeface(customTypefacePlain);
        holder.tvTitle.setTypeface(customTypefacePlain);
        holder.tvDate.setTypeface(customTypefacePlainItalic);
        holder.tvNoticeTo.setTypeface(customTypefacePlainItalic);
        //holder.tv.setTypeface(customTypeface);
        // holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
       // holder.tv.setText(result[position]);
        // holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "item: "+position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, NoticeViewActivity.class);
                intent.putExtra("notice_obj", notice);
                context.startActivity(intent);
                // TODO Auto-generated method stub

            }
        });
        return rowView;
    }

    public class Holder {
        TextView tvNumber, tvTitle, tvDate, tvNoticeTo;
    }

}