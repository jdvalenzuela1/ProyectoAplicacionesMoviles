package com.example.lenovo.proyectoaplicacionesmoviles.PantallaPrincipal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.proyectoaplicacionesmoviles.R;

/**
 * Created by lenovo on 21-05-2018.
 */

public class InicioVistaFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio_vista, container, false);
    }
}