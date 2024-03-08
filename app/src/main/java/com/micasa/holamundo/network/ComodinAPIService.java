package com.micasa.holamundo.network;

import com.micasa.holamundo.model.Comodin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface ComodinAPIService {
        @GET("comodines/cantidad")
        Call<Integer> getComodines(@Header("Authorization") String authorization);


}
