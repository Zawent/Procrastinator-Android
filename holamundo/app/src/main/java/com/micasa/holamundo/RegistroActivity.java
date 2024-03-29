package com.micasa.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.micasa.holamundo.model.RespuestaLogin;
import com.micasa.holamundo.network.RegisterAPICliente;
import com.micasa.holamundo.network.RegisterAPIService;
import com.micasa.holamundo.pregunta.Pregunta1Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private RegisterAPIService service;
    EditText caja1, caja2, caja3, caja4, caja5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        service = RegisterAPICliente.getRegisterService();
    }

    public void guardarUser (View view) {

        caja1 = findViewById(R.id.editTextNombre);
        caja2 = findViewById(R.id.editTextEdad);
        caja3 = findViewById(R.id.editTextOcupacion);
        caja4 = findViewById(R.id.editTextEmail);
        caja5 = findViewById(R.id.editTextContraseña);

        String name = caja1.getText().toString();
        int edad = Integer.parseInt(caja2.getText().toString());
        String ocupacion = caja3.getText().toString();
        String email = caja4.getText().toString();
        String password = caja5.getText().toString();
        int id_rol = 2;

        service.registrar(name, edad, ocupacion, email, password, id_rol).enqueue(new Callback<RespuestaLogin>() {
            @Override
            public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
                if (response.isSuccessful()){
                    DataInfo.respuestaLogin=response.body();
                    Intent intent = new Intent(RegistroActivity.this, Pregunta1Activity.class);
                    intent.putExtra("user", DataInfo.respuestaLogin.getUser());
                    startActivity(intent);
                }else{
                    Toast.makeText(RegistroActivity.this, "Error al crear el Usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaLogin> call, Throwable t) {
                Toast.makeText(RegistroActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        /*
        service.registrar(name, edad, ocupacion, email, password, id_rol).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("registro", response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.i("registro", call.request().body().toString());
                Log.i("registro", t.getMessage());
            }
        });*/





    }

}