package com.micasa.holamundo.yalogueado.comodin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comodin);
        user = DataInfo.respuestaLogin.getUser();
        comodinService = ComodinAPICliente.getCantidadComodin();
        textViewCantComodin = findViewById(R.id.cantComodin);

        comodinService.getComodines(DataInfo.respuestaLogin.getToken_type() + " " +
                        DataInfo.respuestaLogin.getAccess_token(),
                DataInfo.respuestaLogin.getUser().getId()).enqueue(new Callback<List<Comodin>>() {
            @Override
            public void onResponse(Call<List<Comodin>> call, Response<List<Comodin>> response) {
                if (response.isSuccessful()){
                    List<Comodin> cantidadComodines = response.body();
                    int cantidad =  cantidadComodines.size();
                    textViewCantComodin.setText(" " + cantidad);
                }else{
                    textViewCantComodin.setText("No tienes comodines");
                }

            }

            @Override
            public void onFailure(Call<List<Comodin>> call, Throwable t) {
                textViewCantComodin.setText("no sirve");
            }
        });
    }

    public void irAPerfil(View view) {
        startActivity(new Intent(comodin.this, Perfil.class));
    }

    public void irAConsejos(View view) {
        startActivity(new Intent(comodin.this, MenuConsejoActivity.class));
    }

    public void irABloqueo(View view) {
        startActivity((new Intent(comodin.this, InicioBloqueo.class)));
    }

    public void irAMenuInicio(View view){
        startActivity(new Intent(comodin.this, MenuInicioActivity.class));
    }
}