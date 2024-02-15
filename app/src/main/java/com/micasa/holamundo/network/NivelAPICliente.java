package com.micasa.holamundo.network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NivelAPICliente {

    private static final String URL = "http://10.201.194.30:8000/api/";

    private static  NivelAPIService instance;

    public static  NivelAPIService getNivelService(){
        if (instance==null){
            Retrofit http = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
            instance = http.create(NivelAPIService.class);
        }
        return  instance;
    }
}
