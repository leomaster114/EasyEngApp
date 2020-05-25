package com.example.easyengapp.Retrofit;

import com.example.easyengapp.moldel.LoginResponse;
import com.example.easyengapp.moldel.RegisterResponse;
import com.example.easyengapp.moldel.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface api {
    @GET("users")
    Call<List<User>> getUsers();
    @POST("user/register")
    Call<RegisterResponse> createUser(@Body User user
    );
    @FormUrlEncoded
    @POST("user/login")
    Call<LoginResponse> LoginUser(
            @Field("username") String username,
            @Field("password") String password
    );
}
