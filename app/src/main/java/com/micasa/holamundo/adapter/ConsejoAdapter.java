package com.micasa.holamundo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.micasa.holamundo.R;
import com.micasa.holamundo.model.Consejo;

import java.util.List;

public class ConsejoAdapter extends BaseAdapter {

    private List<Consejo> consejos;
    private Context context;

    public ConsejoAdapter(List<Consejo> consejos, Context context) {
        this.consejos = consejos;
        this.context = context;
    }

    @Override
    public int getCount() {return consejos.size();}

    @Override
    public Object getItem(int position) {return consejos.get(position);}

    @Override
    public long getItemId(int position) {return consejos.get(position).getId();}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.consejo_item_layout, null);
        }

        Consejo consejo = consejos.get(position);

        TextView txtId = convertView.findViewById(R.id.idDelConsejo);
        TextView txtConsejo = convertView.findViewById(R.id.contenidoConsejo);

        txtId.setText(String.valueOf(consejo.getId()));
        txtConsejo.setText(String.valueOf(consejo.getConsejo()));
        return convertView;
    }



}
