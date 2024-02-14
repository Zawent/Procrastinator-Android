package com.micasa.holamundo.network;

import com.micasa.holamundo.model.Pregunta;
import com.micasa.holamundo.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserAPIService {

    @GET("user")
    Call<List<User>> getAll();

    @GET("user/{id}")
    Call <User> getOne(@Path("id")long id);
}
