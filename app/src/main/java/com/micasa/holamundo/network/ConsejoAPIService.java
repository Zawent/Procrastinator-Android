package com.micasa.holamundo.network;

import com.micasa.holamundo.model.Consejo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ConsejoAPIService {

    @GET("consejo/{id}")
    Call<Consejo> getConsejo(@Header("Authorization") String authorization, @Path("id") long id);

}
