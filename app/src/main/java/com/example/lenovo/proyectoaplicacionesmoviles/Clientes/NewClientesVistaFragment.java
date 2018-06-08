package com.example.lenovo.proyectoaplicacionesmoviles.Clientes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteViewModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lenovo on 05-06-2018.
 */

public class NewClientesVistaFragment extends Fragment {
    private Button guardarCliente;
    private EditText nombreCliente;
    private EditText apellidoCliente;
    private EditText emailCliente;
    private EditText comentarioCliente;

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
        nombreCliente = (EditText) getActivity().findViewById(R.id.NombreCliente);
        apellidoCliente = (EditText) getActivity().findViewById(R.id.ApellidoCliente);
        emailCliente = (EditText) getActivity().findViewById(R.id.EmailCliente);
        comentarioCliente = (EditText) getActivity().findViewById(R.id.ComentarioCliente);

        guardarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nombreCliente.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Nombre es un campo obligatorio", Toast.LENGTH_LONG).show();
                } else if (apellidoCliente.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Apellido es un campo obligatorio", Toast.LENGTH_LONG).show();
                } else if (nombreCliente.getText().toString().equals("") && apellidoCliente.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Nombre y Apellido son campos obligatorios", Toast.LENGTH_LONG).show();
                } else {

                    String nombreClienteString = nombreCliente.getText().toString();
                    String apellidoClienteString = apellidoCliente.getText().toString();
                    String emailClienteString = emailCliente.getText().toString();
                    String comentarioClienteString = comentarioCliente.getText().toString();
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                    Cliente cliente = new Cliente();
                    cliente.setNombre(nombreClienteString);
                    cliente.setApellido(apellidoClienteString);
                    cliente.setEmail(emailClienteString);
                    cliente.setComentario(comentarioClienteString);
                    cliente.setFecha_creacion(currentDateTimeString);

                    ArrayList<Cliente> listaCliente = new ArrayList<Cliente>();
                    listaCliente.add(cliente);

                    mClienteViewProvider.insert(listaCliente);
                    Toast.makeText(getActivity(), "Cliente Guardado con exito", Toast.LENGTH_LONG).show();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                }
            }
        });
    }
}
