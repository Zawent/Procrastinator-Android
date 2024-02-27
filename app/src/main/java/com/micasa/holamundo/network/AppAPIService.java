package com.micasa.holamundo.network;

import com.micasa.holamundo.model.App;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppAPIService {

    @FormUrlEncoded
    @POST("app")
    Call<App> crearApp (@Header("Authorization")String authorization, @Field("nombre") String nombre, @Field("id_user")long id_user);

    @GET("app")
    Call<List<App>> getApps (@Header("Authorization")String authorization);

    @FormUrlEncoded
    @POST("apps/{id_user}")
    Call<List<App>> getAppsUser(@Header("Authorization")String authorization, @Field("id_user")long id_user);
}
