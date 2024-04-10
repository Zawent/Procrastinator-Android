package com.micasa.holamundo.network;

import com.micasa.holamundo.model.Pregunta;
import com.micasa.holamundo.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPIService {

    @GET("auth/user")
    Call<List<User>> getAll();

    @GET("auth/user/{id}")
    Call <User> getOne(@Path("id")long id);


    @PUT("auth/user/{id}")
    Call <User> updateUser(@Header("Authorization") String authorization, @Path("id")long id, @Body User user);
}
