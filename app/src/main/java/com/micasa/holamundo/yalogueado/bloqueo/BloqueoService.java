package com.micasa.holamundo.yalogueado.bloqueo;

import android.app.NotificationChannel;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.micasa.holamundo.DataInfo;
import com.micasa.holamundo.R;
import com.micasa.holamundo.model.Bloqueo;
import com.micasa.holamundo.model.User;
import com.micasa.holamundo.network.BloqueoAPICliente;
import com.micasa.holamundo.network.BloqueoAPIService;
import com.micasa.holamundo.network.ComodinAPICliente;
import com.micasa.holamundo.network.ComodinAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BloqueoService extends Service {

    private static final String TAG = "Bloquiado";
    private static final long INTERVALO_VERIFICACION = 1000;
    private String NombreApp;
    private WindowManager windowManager;
    private View bloqueadoView;
    private int TiempoSegundos;
    private boolean isRunning = false;
    private Timer timer;
    private CountDownTimer countDownTimer;

    private BloqueoAPIService service;
    private ComodinAPIService serviceC;

    private Button comodinButton;

    private Bloqueo bloqueo;



    User user = DataInfo.respuestaLogin.getUser();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Bloquiado", "Servicio de bloqueo iniciado");
        if (intent != null) {
            service = BloqueoAPICliente.getBloqueoService();
            serviceC = ComodinAPICliente.getCantidadComodin();
            NombreApp = intent.getStringExtra("NombreApp");
            Log.d("Bloquiado", "AAAp " + NombreApp );
            TiempoSegundos = intent.getIntExtra("TiempoEnSegundos", 0);
            Log.d("Bloquiado", "SE INICIA CON " + NombreApp );
            bloquearApp();
            MandarBloqueos(NombreApp);
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.d("Bloquiado", "SE DESBLOQUEO " + NombreApp );
                }
            }, TiempoSegundos * 1000);
            isRunning = true;
        } else {
            stopSelf();
        }
        countDownTimer = new CountDownTimer(TiempoSegundos * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                Log.d("Bloquiado", "Tiempo restante: " + secondsRemaining + " segundos");
            }

            public void onFinish() {
                desbloquearApp();
            }
        }.start();

        return START_STICKY;

    }
    private void MandarBloqueos(String activeBlocks) {
        Intent intent = new Intent("BLOQUEOS_ACTIVOS");
        intent.putExtra("bloques_activos", activeBlocks);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        desbloquearApp();
        isRunning = false;
    }

    private void bloquearApp() {

        bloqueadoView = LayoutInflater.from(this).inflate(R.layout.bloqueado, null);

        Button btnBlouqeo=bloqueadoView.findViewById(R.id.usar_comdin);

        TextView cantComodines = bloqueadoView.findViewById(R.id.cantidadComodin);
        serviceC.getComodines(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                cantComodines.setText(""+response.body().toString());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

        btnBlouqeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service.tenerBloqueo(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token()).enqueue(new Callback<Bloqueo>() {
                    @Override
                    public void onResponse(Call<Bloqueo> call, Response<Bloqueo> response) {
                        bloqueo = response.body();
                        service.comodin(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token(), bloqueo.getId(), DataInfo.respuestaLogin.getUser().getId(),"0").enqueue(new Callback<Bloqueo>() {
                            @Override
                            public void onResponse(Call<Bloqueo> call, Response<Bloqueo> response) {
                                Log.i("si", response.body().toString());
                                //if (response.body()!=null){
                                    desbloquearApp();
                                //}

                            }

                            @Override
                            public void onFailure(Call<Bloqueo> call, Throwable t) {
                                Log.e("error comodin", t.getMessage());
                                Toast.makeText(BloqueoService.this, "No tiene comodines disponibles", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Bloqueo> call, Throwable t) {
                        Log.e("Error Bloqueo", t.getMessage() );
                    }
                });
            }
        });

        WindowManager.LayoutParams params;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        } else {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        params.gravity = Gravity.CENTER;

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(bloqueadoView, params);

        Toast.makeText(this, "La aplicación " + NombreApp + " se ha bloqueado.", Toast.LENGTH_SHORT).show();
    }
 
    private void desbloquearApp() {
        countDownTimer.cancel();

        service.desactivar(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token(), DataInfo.respuestaLogin.getUser().getId()).enqueue(new Callback<Bloqueo>() {
            @Override
            public void onResponse(Call<Bloqueo> call, Response<Bloqueo> response) {
                Log.i("desbloqueo exitoso", response.body().toString());
            }

            @Override
            public void onFailure(Call<Bloqueo> call, Throwable t) {
                Log.e("error desbloqueo", t.getMessage());
            }
        });
        if (windowManager != null && bloqueadoView != null) {
            windowManager.removeView(bloqueadoView);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BloqueoService.this, "La aplicación " + NombreApp + " se ha desbloqueado.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showNotification() {
       // NotificationChannel channel = new NotificationChannel
    }
}

