package com.micasa.holamundo.yalogueado;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.micasa.holamundo.DataInfo;
import com.micasa.holamundo.MainActivity;
import com.micasa.holamundo.MainActivity2;
import com.micasa.holamundo.R;
import com.micasa.holamundo.adapter.AppsAdapter;
import com.micasa.holamundo.model.Bloqueo;
import com.micasa.holamundo.model.Comodin;
import com.micasa.holamundo.model.Consejo;
import com.micasa.holamundo.model.RespuestaLogin;
import com.micasa.holamundo.model.TopApps;
import com.micasa.holamundo.network.BloqueoAPICliente;
import com.micasa.holamundo.network.BloqueoAPIService;
import com.micasa.holamundo.network.ConsejoAPICliente;
import com.micasa.holamundo.network.ConsejoAPIService;
import com.micasa.holamundo.network.LoginAPICliente;
import com.micasa.holamundo.network.LoginAPIService;
import com.micasa.holamundo.yalogueado.bloqueo.InicioBloqueo;
import com.micasa.holamundo.yalogueado.comodin.comodin;
import com.micasa.holamundo.yalogueado.consejos.MenuConsejoActivity;
import com.micasa.holamundo.yalogueado.edituser.Perfil;
import com.micasa.holamundo.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuInicioActivity extends AppCompatActivity {

    User user;
    TextView si;
    TextView consejoDiario;
    private LinearLayout overlayLayout;
    private ConsejoAPIService serviceC;

    private LoginAPIService serviceL;
    private BloqueoAPIService serviceB;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);
        user = DataInfo.respuestaLogin.getUser();
        serviceC = ConsejoAPICliente.getConsejoService();
        serviceB = BloqueoAPICliente.getBloqueoService();
        serviceL = LoginAPICliente.getLoginService();
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


        serviceB.ListaTopApps(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token()).enqueue(new Callback<TopApps>() {
            @Override
            public void onResponse(Call<TopApps> call, Response<TopApps> response) {
                TopApps respuesta = response.body();
                Log.i("respuesta", respuesta.toString());
                cargarLista(respuesta);
            }

            @Override
            public void onFailure(Call<TopApps> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });


    }

    public void logout (View view){
        serviceL.logout(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token()).enqueue(new Callback<RespuestaLogin>() {
            @Override
            public void onResponse(Call<RespuestaLogin> call, Response<RespuestaLogin> response) {
                if (response.isSuccessful()){
                    SharedPreferences usuario = MenuInicioActivity.this.getSharedPreferences("token", MODE_PRIVATE);
                    SharedPreferences.Editor editUsuario = usuario.edit();
                    editUsuario.clear().apply();

                    Intent intent = new Intent(MenuInicioActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RespuestaLogin> call, Throwable t) {

            }
        });
    }

    private void cargarLista(TopApps respuesta) {
        AppsAdapter adapter = new AppsAdapter(respuesta, this);
        lista = findViewById(R.id.lista_topApps);
        lista.setAdapter(adapter);
    }


    public void irAPerfil(View view) {
        startActivity(new Intent(MenuInicioActivity.this, Perfil.class));
        finish();
    }

    public void irAConsejos(View view) {
        startActivity(new Intent(MenuInicioActivity.this, MenuConsejoActivity.class));
        finish();
    }

    public void irABloqueo(View view) {
        startActivity((new Intent(MenuInicioActivity.this, InicioBloqueo.class)));
        finish();
    }

    public void irAComodin(View view) {
        startActivity((new Intent(MenuInicioActivity.this, comodin.class)));
        finish();
    }

}