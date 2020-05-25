package com.example.easyengapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient{
    private static final String url = "http://192.168.1.6:8085/api/";
    private static final String url2 = "https://doulingo-server.herokuapp.com/api/v1/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit = null;
    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(url2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized RetrofitClient getInstance(){
        if(mInstance==null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }
    public api getApi(){
        return retrofit.create(api.class);
    }
}
