package com.absjbd.pciu_notice_board.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by abs pc1 on 2017-12-20.
 */

public class ServerRequest {

    private String operation;

    private Student student;

    public ServerRequest(){}

    public ServerRequest(String operation, Student student) {
        this.student = student;
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    /*@Override
    public String toString() {
        return "studentModel{" +
                "operation='" + operation + '\'' +
                ", student=" + studentModel +
                '}';
    }*/
}
