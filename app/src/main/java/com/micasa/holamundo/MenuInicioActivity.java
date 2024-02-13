package com.micasa.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.micasa.holamundo.edituser.Perfil;
import com.micasa.holamundo.model.User;

public class MenuInicioActivity extends AppCompatActivity {

    User user;
    TextView si;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        si = findViewById(R.id.BvNombre);
        si.setText(user.getName());
    }

    public void goPerfil(View view) { startActivity(new Intent(this, Perfil.class));}


}