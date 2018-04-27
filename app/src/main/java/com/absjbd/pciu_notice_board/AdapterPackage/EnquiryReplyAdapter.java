package com.absjbd.pciu_notice_board.AdapterPackage;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.absjbd.pciu_notice_board.Model.EnqueryReplyModel;
import com.absjbd.pciu_notice_board.R;

import java.util.ArrayList;

/**
 * Created by abs pc1 on 2018-04-27.
 */

public class EnquiryReplyAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context context;
    private ArrayList<EnqueryReplyModel> data;
    private Typeface customTypefacePlain, customTypefacePlainItalic;


    public EnquiryReplyAdapter(Context context, ArrayList<EnqueryReplyModel> enqueries) {
        this.context = context;
        data = enqueries;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        customTypefacePlain = Typeface.createFromAsset(context.getAssets(), "fonts/Cambria.ttf");
        customTypefacePlainItalic = Typeface.createFromAsset(context.getAssets(), "fonts/CAMBRIAI.TTF");
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
            rowView = inflater.inflate(R.layout.row_item_enquiry_reply, null);

        holder.tvEnqReplyTopic = (TextView) rowView.findViewById(R.id.replyEnqueryTopic);
        holder.tvEnqReplyText = (TextView) rowView.findViewById(R.id.replyEnquiryTxt);
        holder.tvEnqReplyDateTime = (TextView) rowView.findViewById(R.id.replyDateTimeStmp);

        final EnqueryReplyModel enqueryModel;
        enqueryModel = data.get(position);

        holder.tvEnqReplyTopic.setText(enqueryModel.getQueryTxt());
        holder.tvEnqReplyText.setText(enqueryModel.getReplyTxt());
        holder.tvEnqReplyDateTime.setText(enqueryModel.getDatetimeStmp());

        holder.tvEnqReplyTopic.setTypeface(customTypefacePlain);
        holder.tvEnqReplyText.setTypeface(customTypefacePlainItalic);
        holder.tvEnqReplyDateTime.setTypeface(customTypefacePlain);

        return rowView;
    }

    public class Holder {
        TextView tvEnqReplyTopic, tvEnqReplyText, tvEnqReplyDateTime;
    }
}
