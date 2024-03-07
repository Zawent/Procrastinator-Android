package com.micasa.holamundo.yalogueado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.micasa.holamundo.DataInfo;
import com.micasa.holamundo.R;
import com.micasa.holamundo.model.Comodin;
import com.micasa.holamundo.model.Consejo;
import com.micasa.holamundo.network.BloqueoAPIService;
import com.micasa.holamundo.network.ConsejoAPICliente;
import com.micasa.holamundo.network.ConsejoAPIService;
import com.micasa.holamundo.yalogueado.bloqueo.InicioBloqueo;
import com.micasa.holamundo.yalogueado.comodin.comodin;
import com.micasa.holamundo.yalogueado.consejos.MenuConsejoActivity;
import com.micasa.holamundo.yalogueado.edituser.Perfil;
import com.micasa.holamundo.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuInicioActivity extends AppCompatActivity {

    User user;
    TextView si;
    TextView consejoDiario;
    private LinearLayout overlayLayout;
    private ConsejoAPIService serviceC;

    private BloqueoAPIService serviceB;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);
        user = DataInfo.respuestaLogin.getUser();
        serviceC = ConsejoAPICliente.getConsejoService();
        overlayLayout = findViewById(R.id.overlayLayout);
        overlayLayout.setVisibility(View.VISIBLE);
        si = findViewById(R.id.BvNombre);
        si.setText(user.getName());
        consejoDiario = findViewById(R.id.consejoDia);
        serviceC.getConsejo(DataInfo.respuestaLogin.getToken_type()+" " +
                DataInfo.respuestaLogin.getAccess_token(),user.getNivel_id()).enqueue(new Callback<Consejo>() {
            @Override
            public void onResponse(Call<Consejo> call, Response<Consejo> response) {
                consejoDiario.setText(""+response.body().getConsejo());
                Log.i("consejo", response.body().getConsejo());
            }

            @Override
            public void onFailure(Call<Consejo> call, Throwable t) {
                Log.i("error", t.getMessage());
            }
        });

    }


    public void irAPerfil(View view) {
        startActivity(new Intent(MenuInicioActivity.this, Perfil.class));
    }

    public void irAConsejos(View view) {
        startActivity(new Intent(MenuInicioActivity.this, MenuConsejoActivity.class));
    }

    public void irABloqueo(View view) {
        startActivity((new Intent(MenuInicioActivity.this, InicioBloqueo.class)));
    }

    public void irAComodin(View view) {
        startActivity((new Intent(MenuInicioActivity.this, comodin.class)));
    }

}