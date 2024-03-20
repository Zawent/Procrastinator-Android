package com.micasa.holamundo.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginAPICliente {

    private static final String URL = "http://10.201.194.69:8000/api/";
    private static LoginAPIService instance;

    public static LoginAPIService getLoginService() {
        if (instance==null) {
            final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            /*
            httpClient.addInterceptor(logging);
            httpClient.setConnectTimeout$okhttp(15);
*/
            Retrofit http = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();

            //Retrofit http = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
            instance = http.create(LoginAPIService.class);
        }
        return instance;
    }
}


