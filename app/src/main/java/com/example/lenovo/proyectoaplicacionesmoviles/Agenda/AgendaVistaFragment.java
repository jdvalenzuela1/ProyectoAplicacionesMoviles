package com.example.lenovo.proyectoaplicacionesmoviles.Agenda;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.support.design.widget.FloatingActionButton;

import com.example.lenovo.proyectoaplicacionesmoviles.Clientes.EditarClientesVistaFragment;
import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteViewModel;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicio;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicioViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lenovo on 21-05-2018.
 */

public class AgendaVistaFragment extends Fragment {
    private RecyclerView recyclerFichas;
    private Button agregarFichaServicio;
    private FichaServicioViewModel mFichaServicioViewModel;
    private ClienteViewModel mClienteViewModel;
    private CalendarView calendario;
    private int anio, mes, dia;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agenda_vista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            AgregarFichasRecyclerView();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mClienteViewModel = ViewModelProviders.of(this).get(ClienteViewModel.class);
        FloatingActionButton agregarFichaServicio = getActivity().findViewById(R.id.AgregarFicha);
        calendario = (CalendarView) getActivity().findViewById(R.id.CalendarView);

        Calendar FechaActual = Calendar.getInstance();
        anio = FechaActual.get(Calendar.YEAR);
        mes = FechaActual.get(Calendar.MONTH);
        dia = FechaActual.get(Calendar.DAY_OF_MONTH);

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                anio = year;
                mes = month;
                dia = dayOfMonth;
                // TODO Auto-generated method stub

                // Se genera el recycler view de las fichas de dicho dia

            }
        });

        agregarFichaServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("anio", anio);
                bundle.putInt("mes", mes);
                bundle.putInt("dia", dia);

                Fragment newFichaServicioVistaFragment = new NuevaFichaServicioVistaFragment();
                newFichaServicioVistaFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, newFichaServicioVistaFragment,"newFichaServicioVistaFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void AgregarFichasRecyclerView() throws ExecutionException, InterruptedException {
        recyclerFichas = (RecyclerView) getActivity().findViewById(R.id.FichasRecyclerId);
        recyclerFichas.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerFichas.setHasFixedSize(true);

        mFichaServicioViewModel = ViewModelProviders.of(this).get(FichaServicioViewModel.class);
        mFichaServicioViewModel.getAllFichaServicio().observe(this, new Observer<List<FichaServicio>>() {

            @Override
            public void onChanged(@Nullable final List<FichaServicio> fichaServicios) {
                FichaServicioAdapter adapterFichaServicio = new FichaServicioAdapter(fichaServicios);
                adapterFichaServicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cliente cliente = null;
                        try {
                            cliente = mClienteViewModel.SelectClienteByClienteId(fichaServicios.get(recyclerFichas.getChildAdapterPosition(v)).getId_cliente());
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Bundle bundle = new Bundle();
                        bundle.putInt("FichaServicioId", fichaServicios.get(recyclerFichas.getChildAdapterPosition(v)).getId_ficha_servicio());
                        bundle.putString("ClienteNombre", cliente.getNombre());
                        bundle.putString("ClienteApellido", cliente.getApellido());

                        Fragment editarFichaServicioVistaFragment= new EditarFichaServicioVistaFragment();
                        editarFichaServicioVistaFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flContent, editarFichaServicioVistaFragment,"editarFichaServicioVistaFragment")
                                .addToBackStack(null)
                                .commit();

                    }
                });
                recyclerFichas.setAdapter(adapterFichaServicio);
            }
        });
    }
}