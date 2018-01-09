package com.absjbd.pciu_notice_board.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abs pc1 on 2017-12-24.
 */

public class Student implements Parcelable{


    private Integer id;

    private String studentName;

    private String studentAddress;

    private String studentPhone;

    private String studentEmail;

    private String deptCode;

    private String batchId;

    private String studentId;

    private String uniqueId;

    private String password;

    private String dateTime;

    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public Student() {
    }

    /**
     *
     * @param id
     * @param studentAddress
     * @param studentPhone
     * @param studentEmail
     * @param status
     * @param dateTime
     * @param studentId
     * @param password
     * @param studentName
     * @param batchId
     * @param deptCode
     * @param uniqueId
     */
    public Student(Integer id, String studentName, String uniqueId, String studentAddress, String studentEmail, String studentPhone, String deptCode, String batchId, String studentId, String password, String dateTime, String status) {
        super();
        this.id = id;
        this.studentName = studentName;
        this.uniqueId = uniqueId;
        this.studentAddress = studentAddress;
        this.studentEmail = studentEmail;
        this.studentPhone = studentPhone;
        this.deptCode = deptCode;
        this.batchId = batchId;
        this.studentId = studentId;
        this.password = password;
        this.dateTime = dateTime;
        this.status = status;
    }

    protected Student(Parcel in) {
        studentName = in.readString();
        studentAddress = in.readString();
        studentPhone = in.readString();
        studentEmail = in.readString();
        deptCode = in.readString();
        batchId = in.readString();
        studentId = in.readString();
        uniqueId = in.readString();
        password = in.readString();
        dateTime = in.readString();
        status = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
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

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(studentName);
        dest.writeString(studentAddress);
        dest.writeString(studentPhone);
        dest.writeString(studentEmail);
        dest.writeString(deptCode);
        dest.writeString(batchId);
        dest.writeString(studentId);
        dest.writeString(uniqueId);
        dest.writeString(password);
        dest.writeString(dateTime);
        dest.writeString(status);
    }
}

