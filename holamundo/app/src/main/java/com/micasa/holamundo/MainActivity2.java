package com.micasa.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.micasa.holamundo.model.RespuestaLogin;
import com.micasa.holamundo.model.User;
import com.micasa.holamundo.network.LoginAPICliente;
import com.micasa.holamundo.network.LoginAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    private LoginAPIService service;
    EditText txtUsuario;
    EditText txtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        service = LoginAPICliente.getLoginService();
    }

    public void ingresar (View view){
        txtUsuario = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);

        String email = txtUsuario.getText().toString();
        String password = txtPassword.getText().toString();

        service.login(email, password).enqueue(new Callback<RespuestaLogin>() {
            @Override
            public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
                Log.i("login", response.toString());
                if (response.isSuccessful()){

                    DataInfo.respuestaLogin = response.body();
                    Intent intent = new Intent(MainActivity2.this, MenuInicioActivity.class);
                    intent.putExtra("user", DataInfo.respuestaLogin.getUser());
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity2.this, "error email/ password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaLogin> call, Throwable t) {
                Log.i("Login","Error Login");
                Toast.makeText(MainActivity2.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}