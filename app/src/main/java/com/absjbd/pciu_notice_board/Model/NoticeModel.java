package com.absjbd.pciu_notice_board.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoticeModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("notice_ref_no")
    @Expose
    private String noticeRefNo;
    @SerializedName("notice_from")
    @Expose
    private String noticeFrom;
    @SerializedName("notice_to")
    @Expose
    private String noticeTo;
    @SerializedName("notice_date")
    @Expose
    private String noticeDate;
    @SerializedName("notice_title")
    @Expose
    private String noticeTitle;
    @SerializedName("notice_sort_title")
    @Expose
    private String noticeSortTitle;
    @SerializedName("notice_body")
    @Expose
    private String noticeBody;
    @SerializedName("date_time_stmp")
    @Expose
    private String dateTimeStmp;
    @SerializedName("notice_fb_id")
    @Expose
    private String noticeFbId;
    @SerializedName("status")
    @Expose
    private String status;

    protected NoticeModel(Parcel in) {
        id = in.readString();
        noticeRefNo = in.readString();
        noticeFrom = in.readString();
        noticeTo = in.readString();
        noticeDate = in.readString();
        noticeTitle = in.readString();
        noticeSortTitle = in.readString();
        noticeBody = in.readString();
        dateTimeStmp = in.readString();
        noticeFbId = in.readString();
        status = in.readString();
    }

    public static final Creator<NoticeModel> CREATOR = new Creator<NoticeModel>() {
        @Override
        public NoticeModel createFromParcel(Parcel in) {
            return new NoticeModel(in);
        }

        @Override
        public NoticeModel[] newArray(int size) {
            return new NoticeModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeRefNo() {
        return noticeRefNo;
    }

    public void setNoticeRefNo(String noticeRefNo) {
        this.noticeRefNo = noticeRefNo;
    }

    public String getNoticeFrom() {
        return noticeFrom;
    }

    public void setNoticeFrom(String noticeFrom) {
        this.noticeFrom = noticeFrom;
    }

    public String getNoticeTo() {
        return noticeTo;
    }

    public void setNoticeTo(String noticeTo) {
        this.noticeTo = noticeTo;
    }

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeSortTitle() {
        return noticeSortTitle;
    }

    public void setNoticeSortTitle(String noticeSortTitle) {
        this.noticeSortTitle = noticeSortTitle;
    }

    public String getNoticeBody() {
        return noticeBody;
    }

    public void setNoticeBody(String noticeBody) {
        this.noticeBody = noticeBody;
    }

    public String getDateTimeStmp() {
        return dateTimeStmp;
    }

    public void setDateTimeStmp(String dateTimeStmp) {
        this.dateTimeStmp = dateTimeStmp;
    }

    public String getNoticeFbId() {
        return noticeFbId;
    }

    public void setNoticeFbId(String noticeFbId) {
        this.noticeFbId = noticeFbId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(noticeRefNo);
        dest.writeString(noticeFrom);
        dest.writeString(noticeTo);
        dest.writeString(noticeDate);
        dest.writeString(noticeTitle);
        dest.writeString(noticeSortTitle);
        dest.writeString(noticeBody);
        dest.writeString(dateTimeStmp);
        dest.writeString(noticeFbId);
        dest.writeString(status);
    }
}






























/*
package com.absjbd.pciu_notice_board.Model;

*/
/**
 * Created by abs pc1 on 2017-12-09.
 *//*


public class NoticeModel {
    private String id;
    private String ref_no;
    private String dateTime;
    private String notice_to;
    private String notice_from;
    private String notice_title;
    private String notice_body;
    private String notice_sentBy;
    private String notice_status;

    public NoticeModel() {
        id = "0";
        ref_no = "0";
        dateTime = "09-Sep-2017";
        notice_to = "CSE";
        notice_from = "Dean";
        notice_title = "Demo Notice 0";
        notice_body = "this is demo body.";
        notice_sentBy = "Mr. Tomuk";
        notice_status = "1";
    }

    public NoticeModel(String notice_title, String id, String dateTime, String notice_to) {

        this.notice_title = notice_title;
        this.id = id;
        this.dateTime = dateTime;
        this.notice_to = notice_to;
    }

    public NoticeModel(String id, String ref_no,
                       String dateTime, String notice_to,
                       String notice_from, String notice_title,
                       String notice_body, String notice_sentBy,
                       String notice_status) {
        this.id = id;
        this.ref_no = ref_no;
        this.dateTime = dateTime;
        this.notice_to = notice_to;
        this.notice_from = notice_from;
        this.notice_title = notice_title;
        this.notice_body = notice_body;
        this.notice_sentBy = notice_sentBy;
        this.notice_status = notice_status;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNotice_to() {
        return notice_to;
    }

    public void setNotice_to(String notice_to) {
        this.notice_to = notice_to;
    }

    public String getNotice_from() {
        return notice_from;
    }

    public void setNotice_from(String notice_from) {
        this.notice_from = notice_from;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }

    public String getNotice_body() {
        return notice_body;
    }

    public void setNotice_body(String notice_body) {
        this.notice_body = notice_body;
    }

    public String getNotice_sentBy() {
        return notice_sentBy;
    }

    public void setNotice_sentBy(String notice_sentBy) {
        this.notice_sentBy = notice_sentBy;
    }

    public String getNotice_status() {
        return notice_status;
    }

    public void setNotice_status(String notice_status) {
        this.notice_status = notice_status;
    }
}
*/
