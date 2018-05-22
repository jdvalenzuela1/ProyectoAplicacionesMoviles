package com.example.lenovo.proyectoaplicacionesmoviles.Agenda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.proyectoaplicacionesmoviles.R;

/**
 * Created by lenovo on 21-05-2018.
 */

public class AgendaVistaFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agenda_vista, container, false);
    }
}