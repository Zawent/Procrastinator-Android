package co.edu.procastnew;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class InicioBloqueo extends AppCompatActivity {

    private Spinner appSpinner;
    private EditText hoursEditText;
    private EditText minutesEditText;
    private Button blockButton;
    private TextView activeBlocksDisplay;
    private List<String> installedApps;
    private BroadcastReceiver receiver;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloqueador);

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

        installedApps = getInstalledApps();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, installedApps);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appSpinner.setAdapter(adapter);

        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, installedApps);
        ListView appListView = findViewById(R.id.app_listview);
        appListView.setAdapter(listViewAdapter);


    Button BloqueoG = findViewById(R.id.botongeneral);
        BloqueoG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BloquearGeneral(listViewAdapter);
            }
        });
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


    private List<String> getInstalledApps() {
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> apps = packageManager.getInstalledApplications(0);
        List<String> packageNames = new ArrayList<>();
        for (ApplicationInfo appInfo : apps) {
            packageNames.add(appInfo.packageName);
            Log.d("InicioBloqueo", "Package name es " + appInfo.packageName);
        }
        return packageNames;
    }


    private void Bloquear() {
        String NombreApp;
        if (appSpinner.getVisibility() == View.VISIBLE) {
            NombreApp = appSpinner.getSelectedItem().toString();
        } else {
            NombreApp = getSelectedAppsFromListView();
        }

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
        int TiempoSegundos = (hours * 60 + minutes) * 60;
        Intent serviceIntent = new Intent(this, BloqueoService.class);
        serviceIntent.putExtra("NombreApp", NombreApp);
        serviceIntent.putExtra("TiempoEnSegundos", TiempoSegundos);
        startService(serviceIntent);

    }
    private String getSelectedAppsFromListView() {
        ListView appListView = findViewById(R.id.app_listview);
        SparseBooleanArray checkedItems = appListView.getCheckedItemPositions();
        StringBuilder selectedAppsBuilder = new StringBuilder();

        for (int i = 0; i < checkedItems.size(); i++) {
            int position = checkedItems.keyAt(i);
            if (checkedItems.get(position)) {
                selectedAppsBuilder.append(installedApps.get(position)).append(", ");
            }
        }
        if (selectedAppsBuilder.length() > 0) {
            selectedAppsBuilder.setLength(selectedAppsBuilder.length() - 2);
        }

        return selectedAppsBuilder.toString();
    }
    private void BloquearGeneral(ArrayAdapter<String> listViewAdapter) {
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