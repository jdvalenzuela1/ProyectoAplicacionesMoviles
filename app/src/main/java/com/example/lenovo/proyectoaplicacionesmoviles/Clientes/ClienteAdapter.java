package com.example.lenovo.proyectoaplicacionesmoviles.Clientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 05-06-2018.
 */

public class ClienteAdapter extends ArrayAdapter<Cliente> {
    private Context mContext;
    private List<Cliente> clientesList = new ArrayList<>();

    public ClienteAdapter(Context context, ArrayList<Cliente> list) {
        super(context, 0 , list);
        mContext = context;
        clientesList = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.clienteitemview, parent, false);
        }

        // Get the data item for this position
        Cliente cliente = getItem(position);

        // Lookup view for data population
        TextView nombre = (TextView) convertView.findViewById(R.id.NombreClienteAdapter);
        TextView apellido = (TextView) convertView.findViewById(R.id.ApellidoClienteAdapter);

        // Populate the data into the template view using the data object
        nombre.setText(cliente.getNombre());
        apellido.setText(cliente.getApellido());
        // Return the completed view to render on screen
        return convertView;
    }
}
