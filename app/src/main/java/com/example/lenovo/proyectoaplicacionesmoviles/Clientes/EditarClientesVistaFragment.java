package com.example.lenovo.proyectoaplicacionesmoviles.Clientes;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteViewModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by lenovo on 07-06-2018.
 */

public class EditarClientesVistaFragment extends Fragment {

    private int clienteId;
    private TextView fechaCreacion;
    private EditText editNombreCliente;
    private EditText editApellidoCliente;
    private EditText editEmailCliente;
    private EditText editComentarioCliente;
    private ClienteViewModel mClienteViewModel;
    private Button actualizarCliente;
    private Button eliminarCliente;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_cliente_vista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fechaCreacion = (TextView) getActivity().findViewById(R.id.FechaCreacionCliente);
        editNombreCliente = (EditText) getActivity().findViewById(R.id.EditNombreCliente);
        editApellidoCliente = (EditText) getActivity().findViewById(R.id.EditApellidoCliente);
        editEmailCliente = (EditText) getActivity().findViewById(R.id.EditEmailCliente);
        editComentarioCliente = (EditText) getActivity().findViewById(R.id.EditComentarioCliente);

        Bundle bundle = getArguments();
        clienteId = bundle.getInt("ClienteId");

        mClienteViewModel = ViewModelProviders.of(this).get(ClienteViewModel.class);

        Cliente cliente = null;
        try {
            cliente = mClienteViewModel.SelectClienteByClienteId(clienteId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fechaCreacion.setText(cliente.getFecha_creacion());
        editNombreCliente.setText(cliente.getNombre());
        editApellidoCliente.setText(cliente.getApellido());
        editEmailCliente.setText(cliente.getEmail());
        editComentarioCliente.setText(cliente.getComentario());

        actualizarCliente = (Button) getActivity().findViewById(R.id.ActualizarCliente);
        eliminarCliente = (Button) getActivity().findViewById(R.id.EliminarCliente);

        actualizarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editNombreCliente.getText().toString().equals("") && editApellidoCliente.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Nombre y Apellido son campos obligatorio", Toast.LENGTH_LONG).show();
                } else if (editApellidoCliente.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Apellido es un campo obligatorio", Toast.LENGTH_LONG).show();
                } else if (editNombreCliente.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Nombre es un campo obligatorio", Toast.LENGTH_LONG).show();
                } else {

                    String nombreClienteString = editNombreCliente.getText().toString();
                    String apellidoClienteString = editApellidoCliente.getText().toString();
                    String emailClienteString = editEmailCliente.getText().toString();
                    String comentarioClienteString = editComentarioCliente.getText().toString();

                    Cliente cliente = new Cliente();
                    cliente.setId_cliente(clienteId);
                    cliente.setNombre(nombreClienteString);
                    cliente.setApellido(apellidoClienteString);
                    cliente.setEmail(emailClienteString);
                    cliente.setComentario(comentarioClienteString);
                    cliente.setFecha_creacion(fechaCreacion.getText().toString());

                    ArrayList<Cliente> listaCliente = new ArrayList<Cliente>();
                    listaCliente.add(cliente);

                    mClienteViewModel.updateCliente(cliente);
                    Toast.makeText(getActivity(), "Cliente Guardado con exito", Toast.LENGTH_LONG).show();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                }

            }
        });
        eliminarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClienteViewModel.deleteClienteByClienteId(clienteId);
                Toast.makeText(getActivity(), "Cliente eliminado con exito", Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }
}
