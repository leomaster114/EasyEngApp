package com.example.easyengapp.Retrofit;

import com.example.easyengapp.Model.GetResultResponse;
import com.example.easyengapp.Model.LevelByIdResponse;
import com.example.easyengapp.Model.LevelResponse;
import com.example.easyengapp.Model.LoginResponse;
import com.example.easyengapp.Model.RegisterResponse;
import com.example.easyengapp.Model.ResetPasswordResponse;
import com.example.easyengapp.Model.Result;
import com.example.easyengapp.Model.SaveResultResponse;
import com.example.easyengapp.Model.Topic;
import com.example.easyengapp.Model.TopicByIdResponse;
import com.example.easyengapp.Model.TopicResponse;
import com.example.easyengapp.Model.UpdateAvatarResponse;
import com.example.easyengapp.Model.UpdateUserResponse;
import com.example.easyengapp.Model.User;
import com.example.easyengapp.Model.UserReminderResponse;
import com.example.easyengapp.Model.UserReminderUpdateResponse;


import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
            @Part MultipartBody.Part image);
    @GET("topic/get-all-topics")
    Call<TopicResponse> getAllTopic();


    @GET("topic/get-topic-by-id")
    Call<TopicByIdResponse> getTopicById(
            @Query("id") int id);

    @POST("result/create")
    Call<SaveResultResponse> createResult(@Body Result result);
    @GET("result/get-all-result-by-user")
    Call<GetResultResponse> getAllResultByUser(@Query("user_id") String user_id);
    @GET("result/get-result-by-user-and-topic")
    Call<GetResultResponse> getResultByUserTopic(
            @Query("user_id") String user_id,
            @Query("topic_id") String topic_id
    );

    @GET("reminder/get-reminder-by-user")
    Call<UserReminderResponse> getReminderByUser(
            @Query("user_id") String user_id
    );

    @FormUrlEncoded
    @PUT("reminder/update")
    Call<UserReminderUpdateResponse> updateReminder(
            @Field("content") String content,
            @Field("time") String time,
            @Field("active") boolean active,
            @Field("user_id") String user_id
    );
    @GET("level/get-all-levels")
    Call<LevelResponse> getAllLevel();
    @GET("level/get-level-by-id")
    Call<LevelByIdResponse> getLevelById(@Query("id") int id);
    @POST("user/forget-pasword")
    @FormUrlEncoded
    Call<ResetPasswordResponse> resetPassword(
            @Field("email") String email
    );

}
