package com.micasa.holamundo.network;

import com.micasa.holamundo.model.App;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppAPIService {

    @POST("app")
    Call<App> crearApp (@Header("Authorization")String authorization, @Path("nombre")String nombre, @Path("id_user")long id_user);

    @GET("app")
    Call<List<App>> getApps (@Header("Authorization")String authorization);
}
