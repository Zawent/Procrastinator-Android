package com.micasa.holamundo.yalogueado.edituser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.micasa.holamundo.DataInfo;
import com.micasa.holamundo.R;
import com.micasa.holamundo.RegistroActivity;
import com.micasa.holamundo.model.Nivel;
import com.micasa.holamundo.model.User;
import com.micasa.holamundo.network.NivelAPICliente;
import com.micasa.holamundo.network.NivelAPIService;
import com.micasa.holamundo.network.UserAPICliente;
import com.micasa.holamundo.network.UserAPIService;
import com.micasa.holamundo.yalogueado.MenuInicioActivity;
import com.micasa.holamundo.yalogueado.bloqueo.InicioBloqueo;
import com.micasa.holamundo.yalogueado.comodin.comodin;
import com.micasa.holamundo.yalogueado.consejos.MenuConsejoActivity;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*En esta actividad se asignan los datos al perfil de usuario y se manejan la edicion del mismo*/
public class Perfil extends AppCompatActivity {

    Nivel nivel;
    User user;
    TextView nombre_user;
    TextView nivel_user;
    TextView texto_nivel;
    TextView fecha_user;
    TextView ocupacion_user;

    EditText editNombre;
    EditText editFecha;
    EditText editOcupacion;
    Button btnGuardar;
    Button btnCancelar;
    Button btnEditar;

    NivelAPIService serviceNivel;
    UserAPIService serviceUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        serviceNivel = NivelAPICliente.getNivelService();
        serviceUser = UserAPICliente.getUserService();
    }

    @Override
    protected void onStart(){
        super.onStart();
        editNombre = findViewById(R.id.editName);
        editNombre.setVisibility(View.INVISIBLE);
        editFecha = findViewById(R.id.editFecha);
        editFecha.setVisibility(View.INVISIBLE);
        editOcupacion = findViewById(R.id.editOcupacion);
        editOcupacion.setVisibility(View.INVISIBLE);
        btnGuardar = findViewById(R.id.btnGuardarUser);
        btnGuardar.setVisibility(View.INVISIBLE);
        btnCancelar = findViewById(R.id.btnCancelarUser);
        btnCancelar.setVisibility(View.INVISIBLE);
        user=DataInfo.respuestaLogin.getUser();
        fijarNombre();
        fijarNivel();
        fijarTextoNivel();
        fijarFecha();
        fijarOcupacion();

    }

    private void fijarNombre(){
        nombre_user = findViewById(R.id.txtNombre);
        nombre_user.setText(""+user.getName());
    }

    private void fijarNivel(){

        texto_nivel = findViewById(R.id.txtnivel);
        texto_nivel.setText(""+user.getNivel_id());

    }

    private void fijarTextoNivel(){
        nivel_user = findViewById(R.id.txtnombrenivel);
        serviceNivel.getOne(DataInfo.respuestaLogin.getToken_type()+" "+ DataInfo.respuestaLogin.getAccess_token(), user.getNivel_id()).enqueue(new Callback<Nivel>() {
            @Override
            public void onResponse(Call<Nivel> call, Response<Nivel> response) {
                nivel = response.body();
                nivel_user.setText(""+nivel.getDescripcion());
            }
            @Override
            public void onFailure(Call<Nivel> call, Throwable t) {
                Log.e("Error nivel", "No fue posible encontrar nivel" + t);
            }
        });

    }

    private void fijarFecha(){
        fecha_user = findViewById(R.id.txtfechanacimiento);
        fecha_user.setText(""+user.getFecha_nacimiento());
    }

    private void fijarOcupacion(){
        ocupacion_user = findViewById(R.id.txtocupacion);
        ocupacion_user.setText(""+user.getOcupacion());
    }

    public void editarPerfil(View view) {
        btnEditar = findViewById(R.id.btneditaruser);
        editNombre.setVisibility(View.VISIBLE);
        editFecha.setVisibility(View.VISIBLE);
        editOcupacion.setVisibility(View.VISIBLE);
        btnGuardar.setVisibility(View.VISIBLE);
        btnCancelar.setVisibility(View.VISIBLE);
        nombre_user.setVisibility(View.INVISIBLE);
        fecha_user.setVisibility(View.INVISIBLE);
        ocupacion_user.setVisibility(View.INVISIBLE);
        btnEditar.setVisibility(View.INVISIBLE);
        editNombre.setText(""+user.getName());
        editFecha.setText(""+user.getFecha_nacimiento());
        editOcupacion.setText(""+user.getOcupacion());
    }
