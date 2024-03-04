package com.micasa.holamundo.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RespuestaAPICliente {

    private static final String URL = "http://10.201.194.45:8000/api/";

    private static RespuestaAPIService instance;

    public static RespuestaAPIService getRespuestaService(){
        if(instance==null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
            instance = retrofit.create(RespuestaAPIService.class);
        }
        return instance;
    }
}
