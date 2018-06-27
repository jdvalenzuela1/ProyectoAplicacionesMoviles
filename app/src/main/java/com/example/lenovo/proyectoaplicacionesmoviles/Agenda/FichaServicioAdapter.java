package com.example.lenovo.proyectoaplicacionesmoviles.Agenda;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.proyectoaplicacionesmoviles.Clientes.ClienteAdapter;
import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicio;

import java.util.List;

/**
 * Created by lenovo on 26-06-2018.
 */

public class FichaServicioAdapter extends RecyclerView.Adapter<FichaServicioAdapter.ViewHolderFichaServicio> implements View.OnClickListener {

    private List<FichaServicio> fichaServiciosList;
    private View.OnClickListener listener;

    public FichaServicioAdapter(List<FichaServicio> fichaServiciosList) {
        this.fichaServiciosList = fichaServiciosList;
    }

    @NonNull
    @Override
    public FichaServicioAdapter.ViewHolderFichaServicio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha_servicio_item_list, null, false);
        view.setOnClickListener(this);
        return new FichaServicioAdapter.ViewHolderFichaServicio(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FichaServicioAdapter.ViewHolderFichaServicio holder, int position) {
        holder.asignarDatos(fichaServiciosList.get(position));
    }

    @Override
    public int getItemCount() {
        return fichaServiciosList.size();
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

    public class ViewHolderFichaServicio extends RecyclerView.ViewHolder {
        TextView NombreClienteAdapter;
        TextView ApellidoClienteAdapter;
        TextView HoraAdapter;
        TextView TratamientoAdapter;

        public ViewHolderFichaServicio(View itemView) {
            super(itemView);
            NombreClienteAdapter = itemView.findViewById(R.id.NombreClienteFichaServicioAdapter);
            ApellidoClienteAdapter = itemView.findViewById(R.id.ApellidoClienteFichaServicioAdapter);
            HoraAdapter = itemView.findViewById(R.id.HoraFichaServicioAdapter);
            TratamientoAdapter = itemView.findViewById(R.id.TratamientoAdapter);
        }

        public void asignarDatos(FichaServicio fichaServicio) {
            NombreClienteAdapter.setText(fichaServicio.getId_cliente());
            ApellidoClienteAdapter.setText(fichaServicio.getId_cliente());
            HoraAdapter.setText(fichaServicio.getHora());
            TratamientoAdapter.setText(fichaServicio.getTratamiento());
        }
    }
}