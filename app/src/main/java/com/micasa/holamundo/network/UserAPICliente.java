package com.micasa.holamundo.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPICliente {

    private static final String URL = "http://192.168.146.37:8000/api/";

    private static UserAPIService instance;
    private static UserAPIService getUserService(){
        if(instance==null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
            instance = retrofit.create(UserAPIService.class);
        }
        return instance;
    }
}
