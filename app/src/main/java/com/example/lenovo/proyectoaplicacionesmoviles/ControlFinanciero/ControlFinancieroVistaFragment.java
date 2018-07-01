package com.example.lenovo.proyectoaplicacionesmoviles.ControlFinanciero;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lenovo.proyectoaplicacionesmoviles.R;

/**
 * Created by lenovo on 21-05-2018.
 */

public class ControlFinancieroVistaFragment extends Fragment {
    private Button cajaMensual;
    private Button cajaDiaria;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_control_financiero_vista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cajaMensual = (Button) getActivity().findViewById(R.id.CajaMensual);
        cajaDiaria = (Button) getActivity().findViewById(R.id.CajaDiaria);

        cajaDiaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment cajaDiariaVistaFragment  = new CajaDiariaVistaFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, cajaDiariaVistaFragment,"cajaDiariaVistaFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        cajaMensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment cajaMensualVistaFragment= new CajaMensualVistaFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, cajaMensualVistaFragment,"cajaMensualVistaFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}