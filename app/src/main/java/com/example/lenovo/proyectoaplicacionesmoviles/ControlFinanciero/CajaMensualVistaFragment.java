package com.example.lenovo.proyectoaplicacionesmoviles.ControlFinanciero;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.proyectoaplicacionesmoviles.R;

/**
 * Created by lenovo on 01-07-2018.
 */

public class CajaMensualVistaFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caja_mensual, container, false);
    }
}
