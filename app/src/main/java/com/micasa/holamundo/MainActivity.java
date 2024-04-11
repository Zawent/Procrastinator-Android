package com.micasa.holamundo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.micasa.holamundo.model.Consejo;
import com.micasa.holamundo.model.RespuestaLogin;
import com.micasa.holamundo.model.User;
import com.micasa.holamundo.network.LoginAPICliente;
import com.micasa.holamundo.network.LoginAPIService;
import com.micasa.holamundo.yalogueado.MenuInicioActivity;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*Inicio de sesion por google no implementado*/
public class MainActivity extends AppCompatActivity {

    TextView txtRegistro;
    LoginAPIService service;
    //FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
/**Proceso para el inicio de sesion por medio de google (No implementado)*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // mAuth = FirebaseAuth.getInstance();
        txtRegistro = findViewById(R.id.txtRegistro);

        service = LoginAPICliente.getLoginService();

        this.validarInicio();

    }

    private void validarInicio() {
        /**Leer Shared*/
        SharedPreferences usuario = MainActivity.this.getSharedPreferences("token", MODE_PRIVATE);
        String token = usuario.getString("token", null);
        if (token!=null) {
            service.getUser("Bearer " + token).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        DataInfo.respuestaLogin = new RespuestaLogin();
                        DataInfo.respuestaLogin.setAccess_token(token);
                        DataInfo.respuestaLogin.setToken_type("Bearer");
                        DataInfo.respuestaLogin.setUser(response.body());
                        //Llamado
                        Intent intent = new Intent(MainActivity.this, MenuInicioActivity.class);
                        startActivity(intent);
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.i("error++", t.getMessage());
                }
            });

        } else {
            txtRegistro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                    startActivity(intent);
                }
            });
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this, gso);

            GoogleSignInAccount cliente = GoogleSignIn.getLastSignedInAccount(this);

            if(cliente != null){
                irSiguiente();
            }
        }
    }

    private void irSiguiente() {
        Intent intent = new Intent(this, MenuInicioActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                validarInicio();
                finish();
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 2000);
    }

    public void login (View view) {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void google (View view){
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 5000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if(requestCode == 5000){
                Task <GoogleSignInAccount> tarea = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount cuenta = tarea.getResult(ApiException.class);
                    irSiguiente();
                }
                catch (ApiException ex){
                    Log.e("error", ex.getMessage());
                }
            }

    }


}