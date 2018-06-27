package com.example.lenovo.proyectoaplicacionesmoviles.Agenda;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.lenovo.proyectoaplicacionesmoviles.Catalogo.CatalogoVistaFragment;
import com.example.lenovo.proyectoaplicacionesmoviles.Clientes.EditClientesVistaFragment;
import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteViewModel;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicio;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicioViewModel;
import com.santalu.maskedittext.MaskEditText;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Created by lenovo on 26-06-2018.
 */

public class NuevaFichaServicioVistaFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    Spinner mediosPago;
    private Button guardarFichaServicio;
    private Button fechayHoraBotonFichaServicio;
    private Button buscarCliente;
    private int ClienteId;
    private TextView nombreCliente;
    private TextView apellidoCliente;
    private TextView fechayHoraSeleccionadaFichaServicios;
    private EditText tratamientoFichaServicio;
    private Spinner medioPagoFichaServicio;
    private EditText comentarioFichaServicio;
    private int dia, mes, anio, hora, minuto;
    private int diaFinal, mesFinal, anioFinal, horaFinal, minutoFinal;
    private MaskEditText precioFichaServicio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nueva_ficha_servicio_vista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mediosPago = (Spinner) getActivity().findViewById(R.id.MedioPagoFichaServicio);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.mediosPago, android.R.layout.simple_spinner_item);
        mediosPago.setAdapter(adapter);

        final FichaServicioViewModel mFichaServicioViewProvider = ViewModelProviders.of(this).get(FichaServicioViewModel.class);

        guardarFichaServicio = (Button) getActivity().findViewById(R.id.GuardarFichaServicio);
        buscarCliente = (Button) getActivity().findViewById(R.id.BuscarFichaServicioCliente);
        nombreCliente = (TextView) getActivity().findViewById(R.id.NombreClienteFichaServicio);
        apellidoCliente = (TextView) getActivity().findViewById(R.id.ApellidoClienteFichaServicio);
        fechayHoraBotonFichaServicio = (Button) getActivity().findViewById(R.id.FechayHoraBotonFichaServicio);
        fechayHoraSeleccionadaFichaServicios = (TextView) getActivity().findViewById(R.id.FechayHoraSeleccionadaFichaServicios);
        tratamientoFichaServicio = (EditText) getActivity().findViewById(R.id.TratamientoFichaServicio);

        precioFichaServicio = (MaskEditText) getActivity().findViewById(R.id.PrecioFichaServicio);
        medioPagoFichaServicio = (Spinner) getActivity().findViewById(R.id.MedioPagoFichaServicio);
        comentarioFichaServicio = (EditText) getActivity().findViewById(R.id.ComentarioFichaServicio);


        Bundle bundle = getArguments();
        ClienteId = bundle.getInt("ClienteId");
        String ClienteNombre = bundle.getString("ClienteNombre");
        String ClienteApellido = bundle.getString("ClienteApellido");
        anio = bundle.getInt("anio");
        mes = bundle.getInt("mes");
        dia = bundle.getInt("dia");
        hora = bundle.getInt("hora");
        minuto = bundle.getInt("minuto");
        tratamientoFichaServicio.setText(bundle.getString("Tratamiento"));
        medioPagoFichaServicio.setSelection(bundle.getInt("MedioPago"));
        int precioTemp = bundle.getInt("precio");
        if (precioTemp != 0) {
            precioFichaServicio.setText(Integer.toString(precioTemp));
        }

        comentarioFichaServicio.setText(bundle.getString("Comentario"));
        nombreCliente.setText(ClienteNombre);
        apellidoCliente.setText(ClienteApellido);

        fechayHoraSeleccionadaFichaServicios.setText(dia+"/"+mes+"/"+anio+"00:00");

        fechayHoraBotonFichaServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), NuevaFichaServicioVistaFragment.this, anio, mes, dia);
                datePickerDialog.show();
            }
        });
        buscarCliente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment buscarClienteFichaServicioFragment= new BuscarClienteFichaServicioFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("anio", anio);
                        bundle.putInt("mes", mes);
                        bundle.putInt("dia", dia);
                        String precio = precioFichaServicio.getRawText();
                        bundle.putInt("precio", Integer.parseInt(precio));
                        bundle.putString("Tratamiento", tratamientoFichaServicio.getText().toString());
                        bundle.putInt("MedioPago", (int) medioPagoFichaServicio.getSelectedItemId());
                        bundle.putString("Comentario", comentarioFichaServicio.getText().toString());


                        buscarClienteFichaServicioFragment.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flContent, buscarClienteFichaServicioFragment,"buscarClienteFichaServicioFragment")
                                .addToBackStack(null)
                                .commit();
                    }
                });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        anioFinal = year;
        mesFinal = month;
        diaFinal = dayOfMonth;

        Calendar calendarFichaServicio = Calendar.getInstance();
        hora = calendarFichaServicio.get(Calendar.HOUR_OF_DAY);
        minuto = calendarFichaServicio.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                NuevaFichaServicioVistaFragment.this, hora, minuto, DateFormat.is24HourFormat(getActivity().getBaseContext()));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        horaFinal = hourOfDay;
        minutoFinal = minute;

        fechayHoraSeleccionadaFichaServicios.setText(diaFinal+"/"+mesFinal+"/"+anioFinal+" "+horaFinal+":"+minutoFinal);
    }
}
