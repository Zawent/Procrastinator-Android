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

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private RegisterAPIService service;
    EditText caja1, caja2, caja3, caja4, caja5;
//Se inicia el proceso de registro.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        service = RegisterAPICliente.getRegisterService();
    }
//Aqui se guardan los datos proporcionados por el usuario en el registro.
    public void guardarUser (View view) {

        caja1 = findViewById(R.id.editTextNombre);
        caja2 = findViewById(R.id.editTextEdad);
        caja3 = findViewById(R.id.editTextOcupacion);
        caja4 = findViewById(R.id.editTextEmail);
        caja5 = findViewById(R.id.editTextContraseña);

        String name = caja1.getText().toString();
        String cajaFecha = caja2.getText().toString();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        Date fecha_nacimiento = null;
        try {
            fecha_nacimiento = formato.parse(cajaFecha);
        } catch (ParseException e) {
            Toast.makeText(this, "La fecha no puede estar vacia", Toast.LENGTH_SHORT).show();
            return;
            //throw new RuntimeException(e);
        }

        String ocupacion = caja3.getText().toString();
        String email = caja4.getText().toString();
        String password = caja5.getText().toString();
        String fecha_n = formato.format(fecha_nacimiento);
        int id_rol = 2;

        service.registrar(name, fecha_n, ocupacion, email, password, id_rol).enqueue(new Callback<RespuestaLogin>() {
            @Override
            public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
                if (response.isSuccessful()){
                    DataInfo.respuestaLogin=response.body();
                    Intent intent = new Intent(RegistroActivity.this, Pregunta1Activity.class);
                    intent.putExtra("user", DataInfo.respuestaLogin.getUser());
                    startActivity(intent);
                    finish();
                }else{
                    try {
                        String msg = response.errorBody().string();
                        Toast.makeText(RegistroActivity.this, msg, Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<RespuestaLogin> call, Throwable t) {
                Toast.makeText(RegistroActivity.this, "---"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("error", t.getMessage());
            }
        });

    }

    public void irAprincipal(View view) {
        finish();
    }
}