package com.absjbd.pciu_notice_board.Retrofit;

import com.absjbd.pciu_notice_board.Connectivity.Config_Ref;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abs pc1 on 2017-12-16.
 */

public class RetrofitApiClient {
    private static final String BASE_URL = "https://pciunotice.000webhostapp.com"; //address of your remote server. Here I used localhost
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {

            /*Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();*/

                Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(Config_Ref.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

            retrofit = builder.build();

        }
        return retrofit;
        }

    }
