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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
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

public class CajaMensualVistaFragment extends Fragment implements NumberPicker.OnValueChangeListener {
    private TextView efectivoId;
    private TextView tarjetaId;
    private TextView depositoId;
    private TextView chequeId;
    private TextView totalId;
    int dia, mes, anio;

    private FichaServicioViewModel mFichaServicioViewModel;
    private NumberPicker picker_mes;
    private NumberPicker picker_anio;
    private CheckBox noPagados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caja_mensual, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        efectivoId = (TextView) getActivity().findViewById(R.id.EfectivoIdCajaMensual);
        tarjetaId = (TextView) getActivity().findViewById(R.id.TarjetaIdCajaMensual);
        depositoId = (TextView) getActivity().findViewById(R.id.DepositoIdCajaMensual);
        chequeId = (TextView) getActivity().findViewById(R.id.ChequeIdCajaMensual);
        totalId = (TextView) getActivity().findViewById(R.id.TotalIdCajaMensual);
        noPagados = (CheckBox) getActivity().findViewById(R.id.NoPagadoIdCajaMensual);

        mFichaServicioViewModel = ViewModelProviders.of(this).get(FichaServicioViewModel.class);

        Calendar cal = Calendar.getInstance();

        picker_mes = (NumberPicker) getActivity().findViewById(R.id.picker_mes);
        picker_anio = (NumberPicker) getActivity().findViewById(R.id.picker_anio);

        picker_mes.setMinValue(1);
        picker_mes.setMaxValue(12);
        picker_mes.setValue(cal.get(Calendar.MONTH) + 1);
        picker_mes.setOnValueChangedListener(this);

        int year = cal.get(Calendar.YEAR);
        picker_anio.setMinValue(2015);
        picker_anio.setMaxValue(2080);
        picker_anio.setValue(year);
        picker_anio.setOnValueChangedListener(this);

        ActualizarMediosPago();


        noPagados.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ActualizarMediosPago();
            }
        });
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        ActualizarMediosPago();
    }

    private void ActualizarMediosPago() {
        mFichaServicioViewModel = ViewModelProviders.of(getActivity()).get(FichaServicioViewModel.class);
        try {
            mFichaServicioViewModel.getAllFichaServicioByParametersMonthAndYear(picker_mes.getValue(), picker_anio.getValue()).observe(getActivity(), new Observer<List<FichaServicio>>() {

                @Override
                public void onChanged(@Nullable final List<FichaServicio> fichaServicios) {
                    int efectivo = 0;
                    int tarjeta = 0;
                    int deposito = 0;
                    int cheque = 0;
                    int total = 0;

                    int total_servicios = fichaServicios.size();

                    for (int i = 0; i < fichaServicios.size(); i++){
                        if (noPagados.isChecked()){

                            if (fichaServicios.get(i).getMedio_pago() == 0) {
                                efectivo += fichaServicios.get(i).getPrecio();
                            } else if (fichaServicios.get(i).getMedio_pago() == 1) {
                                tarjeta += fichaServicios.get(i).getPrecio();
                            } else if (fichaServicios.get(i).getMedio_pago() == 2) {
                                deposito += fichaServicios.get(i).getPrecio();
                            } else if (fichaServicios.get(i).getMedio_pago() == 3) {
                                cheque += fichaServicios.get(i).getPrecio();
                            }
                        } else {
                            if (fichaServicios.get(i).getPagado()) {
                                if (fichaServicios.get(i).getMedio_pago() == 0) {
                                    efectivo += fichaServicios.get(i).getPrecio();
                                } else if (fichaServicios.get(i).getMedio_pago() == 1) {
                                    tarjeta += fichaServicios.get(i).getPrecio();
                                } else if (fichaServicios.get(i).getMedio_pago() == 2) {
                                    deposito += fichaServicios.get(i).getPrecio();
                                } else if (fichaServicios.get(i).getMedio_pago() == 3) {

                                }
                            }
                        }
                    }

                    total = efectivo + tarjeta + deposito + cheque;

                    efectivoId.setText("Efectivo: " + Integer.toString(efectivo));
                    tarjetaId.setText("Tarjeta: " + Integer.toString(tarjeta));
                    depositoId.setText("Deposito: " + Integer.toString(deposito));
                    chequeId.setText("Cheque: " + Integer.toString(cheque));
                    totalId.setText("Total: " + Integer.toString(total));
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
