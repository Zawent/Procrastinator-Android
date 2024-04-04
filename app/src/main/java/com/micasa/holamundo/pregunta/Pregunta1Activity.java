package com.micasa.holamundo.pregunta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.micasa.holamundo.DataInfo;
import com.micasa.holamundo.FinEncuestaActivity;
import com.micasa.holamundo.R;
import com.micasa.holamundo.RegistroActivity;
import com.micasa.holamundo.model.Pregunta;
import com.micasa.holamundo.model.Respuesta;
import com.micasa.holamundo.model.User;
import com.micasa.holamundo.network.PreguntaAPICliente;
import com.micasa.holamundo.network.PreguntaAPIService;
import com.micasa.holamundo.network.RespuestaAPICliente;
import com.micasa.holamundo.network.RespuestaAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pregunta1Activity extends AppCompatActivity {

    private PreguntaAPIService serviceP;
    private RespuestaAPIService serviceR;
    Pregunta pregunta1;
    User user;
    TextView ola;
    int numPregunta = 2;
    int cantPregunta=0;

    int respuestaSi = 0;
    int respuestaNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta1);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        serviceP = PreguntaAPICliente.getPreguntaService();
        serviceR = RespuestaAPICliente.getRespuestaService();


        serviceP.getCantidad().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    cantPregunta = response.body();
                }else{
                    cantPregunta=0;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        serviceP.getOne(DataInfo.respuestaLogin.getToken_type()+" "+
                DataInfo.respuestaLogin.getAccess_token()
                ,1).enqueue(new Callback<Pregunta>() {
            @Override
            public void onResponse(Call<Pregunta> call, Response<Pregunta> response) {
                Log.i("ola1",""+response.body());
                if (response.isSuccessful()) {
                    Log.i("ola","si");
                    pregunta1 = response.body();

                    ola = findViewById(R.id.textViewPregunta1);
                    ola.setText(pregunta1.getDescripcion_pregunta());
                }
            }

            @Override
            public void onFailure(Call<Pregunta> call, Throwable t) {
                Log.i("ola",t.getMessage());
            }
        });

    }

    public void nextSi (View view) {
        if (numPregunta <= cantPregunta) {
            serviceP.getOne(DataInfo.respuestaLogin.getToken_type()+" "+
                            DataInfo.respuestaLogin.getAccess_token()
                    ,
                    numPregunta).enqueue(new Callback<Pregunta>() {
                @Override
                public void onResponse(Call<Pregunta> call, Response<Pregunta> response) {
                    pregunta1 = response.body();
                    ola = findViewById(R.id.textViewPregunta1);
                    ola.setText(pregunta1.getDescripcion_pregunta());
                    numPregunta+=1;
                }

                @Override
                public void onFailure(Call<Pregunta> call, Throwable t) {           }
            });
        } else {
            Intent intent = new Intent(Pregunta1Activity.this, FinEncuestaActivity.class);
           // intent.putExtra("user", user);
            startActivity(intent);
            finish();

        }

        serviceR.sendRespuesta(user.getId(), 2L, null, (long) numPregunta-1).enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {

            }
        });

    }

    public void nextNo (View view) {
        if (numPregunta<=cantPregunta) {
            serviceP.getOne(DataInfo.respuestaLogin.getToken_type()+" "+
                            DataInfo.respuestaLogin.getAccess_token()
                    ,
                    numPregunta).enqueue(new Callback<Pregunta>() {
                @Override
                public void onResponse(Call<Pregunta> call, Response<Pregunta> response) {
                    pregunta1 = response.body();
                    ola = findViewById(R.id.textViewPregunta1);
                    ola.setText(pregunta1.getDescripcion_pregunta());
                    numPregunta+=1;
                }

                @Override
                public void onFailure(Call<Pregunta> call, Throwable t) {           }
            });
        } else {
            Intent intent = new Intent(Pregunta1Activity.this, FinEncuestaActivity.class);
           // intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }

        serviceR.sendRespuesta(user.getId(), 1L, null, (long) numPregunta-1).enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {

            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {

            }
        });

    }

}