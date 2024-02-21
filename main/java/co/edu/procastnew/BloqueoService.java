package co.edu.procastnew;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


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


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Bloquiado", "Servicio de bloqueo iniciado");
        if (intent != null) {
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
                    desbloquearApp();
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
}

