package com.absjbd.pciu_notice_board.Model;

/**
 * Created by abs pc1 on 2018-02-16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnqueryModel implements Parcelable {

    public static final Creator<EnqueryModel> CREATOR = new Creator<EnqueryModel>() {
        @Override
        public EnqueryModel createFromParcel(Parcel in) {
            return new EnqueryModel(in);
        }

        @Override
        public EnqueryModel[] newArray(int size) {
            return new EnqueryModel[size];
        }
    };
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("studentName")
    @Expose
    private String studentName;
    @SerializedName("studentId")
    @Expose
    private String studentId;
    @SerializedName("studentEnqueryTopic")
    @Expose
    private String studentEnqueryTopic;
    @SerializedName("studentEnqueryDescription")
    @Expose
    private String studentEnqueryDescription;
    @SerializedName("studentPhone")
    @Expose
    private String studentPhone;
    @SerializedName("deptCode")
    @Expose
    private String deptCode;
    @SerializedName("date_time_stmp")
    @Expose
    private String dateTimeStmp;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     */
    public EnqueryModel() {
    }

    /**
     * @param id
     * @param studentPhone
     * @param status
     * @param studentId
     * @param dateTimeStmp
     * @param studentName
     * @param studentEnqueryTopic
     * @param deptCode
     * @param studentEnqueryDescription
     */
    public EnqueryModel(String id, String studentName, String studentId, String studentEnqueryTopic, String studentEnqueryDescription, String studentPhone, String deptCode, String dateTimeStmp, String status) {
        super();
        this.id = id;
        this.studentName = studentName;
        this.studentId = studentId;
        this.studentEnqueryTopic = studentEnqueryTopic;
        this.studentEnqueryDescription = studentEnqueryDescription;
        this.studentPhone = studentPhone;
        this.deptCode = deptCode;
        this.dateTimeStmp = dateTimeStmp;
        this.status = status;
    }

    protected EnqueryModel(Parcel in) {
        id = in.readString();
        studentName = in.readString();
        studentId = in.readString();
        studentEnqueryTopic = in.readString();
        studentEnqueryDescription = in.readString();
        studentPhone = in.readString();
        deptCode = in.readString();
        dateTimeStmp = in.readString();
        status = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentEnqueryTopic() {
        return studentEnqueryTopic;
    }

    public void setStudentEnqueryTopic(String studentEnqueryTopic) {
        this.studentEnqueryTopic = studentEnqueryTopic;
    }

    public String getStudentEnqueryDescription() {
        return studentEnqueryDescription;
    }

    public void setStudentEnqueryDescription(String studentEnqueryDescription) {
        this.studentEnqueryDescription = studentEnqueryDescription;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDateTimeStmp() {
        return dateTimeStmp;
    }

    public void setDateTimeStmp(String dateTimeStmp) {
        this.dateTimeStmp = dateTimeStmp;
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
        dest.writeString(studentName);
        dest.writeString(studentId);
        dest.writeString(studentEnqueryTopic);
        dest.writeString(studentEnqueryDescription);
        dest.writeString(studentPhone);
        dest.writeString(deptCode);
        dest.writeString(dateTimeStmp);
        dest.writeString(status);
    }

/*    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("studentName", studentName).append("studentId", studentId).append("studentEnqueryTopic", studentEnqueryTopic).append("studentEnqueryDescription", studentEnqueryDescription).append("studentPhone", studentPhone).append("deptCode", deptCode).append("dateTimeStmp", dateTimeStmp).append("status", status).toString();
    }*/

}