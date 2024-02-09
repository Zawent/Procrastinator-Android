package com.micasa.holamundo.network;

import com.micasa.holamundo.model.Respuesta;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RespuestaAPIService {
    @FormUrlEncoded
    @POST("respuesta")
    Call<Respuesta> sendRespuesta (@Field("id_user") Long id_user, @Field("respuesta") Long respuesta, @Field("id_nivel") Long id_nivel, @Field("id_pregunta") Long id_pregunta);
}
