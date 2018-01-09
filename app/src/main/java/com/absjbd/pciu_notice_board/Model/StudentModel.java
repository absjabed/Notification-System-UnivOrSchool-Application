package com.absjbd.pciu_notice_board.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abs pc1 on 2017-12-20.
 */

public class StudentModel {

    private Integer id;

    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("unique_id")
    @Expose
    private String uniqueId;
    @SerializedName("student_address")
    @Expose
    private String studentAddress;
    @SerializedName("student_email")
    @Expose
    private String studentEmail;
    @SerializedName("student_phone")
    @Expose
    private String studentPhone;
    @SerializedName("dept_code")
    @Expose
    private String deptCode;
    @SerializedName("batch_id")
    @Expose
    private String batchId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    private String status;
    @SerializedName("password")
    @Expose
    private String password;


    public StudentModel(String deptCode, String studentName,
                   String password, String batchId,
                   String studentPhone, String studentAddress,
                   String studentId, String studentEmail) {
        this.deptCode = deptCode;
        this.studentName = studentName;
        this.password = password;
        this.batchId = batchId;
        this.studentPhone = studentPhone;
        this.studentAddress = studentAddress;
        this.studentId = studentId;
        this.studentEmail = studentEmail;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    @Override
    public String toString() {
        return "Student{" +
                "deptCode='" + deptCode + '\'' +
                ", studentName='" + studentName + '\'' +
                ", password='" + password + '\'' +
                ", batchId='" + batchId + '\'' +
                ", studentPhone='" + studentPhone + '\'' +
                ", studentAddress='" + studentAddress + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentEmail='" + studentEmail + '\'' +
                '}';
    }
}
