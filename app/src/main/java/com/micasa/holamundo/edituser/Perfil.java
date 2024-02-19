package com.micasa.holamundo.edituser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.micasa.holamundo.DataInfo;
import com.micasa.holamundo.R;
import com.micasa.holamundo.model.Nivel;
import com.micasa.holamundo.model.User;
import com.micasa.holamundo.network.LoginAPICliente;
import com.micasa.holamundo.network.LoginAPIService;
import com.micasa.holamundo.network.NivelAPICliente;
import com.micasa.holamundo.network.NivelAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfil extends AppCompatActivity {

    Nivel nivel;
    User user;
    TextView nombre_user;
    TextView nivel_user;
    TextView texto_nivel;
    TextView fecha_user;
    TextView ocupacion_user;


    LoginAPIService serviceLog;
    NivelAPIService serviceNivel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        serviceLog = LoginAPICliente.getLoginService();
        serviceNivel = NivelAPICliente.getNivelService();
    }

    @Override
    protected void onStart(){
        super.onStart();
        serviceLog.getUser(
                DataInfo.respuestaLogin.getToken_type()+" "+
                        DataInfo.respuestaLogin.getAccess_token()
        ).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    user=response.body();
                    fijarNombre();
                    fijarNivel();
                    fijarTextoNivel();
                    fijarFecha();
                    fijarOcupacion();
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

        texto_nivel = findViewById(R.id.txtnivel);
        texto_nivel.setText(""+user.getNivel_id());

    }

    private void fijarTextoNivel(){
        nivel_user = findViewById(R.id.txtnombrenivel);
        serviceNivel.getOne(DataInfo.respuestaLogin.getToken_type()+" "+ DataInfo.respuestaLogin.getAccess_token(), user.getNivel_id()).enqueue(new Callback<Nivel>() {
            @Override
            public void onResponse(Call<Nivel> call, Response<Nivel> response) {
                nivel = response.body();
                nivel_user.setText(""+nivel.getDescripcion());
            }
            @Override
            public void onFailure(Call<Nivel> call, Throwable t) {
                Log.e("Error nivel", "No fue posible encontrar nivel" + t);
            }
        });

    }

    private void fijarFecha(){
        fecha_user = findViewById(R.id.txtfechanacimiento);
        fecha_user.setText(""+user.getFecha_nacimiento());
    }

    private void fijarOcupacion(){
        ocupacion_user = findViewById(R.id.txtocupacion);
        ocupacion_user.setText(""+user.getOcupacion());
    }

}