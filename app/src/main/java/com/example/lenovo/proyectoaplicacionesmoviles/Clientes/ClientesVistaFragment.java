package com.example.lenovo.proyectoaplicacionesmoviles.Clientes;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.lenovo.proyectoaplicacionesmoviles.Catalogo.CatalogoVistaFragment;
import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 21-05-2018.
 */

public class ClientesVistaFragment extends Fragment {
    private ListView mainListView;
    private ClienteViewModel mClienteViewModel;
    private Button agregarCliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clientes_vista, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        agregarCliente = (Button) getActivity().findViewById(R.id.AgregarCliente);

        agregarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewClientesVistaFragment nextFrag= new NewClientesVistaFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, nextFrag,"AgregarCliente")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}