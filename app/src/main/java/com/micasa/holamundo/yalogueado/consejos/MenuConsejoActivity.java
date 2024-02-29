package com.micasa.holamundo.yalogueado.consejos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.micasa.holamundo.DataInfo;
import com.micasa.holamundo.R;
import com.micasa.holamundo.adapter.ConsejoAdapter;
import com.micasa.holamundo.model.Consejo;
import com.micasa.holamundo.network.ConsejoAPICliente;
import com.micasa.holamundo.network.ConsejoAPIService;
import com.micasa.holamundo.yalogueado.MenuInicioActivity;
import com.micasa.holamundo.yalogueado.bloqueo.InicioBloqueo;
import com.micasa.holamundo.yalogueado.edituser.Perfil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuConsejoActivity extends AppCompatActivity {

    TextView numeroNivel;
    ListView lista;
    private ConsejoAPIService serviceC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_consejo);
        numeroNivel = findViewById(R.id.numNivel);
        numeroNivel.setText(""+DataInfo.respuestaLogin.getUser().getNivel_id());
        lista = findViewById(R.id.listaConsejo);
        serviceC = ConsejoAPICliente.getConsejoService();
    }

    protected void onStart(){
        super.onStart();
        loadData();
    }

    private void loadData(){
        serviceC.getAll(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token(), DataInfo.respuestaLogin.getUser().getNivel_id()).enqueue(new Callback<List<Consejo>>() {
            @Override
            public void onResponse(Call<List<Consejo>> call, Response<List<Consejo>> response) {
                if (response.isSuccessful()) {
                    List<Consejo> consejos = response.body();
                    cargarConsejos(consejos);
                }
            }

            @Override
            public void onFailure(Call<List<Consejo>> call, Throwable t) {
                Log.e("error", String.valueOf(t));
            }
        });
    }

    private void cargarConsejos(List<Consejo> consejos) {
        ConsejoAdapter datos = new ConsejoAdapter(consejos, this);
        lista.setAdapter(datos);
    }

    public void irABloqueo(View view) {
        startActivity(new Intent(MenuConsejoActivity.this, InicioBloqueo.class));
    }

    public void irAMenuInicio(View view){
        startActivity(new Intent(MenuConsejoActivity.this, MenuInicioActivity.class));
    }

    public void irAPerfil(View view) {
        startActivity(new Intent(MenuConsejoActivity.this, Perfil.class));
    }

    public void irAConsejos(View view) {
        startActivity(new Intent(MenuConsejoActivity.this, MenuConsejoActivity.class));
    }
}