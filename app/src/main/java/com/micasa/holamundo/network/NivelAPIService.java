package com.micasa.holamundo.network;

import com.micasa.holamundo.model.Nivel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface NivelAPIService {

    @GET("nivel")
    Class<List<Nivel>> getAll();

    @GET("nivel/{id}")
    Call <Nivel> getOne(@Header("Authorization") String authorization, @Path("id")long id);
}
