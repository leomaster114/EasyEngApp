package com.example.easyengapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient2 {
    private static final String url = "https://doulingo-server.herokuapp.com/";
    private static RetrofitClient2 mInstance;
    private Retrofit retrofit = null;
    private RetrofitClient2(){
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized RetrofitClient2 getInstance(){
        if(mInstance==null){
            mInstance = new RetrofitClient2();
        }
        return mInstance;
    }
    public api getApi(){
        return retrofit.create(api.class);
    }
}
