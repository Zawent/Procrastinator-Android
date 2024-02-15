package com.micasa.holamundo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micasa.holamundo.edituser.Perfil;
import com.micasa.holamundo.model.User;

public class MenuInicioActivity extends AppCompatActivity {

    User user;
    TextView si;
    private LinearLayout overlayLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        overlayLayout = findViewById(R.id.overlayLayout);
        overlayLayout.setVisibility(View.VISIBLE);

        si = findViewById(R.id.BvNombre);
        si.setText(user.getName());

    }


    public void irperfil (View view) { startActivity(new Intent(this, Perfil.class));}



}