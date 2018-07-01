package com.example.lenovo.proyectoaplicacionesmoviles.Agenda;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lenovo.proyectoaplicacionesmoviles.Clientes.ClienteAdapter;
import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lenovo on 27-06-2018.
 */

public class BuscarClienteFichaServicioFragment extends Fragment {
    private ClienteViewModel mClienteViewModel;
    private RecyclerView recyclerFichaServicio;
    private EditText fichaServicioNombreBuscador;
    private EditText fichaServicioApellidoBuscador;
    private EditText fichaServicioComentarioBuscador;

    private String estado;
    private int FichaServicioId;

    private int anio, mes, dia, hora, minuto;
    private int medioPago, precio;
    private boolean pago;
    private String tratamiento, comentario;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar_cliente_ficha_servicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Agregar los Clientes de la base de datos al RecyclerView
        try {
            AgregarClientesFichaServicioRecyclerView();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Obtener los Parametros anteriormente llenados
        Bundle bundle = getArguments();
        anio = bundle.getInt("anio");
        mes = bundle.getInt("mes");
        dia = bundle.getInt("dia");
        hora = bundle.getInt("hora");
        minuto = bundle.getInt("minuto");
        precio = bundle.getInt("precio");
        FichaServicioId = bundle.getInt("FichaServicioId");
        tratamiento = bundle.getString("Tratamiento");
        medioPago = bundle.getInt("MedioPago");
        comentario = bundle.getString("Comentario");
        pago = bundle.getBoolean("Pago");
        estado = bundle.getString("Estado");
        // Inicializar los filtros de busqueda
        fichaServicioNombreBuscador = (EditText) getActivity().findViewById(R.id.FichaServicioNombreBuscador);
        fichaServicioApellidoBuscador = (EditText) getActivity().findViewById(R.id.FichaServicioApellidoBuscador);
        fichaServicioComentarioBuscador = (EditText) getActivity().findViewById(R.id.FichaServicioComentarioBuscador);



        fichaServicioNombreBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    AgregarClientesFiltradosFichaServicioRecyclerView();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fichaServicioApellidoBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    AgregarClientesFiltradosFichaServicioRecyclerView();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fichaServicioComentarioBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    AgregarClientesFiltradosFichaServicioRecyclerView();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void AgregarClientesFiltradosFichaServicioRecyclerView() throws ExecutionException, InterruptedException {

        String nombreSearchString = fichaServicioNombreBuscador.getText().toString();
        String apellidoSearchString = fichaServicioApellidoBuscador.getText().toString();
        String comentarioSearchString = fichaServicioComentarioBuscador.getText().toString();
        if (nombreSearchString == "" && apellidoSearchString == "" && comentarioSearchString == "") {
            AgregarClientesFichaServicioRecyclerView();
        } else {
            // Agregar los Clientes de la base de datos al RecyclerView
            recyclerFichaServicio = (RecyclerView) getActivity().findViewById(R.id.FichaServicioClientesRecyclerId);
            recyclerFichaServicio.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            mClienteViewModel = ViewModelProviders.of(getActivity()).get(ClienteViewModel.class);
            mClienteViewModel.getAllClientesBySearchParameters("%" + nombreSearchString + "%",
                    "%" + apellidoSearchString + "%", "%" + comentarioSearchString + "%")
                    .observe(getActivity(), new Observer<List<Cliente>>() {
                        @Override
                        public void onChanged(@Nullable final List<Cliente> clientes) {
                            ClienteAdapter adapterClientes = new ClienteAdapter(clientes);
                            adapterClientes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("ClienteId", clientes.get(recyclerFichaServicio.getChildAdapterPosition(v)).getId_cliente());
                                    bundle.putString("ClienteNombre", clientes.get(recyclerFichaServicio.getChildAdapterPosition(v)).getNombre());
                                    bundle.putString("ClienteApellido", clientes.get(recyclerFichaServicio.getChildAdapterPosition(v)).getApellido());
                                    bundle.putInt("anio", anio);
                                    bundle.putInt("mes", mes);
                                    bundle.putInt("dia", dia);
                                    bundle.putInt("hora", hora);
                                    bundle.putInt("minuto", minuto);
                                    bundle.putInt("precio", precio);
                                    bundle.putString("Tratamiento", tratamiento);
                                    bundle.putInt("MedioPago", medioPago);
                                    bundle.putString("Comentario", comentario);
                                    bundle.putBoolean("Pago", pago);
                                    bundle.putInt("FichaServicioId", FichaServicioId);
                                    bundle.putBoolean("DatosNuevos", true);

                                    if (estado.equals("Agregando")) {
                                        Fragment nuevaFichaServicioVistaFragment = new NuevaFichaServicioVistaFragment();
                                        nuevaFichaServicioVistaFragment.setArguments(bundle);
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.flContent, nuevaFichaServicioVistaFragment, "nuevaFichaServicioVistaFragment")
                                                .addToBackStack(null).commit();
                                    } else {

                                        Fragment editarFichaServicioVistaFragment = new EditarFichaServicioVistaFragment();
                                        editarFichaServicioVistaFragment.setArguments(bundle);
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.flContent, editarFichaServicioVistaFragment, "editarFichaServicioVistaFragment")
                                                .addToBackStack(null).commit();
                                    }
                                }
                            });
                            recyclerFichaServicio.setAdapter(adapterClientes);
                        }
                    });
        }
    }

    private void AgregarClientesFichaServicioRecyclerView() throws ExecutionException, InterruptedException {
        recyclerFichaServicio = (RecyclerView) getActivity().findViewById(R.id.FichaServicioClientesRecyclerId);
        recyclerFichaServicio.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        Log.d("Mensaje", "funciona");
        mClienteViewModel = ViewModelProviders.of(this).get(ClienteViewModel.class);
        mClienteViewModel.getAllClientes().observe(this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(@Nullable final List<Cliente> clientes) {
                ClienteAdapter adapterClientes = new ClienteAdapter(clientes);
                adapterClientes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("ClienteId", clientes.get(recyclerFichaServicio.getChildAdapterPosition(v)).getId_cliente());
                        bundle.putString("ClienteNombre", clientes.get(recyclerFichaServicio.getChildAdapterPosition(v)).getNombre());
                        bundle.putString("ClienteApellido", clientes.get(recyclerFichaServicio.getChildAdapterPosition(v)).getApellido());
                        bundle.putInt("anio", anio);
                        bundle.putInt("mes", mes);
                        bundle.putInt("dia", dia);
                        bundle.putInt("hora", hora);
                        bundle.putInt("minuto", minuto);
                        bundle.putInt("precio", precio);
                        bundle.putString("Comentario", comentario);
                        bundle.putBoolean("Pago", pago);
                        bundle.putString("Tratamiento", tratamiento);
                        bundle.putInt("MedioPago", medioPago);
                        bundle.putInt("FichaServicioId", FichaServicioId);
                        bundle.putBoolean("DatosNuevos", true);

                        if (estado.equals("Agregando")) {
                            Fragment nuevaFichaServicioVistaFragment = new NuevaFichaServicioVistaFragment();
                            nuevaFichaServicioVistaFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.flContent, nuevaFichaServicioVistaFragment, "nuevaFichaServicioVistaFragment")
                                    .addToBackStack(null).commit();
                        } else {

                            Fragment editarFichaServicioVistaFragment = new EditarFichaServicioVistaFragment();
                            editarFichaServicioVistaFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.flContent, editarFichaServicioVistaFragment, "editarFichaServicioVistaFragment")
                                    .addToBackStack(null).commit();
                        }
                    }
                });
                recyclerFichaServicio.setAdapter(adapterClientes);
            }
        });
    }
}

