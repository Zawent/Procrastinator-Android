package com.micasa.holamundo.comodin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.micasa.holamundo.DataInfo;
import com.micasa.holamundo.R;
import com.micasa.holamundo.model.Comodin;
import com.micasa.holamundo.model.User;
import com.micasa.holamundo.network.ComodinAPICliente;
import com.micasa.holamundo.network.ComodinAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComodinMain extends AppCompatActivity {

    private TextView textViewCantComodin;
    private ComodinAPIService comodinService;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comodin_main);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        comodinService = ComodinAPICliente.getCantidadComodin();
        textViewCantComodin = findViewById(R.id.textViewCantComodin);

        comodinService.getComodines(DataInfo.respuestaLogin.getToken_type() + " " +
                        DataInfo.respuestaLogin.getAccess_token(),
                DataInfo.respuestaLogin.getUser().getId()).enqueue(new Callback<List<Comodin>>() {
            @Override
            public void onResponse(Call<List<Comodin>> call, Response<List<Comodin>> response) {
                if (response.isSuccessful()){
                    List<Comodin> cantidadComodines = response.body();
                    textViewCantComodin.setText("Cantidad de comodines: " + cantidadComodines);
                }else{
                    textViewCantComodin.setText("Error al obtener comodines");
                }

            }

            @Override
            public void onFailure(Call<List<Comodin>> call, Throwable t) {
                textViewCantComodin.setText("no sirve");
            }
        });
    }
}

