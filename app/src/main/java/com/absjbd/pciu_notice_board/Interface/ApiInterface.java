package com.absjbd.pciu_notice_board.Interface;

import com.absjbd.pciu_notice_board.Model.NoticeModel;
import com.absjbd.pciu_notice_board.Model.ServerRequest;
import com.absjbd.pciu_notice_board.Model.ServerResponse;

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
    Call<ServerResponse> insertStudent(

            @Field("operation") String operation,
            @Field("studentName") String studentName,
            @Field("studentAddress") String studentAddress,
            @Field("studentPhone") String studentPhone,
            @Field("studentEmail") String studentEmail,
            @Field("deptCode") String deptCode,
            @Field("batchId") String batchId,
            @Field("studentId") String studentId,
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




