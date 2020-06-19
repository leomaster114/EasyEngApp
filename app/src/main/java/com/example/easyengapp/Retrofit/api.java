package com.example.easyengapp.Retrofit;

import com.example.easyengapp.Model.LoginResponse;
import com.example.easyengapp.Model.RegisterResponse;
import com.example.easyengapp.Model.UpdateAvatarResponse;
import com.example.easyengapp.Model.UpdateUserResponse;
import com.example.easyengapp.Model.User;


import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

    @FormUrlEncoded
    @PUT("user/update/{id}")
    Call<UpdateUserResponse> updateUser(
            @Path("id") String id,
            @Field("fullname") String fullname,
            @Field("password") String password,
            @Field("email") String email
    );

    @Multipart
    @POST("upload-single-image-s3/{id}")
    Call<UpdateAvatarResponse> updateAvatar(
            @Path("id") String id,
            @Part MultipartBody.Part image
    );
}
