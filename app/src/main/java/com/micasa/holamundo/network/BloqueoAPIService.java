package com.micasa.holamundo.network;

import com.micasa.holamundo.model.Bloqueo;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BloqueoAPIService {

    @FormUrlEncoded
    @POST("bloqueo")
    Call<Bloqueo> crearBloqueo(@Header("Authorization") String authorization, @Field("hora_inicio") Time hora_inicio, @Field("duracion")Time duracion, @Field("estado") String estado, @Field("id_app") long id_app);

    @GET("bloqueados/topApps/{id_user}")
    Call<Bloqueo> topApps(@Header("Authorization")String authorization);

}
