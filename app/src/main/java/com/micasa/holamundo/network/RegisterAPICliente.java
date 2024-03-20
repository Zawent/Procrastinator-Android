package com.micasa.holamundo.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterAPICliente {

    private static final String URL = "http://10.201.194.69:8000/api/";

    private static RegisterAPIService instance;

    public static RegisterAPIService getRegisterService() {
        if (instance==null) {
            final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            httpClient.connectTimeout(45, TimeUnit.SECONDS);
            httpClient.readTimeout(45, TimeUnit.SECONDS);
            httpClient.writeTimeout(45, TimeUnit.SECONDS);

            Retrofit http = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();


            //Retrofit http = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
            instance = http.create(RegisterAPIService.class);
        }
        return instance;
    }

}
