package com.example.lenovo.proyectoaplicacionesmoviles.Clientes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lenovo on 21-05-2018.
 */

public class ClientesVistaFragment extends Fragment {
    private ClienteViewModel mClienteViewModel;
    private Button agregarCliente;
    private RecyclerView recyclerClientes;
    private EditText nombreSearch;
    private EditText apellidoSearch;
    private EditText comentarioSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_clientes_vista, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
        nombreSearch = (EditText) getActivity().findViewById(R.id.NombreSearch);
        apellidoSearch = (EditText) getActivity().findViewById(R.id.ApellidoSearch);
        comentarioSearch = (EditText) getActivity().findViewById(R.id.ComentarioSearch);

        nombreSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    AgregarClientesFiltradosRecyclerView();
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

        apellidoSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    AgregarClientesFiltradosRecyclerView();
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

        comentarioSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    AgregarClientesFiltradosRecyclerView();
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

        // Instanciar agregar cliente
        agregarCliente = (Button) getActivity().findViewById(R.id.AgregarCliente);

        agregarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newClientesVistaFragment= new NewClientesVistaFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, newClientesVistaFragment,"newClientesVistaFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void AgregarClientesFiltradosRecyclerView() throws ExecutionException, InterruptedException {
        String nombreSearchString = nombreSearch.getText().toString();
        String apellidoSearchString = apellidoSearch.getText().toString();
        String comentarioSearchString = comentarioSearch.getText().toString();
        if (nombreSearchString == "" && apellidoSearchString == "" && comentarioSearchString == "") {
            AgregarClientesRecyclerView();
        } else {
            // Agregar los Clientes de la base de datos al RecyclerView
            recyclerClientes = (RecyclerView) getActivity().findViewById(R.id.ClientesRecyclerId);
            recyclerClientes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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
                                    bundle.putInt("ClienteId", clientes.get(recyclerClientes.getChildAdapterPosition(v)).getId_cliente());

                                    Fragment editClientesVistaFragment= new EditClientesVistaFragment();
                                    editClientesVistaFragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.flContent, editClientesVistaFragment,"editClientesVistaFragment")
                                            .addToBackStack(null)
                                            .commit();
                                }
                            });
                            recyclerClientes.setAdapter(adapterClientes);
                        }
                    });
        }
    }

    private void AgregarClientesRecyclerView() throws ExecutionException, InterruptedException {
        recyclerClientes = (RecyclerView) getActivity().findViewById(R.id.ClientesRecyclerId);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));

        mClienteViewModel = ViewModelProviders.of(this).get(ClienteViewModel.class);
        mClienteViewModel.getAllClientes().observe(this, new Observer<List<Cliente>>() {
            @Override
            public void onChanged(@Nullable final List<Cliente> clientes) {
                ClienteAdapter adapterClientes =  new ClienteAdapter(clientes);
                adapterClientes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("ClienteId", clientes.get(recyclerClientes.getChildAdapterPosition(v)).getId_cliente());

                        Fragment editClientesVistaFragment= new EditClientesVistaFragment();
                        editClientesVistaFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flContent, editClientesVistaFragment,"editClientesVistaFragment")
                                .addToBackStack(null)
                                .commit();
                    }
                });
                recyclerClientes.setAdapter(adapterClientes);
            }
        });
    }
}