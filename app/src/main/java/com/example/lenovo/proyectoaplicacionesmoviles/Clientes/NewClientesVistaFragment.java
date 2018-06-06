package com.example.lenovo.proyectoaplicacionesmoviles.Clientes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteViewModel;

import java.util.ArrayList;

/**
 * Created by lenovo on 05-06-2018.
 */

public class NewClientesVistaFragment extends Fragment {
    private Button guardarCliente;
    private Button cancelarGuardarCliente;
    private EditText nombreCliente;
    private EditText apellidoCliente;
    private EditText emailCliente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_clientes_vista, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ClienteViewModel mClienteViewProvider = ViewModelProviders.of(this).get(ClienteViewModel.class);

        guardarCliente = (Button) getActivity().findViewById(R.id.GuardarCliente);
        cancelarGuardarCliente = (Button) getActivity().findViewById(R.id.CancelarGuardarCliente);

        nombreCliente = (EditText) getActivity().findViewById(R.id.NombreCliente);
        apellidoCliente = (EditText) getActivity().findViewById(R.id.ApellidoCliente);
        emailCliente = (EditText) getActivity().findViewById(R.id.EmailCliente);

        guardarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombreCliente.getText().toString().equals("") || apellidoCliente.getText().toString().equals("") || emailCliente.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Faltan datos por completar", Toast.LENGTH_LONG).show();
                } else {

                    String nombreClienteString = nombreCliente.getText().toString();
                    String apellidoClienteString = apellidoCliente.getText().toString();
                    String emailClienteString = emailCliente.getText().toString();

                    Cliente cliente = new Cliente();
                    cliente.setNombre(nombreClienteString);
                    cliente.setApellido(apellidoClienteString);
                    cliente.setEmail(emailClienteString);

                    ArrayList<Cliente> listaCliente = new ArrayList<Cliente>();
                    listaCliente.add(cliente);

                    mClienteViewProvider.insert(listaCliente);
                    Toast.makeText(getActivity(), "Cliente Guardado con exito", Toast.LENGTH_LONG).show();
                    volverClientesVistaFragment();
                }
            }
        });

        cancelarGuardarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverClientesVistaFragment();
            }
        });
    }
    public void volverClientesVistaFragment() {
        NewClientesVistaFragment nextFrag = new NewClientesVistaFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, nextFrag, "AgregarCliente")
                .addToBackStack(null)
                .commit();
        }
}
