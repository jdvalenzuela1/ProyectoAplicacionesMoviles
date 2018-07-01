package com.example.lenovo.proyectoaplicacionesmoviles.ControlFinanciero;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicio;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicioViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lenovo on 01-07-2018.
 */

public class CajaDiariaVistaFragment  extends Fragment {
    private TextView efectivoId;
    private TextView tarjetaId;
    private TextView depositoId;
    private TextView chequeId;
    private TextView totalId;

    private FichaServicioViewModel mFichaServicioViewModel;
    private CalendarView calendario;
    private CheckBox noPagados;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caja_diaria, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        efectivoId = (TextView) getActivity().findViewById(R.id.EfectivoId);
        tarjetaId = (TextView) getActivity().findViewById(R.id.TarjetaId);
        depositoId = (TextView) getActivity().findViewById(R.id.DepositoId);
        chequeId = (TextView) getActivity().findViewById(R.id.ChequeId);
        totalId = (TextView) getActivity().findViewById(R.id.TotalId);
        noPagados = (CheckBox) getActivity().findViewById(R.id.NoPagadoId);

        mFichaServicioViewModel = ViewModelProviders.of(this).get(FichaServicioViewModel.class);

        calendario = (CalendarView) getActivity().findViewById(R.id.CalendarViewCajaDiaria);

        Calendar FechaActual = Calendar.getInstance();
        int anio = FechaActual.get(Calendar.YEAR);
        int mes = FechaActual.get(Calendar.MONTH);
        int dia = FechaActual.get(Calendar.DAY_OF_MONTH);


        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                mFichaServicioViewModel = ViewModelProviders.of(getActivity()).get(FichaServicioViewModel.class);
                try {
                    mFichaServicioViewModel.getAllFichaServicioByParameters(dayOfMonth, month, year).observe(getActivity(), new Observer<List<FichaServicio>>() {

                        @Override
                        public void onChanged(@Nullable final List<FichaServicio> fichaServicios) {
                            int efectivo = 0;
                            int tarjeta = 0;
                            int deposito = 0;
                            int cheque = 0;

                            int total = fichaServicios.size();

                            for (int i = 0; i < fichaServicios.size(); i++){
                                if (noPagados.isChecked()){
                                    int valor = fichaServicios.get(i).getMedio_pago();
                                    if (fichaServicios.get(i).getMedio_pago() == 0) {
                                        efectivo += fichaServicios.get(i).getPrecio();
                                    } else if (fichaServicios.get(i).getMedio_pago() == 1) {
                                        tarjeta += fichaServicios.get(i).getPrecio();
                                    } else if (fichaServicios.get(i).getMedio_pago() == 2) {
                                        deposito += fichaServicios.get(i).getPrecio();
                                    } else if (fichaServicios.get(i).getMedio_pago() == 3) {
                                        cheque += fichaServicios.get(i).getPrecio();
                                    }
                                    total = efectivo + tarjeta + deposito + cheque;

                                    efectivoId.setText(efectivo);
                                    tarjetaId.setText(tarjeta);
                                    depositoId.setText(deposito);
                                    chequeId.setText(cheque);
                                    totalId.setText(total);

                                } else {
                                    if (fichaServicios.get(i).getPagado()) {
                                        int valor = fichaServicios.get(i).getMedio_pago();
                                        if (fichaServicios.get(i).getMedio_pago() == 0) {
                                            efectivo += fichaServicios.get(i).getPrecio();
                                        } else if (fichaServicios.get(i).getMedio_pago() == 1) {
                                            tarjeta += fichaServicios.get(i).getPrecio();
                                        } else if (fichaServicios.get(i).getMedio_pago() == 2) {
                                            deposito += fichaServicios.get(i).getPrecio();
                                        } else if (fichaServicios.get(i).getMedio_pago() == 3) {

                                        }
                                        total = efectivo + tarjeta + deposito + cheque;

                                        efectivoId.setText(efectivo);
                                        tarjetaId.setText(tarjeta);
                                        depositoId.setText(deposito);
                                        chequeId.setText(cheque);
                                        totalId.setText(total);
                                    }
                                }
                            }
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
