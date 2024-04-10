package com.micasa.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.micasa.holamundo.model.RespuestaLogin;
import com.micasa.holamundo.network.LoginAPICliente;
import com.micasa.holamundo.network.LoginAPIService;
import com.micasa.holamundo.yalogueado.MenuInicioActivity;
import com.micasa.holamundo.yalogueado.bloqueo.InicioBloqueo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*Aqui se maneja el inicio de sesion propio de la app*/
public class  MainActivity2 extends AppCompatActivity {

    private LoginAPIService service;
    EditText txtUsuario;
    EditText txtPassword;

/*Se inicia el proceso de logeo.*/
    private String PREFS_KEY = "mispreferencias";

/*Se inicia el proceso de logeo.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        service = LoginAPICliente.getLoginService();
    }
/*Aqui se obtienen los datos de logeo y se verifican.*/
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
                    //Crear el Shared
                    SharedPreferences usuario = MainActivity2.this.getSharedPreferences("token", MODE_PRIVATE);
                    SharedPreferences.Editor editUsuario = usuario.edit();
                    editUsuario.putString("token", DataInfo.respuestaLogin.getAccess_token());
                    editUsuario.commit();
                    startActivity(intent);
                    finish();

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
    public void irAprincipal(View view) {
        finish();
    }
}