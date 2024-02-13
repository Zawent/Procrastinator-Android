package com.micasa.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.micasa.holamundo.model.User;
import com.micasa.holamundo.network.LoginAPICliente;
import com.micasa.holamundo.network.LoginAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinEncuestaActivity extends AppCompatActivity {

    User user;
    TextView nivel;

    LoginAPIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_encuesta);
        service = LoginAPICliente.getLoginService();

    }

    @Override
    protected void onStart() {
        super.onStart();
        service.getUser(
                DataInfo.respuestaLogin.getToken_type()+" "+
                        DataInfo.respuestaLogin.getAccess_token()
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user=response.body();
                    fijarNivel();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }

    private void fijarNivel() {
        nivel = findViewById(R.id.nivelUser);
        nivel.setText(""+user.getNivel_id());
    }
}