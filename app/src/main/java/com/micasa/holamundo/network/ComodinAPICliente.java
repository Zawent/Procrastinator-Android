package com.micasa.holamundo.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ComodinAPICliente {
    private static final String URL= "http://10.201.194.30:8000/api/";
private static ComodinAPIService instance;

public static ComodinAPIService getCantidadComodin(){
    if (instance == null){
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        instance = retro.create(ComodinAPIService.class);
    }
    return instance;
}

}
