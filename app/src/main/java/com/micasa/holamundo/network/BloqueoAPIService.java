package com.micasa.holamundo.network;

import com.micasa.holamundo.model.Bloqueo;

import java.sql.Time;
import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BloqueoAPIService {

    @POST("bloqueo")
    Call<Bloqueo> crearBloqueo(@Header("Authorization") String authorization, @Path("hora_inicio") LocalDateTime hora_inicio, @Path("duracion")Time duracion, @Path("estado") String estado, @Path("id_app") long id_app);



}
