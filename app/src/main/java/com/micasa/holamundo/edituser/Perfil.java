package com.micasa.holamundo.edituser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.micasa.holamundo.DataInfo;
import com.micasa.holamundo.R;
import com.micasa.holamundo.model.User;
import com.micasa.holamundo.network.LoginAPICliente;
import com.micasa.holamundo.network.LoginAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {


    User user;
    TextView nombre_user;
    TextView nivel_user;
    TextView texto_nivel;
    TextView fecha_user;
    TextView ocupacion_user;

    LoginAPIService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        service = LoginAPICliente.getLoginService();
    }

    @Override
    protected void onStart(){
        super.onStart();
        service.getUser(
                DataInfo.respuestaLogin.getToken_type()+" "+
                        DataInfo.respuestaLogin.getAccess_token()
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user=response.body();
                    fijarNombre();
                    fijarNivel();
                   /* fijarTextoNivel();
                    fijarFecha();
                    fijarOcupacion();*/
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("error",t.getMessage());

            }
        });




    }

    private void fijarNombre(){
        nombre_user = findViewById(R.id.txtNombre);
        nombre_user.setText(""+user.getName());
    }

    private void fijarNivel(){
        nivel_user = findViewById(R.id.txtnivel);
        nivel_user.setText(""+user.getNivel_id());
    }

}