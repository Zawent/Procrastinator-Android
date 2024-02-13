package com.micasa.holamundo.edituser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.micasa.holamundo.R;
import com.micasa.holamundo.model.User;

public class Perfil extends AppCompatActivity {


    User user;
    TextView nombre_user;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        nombre_user = findViewById(R.id.txtnombre_user);
        if (user != null) {
            nombre_user.setText(user.getName());
        }else{
            nombre_user.setText("User no");
        }
    }
}