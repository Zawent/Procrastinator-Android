package com.micasa.holamundo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.micasa.holamundo.R;
import com.micasa.holamundo.model.App;
import com.micasa.holamundo.model.TopApps;

import java.util.List;

public class AppsAdapter extends BaseAdapter {
    private TopApps apps;
    private Context context;

    public AppsAdapter(TopApps apps, Context context) {
        this.apps = apps;
        this.context = context;
    }

    @Override
    public int getCount() {return apps.getResultados().size();}

    @Override
    public Object getItem(int position) {return apps.getResultados().get(position);}

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.apps_item_layout, null);
        }

        App app = apps.getResultados().get(position);
        TextView txtApp = convertView.findViewById(R.id.textView19);
        TextView txtNum = convertView.findViewById(R.id.textView20);

        txtApp.setText(String.valueOf(app.getNombre()));
        txtNum.setText(String.valueOf(app.getContador()));

        return convertView;
    }
}
