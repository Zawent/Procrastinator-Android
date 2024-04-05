package com.micasa.holamundo.network;

import com.micasa.holamundo.model.Pregunta;
import com.micasa.holamundo.model.RespuestaLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PreguntaAPIService {
    @GET("pregunta")
    Call<List<Pregunta>> getAll();

    @GET("pregunta/{id}")
    Call <Pregunta> getOne(@Header("Authorization") String authorization, @Path("id")long id);

    @GET("preguntas/cantidad")
    Call <Integer> getCantidad();
}
