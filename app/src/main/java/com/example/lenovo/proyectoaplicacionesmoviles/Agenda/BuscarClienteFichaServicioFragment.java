package com.example.lenovo.proyectoaplicacionesmoviles.Agenda;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lenovo.proyectoaplicacionesmoviles.Clientes.ClienteAdapter;
import com.example.lenovo.proyectoaplicacionesmoviles.Clientes.EditClientesVistaFragment;
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
            AgregarClientesRecyclerView();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
            AgregarClientesRecyclerView();
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

                                    Fragment nuevaFichaServicioVistaFragment= new NuevaFichaServicioVistaFragment();
                                    nuevaFichaServicioVistaFragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.flContent, nuevaFichaServicioVistaFragment,"nuevaFichaServicioVistaFragment")
                                            .addToBackStack(null);
                                }
                            });
                            recyclerFichaServicio.setAdapter(adapterClientes);
                        }
                    });
        }
    }

    private void AgregarClientesRecyclerView() throws ExecutionException, InterruptedException {
        recyclerFichaServicio = (RecyclerView) getActivity().findViewById(R.id.FichaServicioClientesRecyclerId);
        recyclerFichaServicio.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

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

                        Fragment nuevaFichaServicioVistaFragment= new EditClientesVistaFragment();
                        nuevaFichaServicioVistaFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flContent, nuevaFichaServicioVistaFragment,"nuevaFichaServicioVistaFragment")
                                .addToBackStack(null);
                    }
                });
                recyclerFichaServicio.setAdapter(adapterClientes);
            }
        });
    }
}

