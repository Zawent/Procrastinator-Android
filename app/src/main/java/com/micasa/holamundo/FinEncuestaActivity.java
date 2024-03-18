package com.micasa.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micasa.holamundo.model.Nivel;
import com.micasa.holamundo.model.User;
import com.micasa.holamundo.network.LoginAPICliente;
import com.micasa.holamundo.network.LoginAPIService;
import com.micasa.holamundo.network.NivelAPICliente;
import com.micasa.holamundo.network.NivelAPIService;
import com.micasa.holamundo.yalogueado.MenuInicioActivity;
import com.micasa.holamundo.yalogueado.consejos.MenuConsejoActivity;
import com.micasa.holamundo.yalogueado.edituser.Perfil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinEncuestaActivity extends AppCompatActivity {

    User user;
    TextView nivel;
    TextView textNiv;
    private LinearLayout overlayLayout;
    LoginAPIService service;

    NivelAPIService serviceN;
    private Button btnBloqueo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_encuesta);
        service = LoginAPICliente.getLoginService();
        serviceN = NivelAPICliente.getNivelService();
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
                    DataInfo.respuestaLogin.setUser(user);
                    fijarNivel();

                    textNiv = findViewById(R.id.textNivel);
                    serviceN.getOne(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token(), user.getNivel_id()).enqueue(new Callback<Nivel>() {
                        @Override
                        public void onResponse(Call<Nivel> call, Response<Nivel> response) {
                            Nivel respuesta = response.body();
                            textNiv.setText(""+respuesta.getDescripcion());
                        }

                        @Override
                        public void onFailure(Call<Nivel> call, Throwable t) {
                            Log.e("Error", t.getMessage());
                        }
                    });
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

    public void irAMenuInicio(View view){
        startActivity(new Intent(FinEncuestaActivity.this, MainActivity.class));
    }


}