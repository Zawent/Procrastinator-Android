package com.micasa.holamundo.network;

import com.micasa.holamundo.model.Bloqueo;

import java.sql.Time;
import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BloqueoAPIService {

    @GET("tener-bloqueo")
    Call<Bloqueo> tenerBloqueo(@Header("Authorization") String authorization);


    @FormUrlEncoded
    @POST("bloqueo")
    Call<Bloqueo> crearBloqueo(@Header("Authorization") String authorization, @Field("hora_inicio") Time hora_inicio, @Field("duracion")Time duracion, @Field("estado") String estado, @Field("id_app") long id_app);

    @FormUrlEncoded
    @PATCH("desactivado")
    Call<Bloqueo> desactivar(@Header("Authorization") String authorization, @Field("id_user") long id_user);

    @FormUrlEncoded
    @PATCH("desactivar-bloqueo/{id}")
    Call<Bloqueo> comodin(@Header("Authorization") String authorization,@Path("id") long id , @Field("id_user") long id_user, @Field("duracion") String duracion);

    @GET("bloqueados/topsApps")
    Call<Bloqueo> ListaTopApps(@Header("Authorization") String authorization);
}
