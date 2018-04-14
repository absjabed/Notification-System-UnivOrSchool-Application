package com.absjbd.pciu_notice_board.Interface;

import com.absjbd.pciu_notice_board.Model.EnqueryModel;
import com.absjbd.pciu_notice_board.Model.NoticeModel;
import com.absjbd.pciu_notice_board.Model.ServerRequest;
import com.absjbd.pciu_notice_board.Model.ServerResponse;
import com.absjbd.pciu_notice_board.Model.TeacherModel;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by abs pc1 on 2017-12-16.
 */

public interface ApiInterface {

    @GET("/pciu/api/getNoticeData.php")
    Call<ArrayList<NoticeModel>> getNotices();

    @GET("/pciu/api/getTeacherData.php")
    Call<ArrayList<TeacherModel>> getTeachers();

    @GET("/pciu/api/getEnqueryData.php")
    Call<ArrayList<EnqueryModel>> getEnqueries();

    @FormUrlEncoded
    @POST("/pciu/api/credentials/index.php")
    Call<ServerResponse> forgotPassword(
            //forget password request
            @Field("operation") String operation,
            @Field("studentEmail") String studentEmail);

    @FormUrlEncoded
    @POST("/pciu/api/credentials/index.php")
    Call<ServerResponse> changePassword(
            //change password request
            @Field("operation") String operation,
            @Field("studentEmail") String studentEmail,
            @Field("code") String code,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/pciu/api/credentials/index.php")
    Call<ServerResponse> submitEnquiry(

            @Field("operation") String operation,
            @Field("studentName") String studentName,
            @Field("studentEnqueryTopic") String studentEnqueryTopic,
            @Field("studentEnqueryDescription") String studentEnqueryDescription,
            @Field("studentPhone") String studentPhone,
            @Field("deptCode") String deptCode,
            @Field("studentId") String studentId);


    @FormUrlEncoded
    @POST("/pciu/api/credentials/index.php")
    Call<ServerResponse> postNotice(

            /*
                                            $notice_ref_no = $_POST['notice_ref_no'];
                                            $notice_from = $_POST['notice_from'];
                                            $notice_to = $_POST['notice_to'];
                                            $notice_date = $_POST['notice_date'];
                                            $notice_title = $_POST['notice_title'];
                                            $notice_sort_title = $_POST['notice_sort_title'];
                                            $notice_body = $_POST['notice_body'];
                                                    //Todo: complete this....*/
            @Field("operation") String operation,
            @Field("notice_ref_no") String notice_ref_no,
            @Field("notice_from") String notice_from,
            @Field("notice_to") String notice_to,
            @Field("notice_date") String notice_date,
            @Field("notice_title") String notice_title,
            @Field("notice_sort_title") String notice_sort_title,
            @Field("notice_body") String notice_body,
            @Field("studentPhone") String studentPhone, //teacherPhone
            @Field("studentEmail") String studentEmail, //teacherEmail
            @Field("studentId") String studentId, //teacherId
            @Field("batchId") String batchId //designation
    );


    @FormUrlEncoded
    @POST("/pciu/api/credentials/index.php")
    Call<ServerResponse> insertStudent(

            @Field("operation") String operation,
            @Field("studentName") String studentName, //teacherName
            @Field("studentAddress") String studentAddress, //teacherAddress
            @Field("studentPhone") String studentPhone, //teacherPhone
            @Field("studentEmail") String studentEmail, //teacherEmail
            @Field("deptCode") String deptCode, //deptCode
            @Field("batchId") String batchId, //designation
            @Field("studentId") String studentId, //teacherId
            @Field("password") String password);

    //implement login with data sent..... edit php 1st
    @FormUrlEncoded
    @POST("/pciu/api/credentials/index.php")
    Call<ServerResponse> studentLogin(

            @Field("operation") String operation,
            @Field("studentId") String studentId,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("/pciu/api/credentials/index.php")
    Call<ServerResponse> updateProfile(

            @Field("operation") String operation,
            @Field("studentId") String studentId,
            @Field("studentName") String studentName,
            @Field("studentAddress") String studentAddress,
            @Field("studentPhone") String studentPhone,
            @Field("studentEmail") String studentEmail);


    @POST("/pciu/api/credentials/index.php")
    Call<ServerResponse> operation(@Body ServerRequest request);

}

/*//This method is used for "POST"
    @FormUrlEncoded
    @POST("/api.php")
    Call<ServerResponse> post(
            @Field("method") String method,
            @Field("username") String username,
            @Field("password") String password
    );

  //This method is used for "GET"
    @GET("/api.php")
    Call<ServerResponse> get(
            @Query("method") String method,
            @Query("username") String username,
            @Query("password") String password
    );*/




