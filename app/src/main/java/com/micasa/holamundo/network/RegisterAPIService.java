package com.micasa.holamundo.network;

import com.micasa.holamundo.model.RespuestaLogin;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterAPIService {
    @FormUrlEncoded
    @POST("auth/signup")
    Call<RespuestaLogin> registrar (@Field("name")String name, @Field("fecha_nacimiento")String fecha_nacimiento, @Field("ocupacion")String ocupacion, @Field("email")String email, @Field("password")String password, @Field("id_rol")int id_rol);


    /*@FormUrlEncoded
    @POST("auth/signup")
    Call<String> registrar (@Field("name")String name, @Field("edad")int edad, @Field("ocupacion")String ocupacion, @Field("email")String email, @Field("password")String password, @Field("id_rol")int id_rol);
*/
}
