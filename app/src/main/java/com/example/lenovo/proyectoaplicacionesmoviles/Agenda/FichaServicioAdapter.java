package com.example.lenovo.proyectoaplicacionesmoviles.Agenda;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.proyectoaplicacionesmoviles.Clientes.ClienteAdapter;
import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteViewModel;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicio;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lenovo on 26-06-2018.
 */

public class FichaServicioAdapter extends RecyclerView.Adapter<FichaServicioAdapter.ViewHolderFichaServicio> implements View.OnClickListener {

    private List<FichaServicio> fichaServiciosList;
    private View.OnClickListener listener;
    private ClienteViewModel mClienteViewModel;

    public FichaServicioAdapter(List<FichaServicio> fichaServiciosList) {
        this.fichaServiciosList = fichaServiciosList;
    }

    @NonNull
    @Override
    public FichaServicioAdapter.ViewHolderFichaServicio onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha_servicio_item_list, null, false);
        view.setOnClickListener(this);

        this.mClienteViewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(ClienteViewModel.class);

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
            Cliente cliente = null;
            try {
                cliente = mClienteViewModel.SelectClienteByClienteId(fichaServicio.getId_cliente());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            NombreClienteAdapter.setText(cliente.getNombre());
            ApellidoClienteAdapter.setText(cliente.getApellido());
            HoraAdapter.setText(fichaServicio.getHora());
            TratamientoAdapter.setText(fichaServicio.getTratamiento());
        }
    }
}