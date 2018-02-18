/**
 * Created by abs pc1 on 2018-02-14.
 */
package com.absjbd.pciu_notice_board.Model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

//-----------------------------------com.absjbd.pciu_notice_board.Model.TeacherModel.java-----------------------------------

public class TeacherModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("teacherName")
    @Expose
    private String teacherName;
    @SerializedName("uniqueId")
    @Expose
    private String uniqueId;
    @SerializedName("teacherAddress")
    @Expose
    private String teacherAddress;
    @SerializedName("teacherEmail")
    @Expose
    private String teacherEmail;
    @SerializedName("teacherPhone")
    @Expose
    private String teacherPhone;
    @SerializedName("deptCode")
    @Expose
    private String deptCode;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("teacherId")
    @Expose
    private String teacherId;
    @SerializedName("dateTime")
    @Expose
    private String dateTime;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     */
    public TeacherModel() {
    }

    /**
     * @param id
     * @param status
     * @param dateTime
     * @param designation
     * @param teacherPhone
     * @param deptCode
     * @param teacherAddress
     * @param teacherName
     * @param teacherId
     * @param teacherEmail
     * @param uniqueId
     */
    public TeacherModel(String id, String teacherName, String uniqueId, String teacherAddress, String teacherEmail, String teacherPhone, String deptCode, String designation, String teacherId, String dateTime, String status) {
        super();
        this.id = id;
        this.teacherName = teacherName;
        this.uniqueId = uniqueId;
        this.teacherAddress = teacherAddress;
        this.teacherEmail = teacherEmail;
        this.teacherPhone = teacherPhone;
        this.deptCode = deptCode;
        this.designation = designation;
        this.teacherId = teacherId;
        this.dateTime = dateTime;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getTeacherAddress() {
        return teacherAddress;
    }

    public void setTeacherAddress(String teacherAddress) {
        this.teacherAddress = teacherAddress;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getTeacherPhone() {
        return teacherPhone;
    }

    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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

    @Override
    public String toString() {
        return new StringBuilder("id: " + id + "\n" + "teacherName: " + teacherName + "\n" + "uniqueId: " + uniqueId + "\n" + "teacherAddress: " + teacherAddress + "\n" + "teacherEmail: " + teacherEmail + "\n" + "teacherPhone: " + teacherPhone + "\n" + "deptCode: " + deptCode + "\n" + "designation: " + designation + "\n" + "teacherId: " + teacherId + "\n" + "dateTime: " + dateTime + "\n" + "status: " + status).toString();
    }

}
