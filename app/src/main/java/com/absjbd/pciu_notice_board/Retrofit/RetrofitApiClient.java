package com.absjbd.pciu_notice_board.Retrofit;

import com.absjbd.pciu_notice_board.Connectivity.Config_Ref;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.*;

/**
 * Created by abs pc1 on 2017-12-16.
 */

public class RetrofitApiClient {
    private static final String BASE_URL = "https://pciunotice.000webhostapp.com"; //address of your remote server. Here I used localhost
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
/*
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();*/

                Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(Config_Ref.BASE_URL)
                        //.client(client)
                    .addConverterFactory(GsonConverterFactory.create());

            retrofit = builder.build();

        }
        return retrofit;
        }

    }
