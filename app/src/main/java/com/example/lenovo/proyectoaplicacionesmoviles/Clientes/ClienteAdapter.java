package com.example.lenovo.proyectoaplicacionesmoviles.Clientes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;

import java.util.List;

/**
 * Created by lenovo on 05-06-2018.
 */

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolderClientes> implements View.OnClickListener {

    private List<Cliente> clientesList;
    private View.OnClickListener listener;

    public ClienteAdapter(List<Cliente> clientesList) {
        this.clientesList = clientesList;
    }

    @NonNull
    @Override
    public ViewHolderClientes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cliente_item_list, null, false);
        view.setOnClickListener(this);
        return new ViewHolderClientes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClientes holder, int position) {
        holder.asignarDatos(clientesList.get(position));
    }

    @Override
    public int getItemCount() {
        return clientesList.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolderClientes extends RecyclerView.ViewHolder {
        TextView NombreClienteAdapter;
        TextView ApellidoClienteAdapter;
        TextView FechaCreacionAdapter;

        public ViewHolderClientes(View itemView) {
            super(itemView);
            NombreClienteAdapter = itemView.findViewById(R.id.NombreClienteAdapter);
            ApellidoClienteAdapter = itemView.findViewById(R.id.ApellidoClienteAdapter);
            FechaCreacionAdapter = itemView.findViewById(R.id.FechaCreacionAdapter);
        }

        public void asignarDatos(Cliente cliente) {
            NombreClienteAdapter.setText(cliente.getNombre());
            ApellidoClienteAdapter.setText(cliente.getApellido());
            FechaCreacionAdapter.setText(cliente.getFecha_creacion());
        }
    }
}
