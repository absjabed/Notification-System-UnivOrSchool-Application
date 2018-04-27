package com.absjbd.pciu_notice_board.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EnqueryReplyModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("enqueryID")
    @Expose
    private String enqueryID;
    @SerializedName("studentID")
    @Expose
    private String studentID;
    @SerializedName("studentName")
    @Expose
    private String studentName;
    @SerializedName("topic_txt")
    @Expose
    private String topicTxt;
    @SerializedName("query_txt")
    @Expose
    private String queryTxt;
    @SerializedName("reply_txt")
    @Expose
    private String replyTxt;
    @SerializedName("datetime_stmp")
    @Expose
    private String datetimeStmp;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     */
    public EnqueryReplyModel() {
    }

    /**
     * @param id
     * @param status
     * @param enqueryID
     * @param studentID
     * @param studentName
     * @param queryTxt
     * @param replyTxt
     * @param datetimeStmp
     * @param topicTxt
     */
    public EnqueryReplyModel(String id, String enqueryID, String studentID, String studentName, String topicTxt, String queryTxt, String replyTxt, String datetimeStmp, String status) {
        super();
        this.id = id;
        this.enqueryID = enqueryID;
        this.studentID = studentID;
        this.studentName = studentName;
        this.topicTxt = topicTxt;
        this.queryTxt = queryTxt;
        this.replyTxt = replyTxt;
        this.datetimeStmp = datetimeStmp;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnqueryID() {
        return enqueryID;
    }

    public void setEnqueryID(String enqueryID) {
        this.enqueryID = enqueryID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTopicTxt() {
        return topicTxt;
    }

    public void setTopicTxt(String topicTxt) {
        this.topicTxt = topicTxt;
    }

    public String getQueryTxt() {
        return queryTxt;
    }

    public void setQueryTxt(String queryTxt) {
        this.queryTxt = queryTxt;
    }

    public String getReplyTxt() {
        return replyTxt;
    }

    public void setReplyTxt(String replyTxt) {
        this.replyTxt = replyTxt;
    }

    public String getDatetimeStmp() {
        return datetimeStmp;
    }

    public void setDatetimeStmp(String datetimeStmp) {
        this.datetimeStmp = datetimeStmp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
