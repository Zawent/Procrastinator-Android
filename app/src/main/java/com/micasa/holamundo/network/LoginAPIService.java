package com.micasa.holamundo.network;

import com.micasa.holamundo.model.RespuestaLogin;
import com.micasa.holamundo.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginAPIService {

    @FormUrlEncoded
    @POST("auth/login")
    Call <RespuestaLogin> login (@Field("email")String email, @Field("password")String password);

    @GET("auth/getuser")
    Call<User> getUser(@Header("Authorization") String authorization);

    @GET("login-google")
    Call<User> getGoogle();
}
