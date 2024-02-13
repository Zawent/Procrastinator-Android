package com.micasa.holamundo.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreguntaAPICliente {

    private static final String URL = "http://10.201.194.30:8000/api/";

    private static PreguntaAPIService instance;

    public static PreguntaAPIService getPreguntaService() {
        if (instance==null) {
           /* final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);*/

            //Retrofit http = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();

            Retrofit http = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
            instance = http.create(PreguntaAPIService.class);
        }
        return instance;
    }

}
