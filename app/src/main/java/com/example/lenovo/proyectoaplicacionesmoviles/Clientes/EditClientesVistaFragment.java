package com.example.lenovo.proyectoaplicacionesmoviles.Clientes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.proyectoaplicacionesmoviles.R;

/**
 * Created by lenovo on 07-06-2018.
 */

public class EditClientesVistaFragment  extends Fragment {

    private int clienteId;
    private TextView idCliente;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_clientes_vista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        clienteId = bundle.getInt("ClienteId");
        idCliente = (TextView) getActivity().findViewById(R.id.IdClientePrueba);
        idCliente.setText("el id es: "+ String.valueOf((clienteId)));
    }
}
