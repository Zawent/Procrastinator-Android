package com.micasa.holamundo.yalogueado.bloqueo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.micasa.holamundo.DataInfo;
import com.micasa.holamundo.R;
import com.micasa.holamundo.model.App;
import com.micasa.holamundo.model.Bloqueo;
import com.micasa.holamundo.network.AppAPICliente;
import com.micasa.holamundo.network.AppAPIService;
import com.micasa.holamundo.network.BloqueoAPICliente;
import com.micasa.holamundo.network.BloqueoAPIService;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioBloqueo extends AppCompatActivity {

    private Spinner appSpinner;
    private EditText hoursEditText;
    private EditText minutesEditText;
    private Button blockButton;
    private TextView activeBlocksDisplay;
    private List<String> installedApps;
    private BroadcastReceiver receiver;

    private BloqueoAPIService serviceB;
    private AppAPIService serviceA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloqueador);
        serviceB = BloqueoAPICliente.getBloqueoService();
        serviceA = AppAPICliente.getAppService();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }

        appSpinner = findViewById(R.id.app_spinner);
        hoursEditText = findViewById(R.id.hours_edittext);
        minutesEditText = findViewById(R.id.minutes_edittext);
        blockButton = findViewById(R.id.block_button);
        activeBlocksDisplay = findViewById(R.id.active_blocks_display);

        ListView appListView = findViewById(R.id.app_listview);

        Button BloqueoE = findViewById(R.id.botonespecifico);
        BloqueoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appListView.setVisibility(View.GONE);
                appSpinner.setVisibility(View.VISIBLE);
            }
        });
        blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bloquear();

            }
        });
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("BLOQUEOS_ACTIVOS")) {
                    String bloquesActivos = intent.getStringExtra("bloques_activos");
                    activeBlocksDisplay.setText("Bloqueos activos: " + bloquesActivos);
                }
            }
        };
        IntentFilter filter = new IntentFilter("BLOQUEOS_ACTIVOS");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        PackageManager packageManager = getPackageManager();
        installedApps = getInstalledApps(packageManager);

        serviceA.getApps(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token()).enqueue(new Callback<List<App>>() {
            @Override
            public void onResponse(Call<List<App>> call, Response<List<App>> response) {
                if(response.isSuccessful()) {
                    listarApps(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<App>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    public void listarApps (List<App> apps) {
        ArrayAdapter<App> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, apps);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appSpinner.setAdapter(adapter);

        final ArrayAdapter<App> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, apps);
        ListView appListView = findViewById(R.id.app_listview);
        appListView.setAdapter(listViewAdapter);


        Button BloqueoG = findViewById(R.id.botongeneral);
        BloqueoG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BloquearGeneral(listViewAdapter);
            }
        });
    }

    private List<String> getInstalledApps(PackageManager packageManager) {
        List<ApplicationInfo> apps = packageManager.getInstalledApplications(0);
        List<String> packageNames = new ArrayList<>();
        /*List<App> aplicaciones = new ArrayList<>();
        serviceA.getAppsUser(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token(), DataInfo.respuestaLogin.getUser().getId()).enqueue(new Callback<List<App>>() {
            @Override
            public void onResponse(Call<List<App>> call, Response<List<App>> response) {
                //aplicaciones = response.body();
            }

            @Override
            public void onFailure(Call<List<App>> call, Throwable t) {

            }
        });*/

        for (ApplicationInfo appInfo : apps) {
            packageNames.add(appInfo.packageName);
            Log.d("InicioBloqueo", "Package name es " + appInfo.packageName);
        }
        String nombres = String.join(";", packageNames);

        serviceA.crearApp(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token(), nombres, DataInfo.respuestaLogin.getUser().getId()).enqueue(new Callback<App>() {
            @Override
            public void onResponse(Call<App> call, Response<App> response) {
                Log.i("Hola", String.valueOf(response));
            }

            @Override
            public void onFailure(Call<App> call, Throwable t) {
                Log.e("Error", "Error al llamar al servicio", t);
            }
        });
        return packageNames;
    }


    private void Bloquear() {
        App NombreApp=null;
        List<App> nombresApps=null;
        if (appSpinner.getVisibility() == View.VISIBLE) {
            NombreApp = (App) appSpinner.getSelectedItem();
        } else {
            nombresApps =  getSelectedAppsFromListView();
        }

        final App tempApp = NombreApp;

        Log.d("InicioBloqueo", "Bloquear() - NombreApp: " + NombreApp);
        String hoursStr = hoursEditText.getText().toString();
        String minutesStr = minutesEditText.getText().toString();

        if (hoursStr.isEmpty() || minutesStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese un tiempo de bloqueo.", Toast.LENGTH_SHORT).show();
            return;
        }

        int hours = Integer.parseInt(hoursStr);
        int minutes = Integer.parseInt(minutesStr);

        if (hours < 0 || hours > 24 || minutes < 0 || minutes > 60) {
            Toast.makeText(this, "Por favor ingrese un tiempo válido.", Toast.LENGTH_SHORT).show();
            return;
        }
        String estadoBlock = "Activo";
        Time horaBack = Time.valueOf(String.format("%02d:%02d:00", hours, minutes));

        if (NombreApp!=null) {
            LocalDateTime time =  LocalDateTime.now();
            Time tiempo =  Time.valueOf(String.format("%02d:%02d:00", time.getHour(), time.getMinute()));
            serviceB.crearBloqueo(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token(), tiempo, horaBack, estadoBlock, NombreApp.getId()).enqueue(new Callback<Bloqueo>() {
                @Override
                public void onResponse(Call<Bloqueo> call, Response<Bloqueo> response) {
                    if (response.isSuccessful()) {
                        int TiempoSegundos = (hours * 60 + minutes) * 60;
                        Intent serviceIntent = new Intent(InicioBloqueo.this, BloqueoService.class);
                        serviceIntent.putExtra("NombreApp", tempApp.getNombre());
                        serviceIntent.putExtra("TiempoEnSegundos", TiempoSegundos);
                        startService(serviceIntent);
                    }
                }

                @Override
                public void onFailure(Call<Bloqueo> call, Throwable t) {
                    Log.e("error", t.getMessage());
                }
            });
        } else {
            String namesApps = "";
            for (App app : nombresApps) {
                if (namesApps.length()>0) {
                    namesApps+=","+app.getNombre();
                } else {
                    namesApps=app.getNombre();
                }
                LocalDateTime time =  LocalDateTime.now();
                Time tiempo =  Time.valueOf(String.format("%02d:%02d:00", time.getHour(), time.getMinute()));
                serviceB.crearBloqueo(DataInfo.respuestaLogin.getToken_type()+" "+DataInfo.respuestaLogin.getAccess_token(), tiempo, horaBack, estadoBlock, app.getId()).enqueue(new Callback<Bloqueo>() {
                    @Override
                    public void onResponse(Call<Bloqueo> call, Response<Bloqueo> response) {
                        Log.i("error",response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Bloqueo> call, Throwable t) {
                        Log.e("error", t.getMessage());
                    }
                });
            }
            int TiempoSegundos = (hours * 60 + minutes) * 60;
            Intent serviceIntent = new Intent(this, BloqueoService.class);
            serviceIntent.putExtra("NombreApp", namesApps);
            serviceIntent.putExtra("TiempoEnSegundos", TiempoSegundos);
            startService(serviceIntent);
        }


    }

    private List<App> getSelectedAppsFromListView() {
        ListView appListView = findViewById(R.id.app_listview);
        SparseBooleanArray checkedItems = appListView.getCheckedItemPositions();
        StringBuilder selectedAppsBuilder = new StringBuilder();
        List<App> lista = new ArrayList<>();

        for (int i = 0; i < checkedItems.size(); i++) {
            int position = checkedItems.keyAt(i);
            if (checkedItems.get(position)) {
                App app = (App) appListView.getItemAtPosition(position);
                lista.add(app);
                //selectedAppsBuilder.append(installedApps.get(position)).append(", ");
            }
        }
        /*if (selectedAppsBuilder.length() > 0) {
            selectedAppsBuilder.setLength(selectedAppsBuilder.length() - 2);
        }*/
        return lista;
        //return selectedAppsBuilder.toString();
    }
    private void BloquearGeneral(ArrayAdapter<App> listViewAdapter) {
        ListView appListView = findViewById(R.id.app_listview);
        appListView.setVisibility(View.VISIBLE);
        appSpinner.setVisibility(View.GONE);

        listViewAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }
    }
}