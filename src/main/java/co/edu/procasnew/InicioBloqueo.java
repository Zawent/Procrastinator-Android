package co.edu.procasnew;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;



public class InicioBloqueo extends AppCompatActivity {

    private Spinner spinnerApps;
    private List<ApplicationInfo> installedApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloqueador);

        spinnerApps = findViewById(R.id.spinner_apps);

        List<String> appNames = getInstalledAppNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, appNames);
        spinnerApps.setAdapter(adapter);
    }


    private List<String> getInstalledAppNames() {
        List<String> appNames = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);

        for (PackageInfo packageInfo : packages) {
            if (!isSystemPackage(packageInfo)) {
                appNames.add(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            }
        }

        return appNames;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }
}


