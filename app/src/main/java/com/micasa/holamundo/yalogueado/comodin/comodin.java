package com.micasa.holamundo.yalogueado.comodin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.micasa.holamundo.DataInfo;
import com.micasa.holamundo.R;
import com.micasa.holamundo.model.Comodin;
import com.micasa.holamundo.model.User;
import com.micasa.holamundo.network.ComodinAPICliente;
import com.micasa.holamundo.network.ComodinAPIService;
import com.micasa.holamundo.yalogueado.MenuInicioActivity;
import com.micasa.holamundo.yalogueado.bloqueo.InicioBloqueo;
import com.micasa.holamundo.yalogueado.consejos.MenuConsejoActivity;
import com.micasa.holamundo.yalogueado.edituser.Perfil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class comodin extends AppCompatActivity {
    private TextView textViewCantComodin;
    private ComodinAPIService comodinService;
    User user;
//Aqui se inician los procesos para determinar los comodines disponibles al iniciar la app.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comodin);
        user = DataInfo.respuestaLogin.getUser();
        comodinService = ComodinAPICliente.getCantidadComodin();
        textViewCantComodin = findViewById(R.id.cantComodin);
        comodinService.getComodines(DataInfo.respuestaLogin.getToken_type() + " " +
                DataInfo.respuestaLogin.getAccess_token()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.i("info retornada comodin", response.body().toString());
                String cantidadComodines = response.body().toString();
                textViewCantComodin.setText(""+cantidadComodines);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
//Una vez se logea el usuario se muestra la cantida de comodines que este posee.
    @Override
    protected void onStart() {
        super.onStart();
        comodinService.getComodines(DataInfo.respuestaLogin.getToken_type() + " " +
                DataInfo.respuestaLogin.getAccess_token()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.i("info retornada comodin", response.body().toString());
                String cantidadComodines = response.body().toString();
                textViewCantComodin.setText(""+cantidadComodines);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    public void irAPerfil(View view) {
        startActivity(new Intent(comodin.this, Perfil.class));
        finish();
    }

    public void irAConsejos(View view) {
        startActivity(new Intent(comodin.this, MenuConsejoActivity.class));
        finish();
    }

    public void irABloqueo(View view) {
        startActivity((new Intent(comodin.this, InicioBloqueo.class)));
        finish();
    }

    public void irAMenuInicio(View view){
        startActivity(new Intent(comodin.this, MenuInicioActivity.class));
        finish();
    }
}