/*Aqui se obtienen los datos editados del perfil*/
    public void guardarPerfil(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Perfil.this);
        builder.setMessage("¿Estas seguro de guardar cambios?").setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre=editNombre.getText().toString();
                String fecha = editFecha.getText().toString();

                Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
                Matcher matcher = pattern.matcher(fecha);
                if (!matcher.find()) {
                    Toast.makeText(Perfil.this, "Patron de Fecha no coincide YYYY-MM-DD", Toast.LENGTH_LONG).show();
                    return;
                }
                String ocupacion = editOcupacion.getText().toString();
                user.setName(nombre);
                user.setFecha_nacimiento(fecha);
                user.setOcupacion(ocupacion);
                serviceUser.updateUser(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token(),user.getId(), user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            user=response.body();
                            DataInfo.respuestaLogin.setUser(user);
                            fijarNombre();
                            fijarNivel();
                            fijarTextoNivel();
                            fijarFecha();
                            fijarOcupacion();
                            btnCancelar.setVisibility(View.INVISIBLE);
                            btnGuardar.setVisibility(View.INVISIBLE);
                            editOcupacion.setVisibility(View.INVISIBLE);
                            editFecha.setVisibility(View.INVISIBLE);
                            editNombre.setVisibility(View.INVISIBLE);
                            nombre_user.setVisibility(View.VISIBLE);
                            fecha_user.setVisibility(View.VISIBLE);
                            ocupacion_user.setVisibility(View.VISIBLE);
                            btnEditar.setVisibility(View.VISIBLE);
                        }else{
                            try {
                                String msg = response.errorBody().string();
                                Toast.makeText(Perfil.this, msg, Toast.LENGTH_SHORT).show();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btnCancelar.setVisibility(View.INVISIBLE);
                btnGuardar.setVisibility(View.INVISIBLE);
                editOcupacion.setVisibility(View.INVISIBLE);
                editFecha.setVisibility(View.INVISIBLE);
                editNombre.setVisibility(View.INVISIBLE);
                nombre_user.setVisibility(View.VISIBLE);
                fecha_user.setVisibility(View.VISIBLE);
                ocupacion_user.setVisibility(View.VISIBLE);
                btnEditar.setVisibility(View.VISIBLE);
            }
        });
        builder.create().show();
    }

    public void cancelarPerfil(View view){
        btnCancelar.setVisibility(View.INVISIBLE);
        btnGuardar.setVisibility(View.INVISIBLE);
        editOcupacion.setVisibility(View.INVISIBLE);
        editFecha.setVisibility(View.INVISIBLE);
        editNombre.setVisibility(View.INVISIBLE);
        nombre_user.setVisibility(View.VISIBLE);
        fecha_user.setVisibility(View.VISIBLE);
        ocupacion_user.setVisibility(View.VISIBLE);
        btnEditar.setVisibility(View.VISIBLE);
    }


    public void irAConsejos(View view) {
        startActivity(new Intent(Perfil.this, MenuConsejoActivity.class));
        finish();
    }

    public void irABloqueo(View view) {
        startActivity(new Intent(Perfil.this, InicioBloqueo.class));
        finish();
    }

    public void irAMenuInicio(View view){
        startActivity(new Intent(Perfil.this, MenuInicioActivity.class));
        finish();
    }

    public void irAComodin(View view) {
        startActivity((new Intent(Perfil.this, comodin.class)));
        finish();
    }
}