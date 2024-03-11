package com.micasa.holamundo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.micasa.holamundo.network.LoginAPIService;
import com.micasa.holamundo.yalogueado.MenuInicioActivity;


public class MainActivity extends AppCompatActivity {


    

    TextView txtRegistro;
    LoginAPIService service;
    //FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // mAuth = FirebaseAuth.getInstance();
        txtRegistro = findViewById(R.id.txtRegistro);

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

    private void irSiguiente() {
        Intent intent = new Intent(this, MenuInicioActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

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