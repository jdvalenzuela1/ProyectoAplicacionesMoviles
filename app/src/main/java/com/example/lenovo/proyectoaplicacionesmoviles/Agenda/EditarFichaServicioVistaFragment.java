package com.example.lenovo.proyectoaplicacionesmoviles.Agenda;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicio;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicioViewModel;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by lenovo on 30-06-2018.
 */

public class EditarFichaServicioVistaFragment  extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    Spinner mediosPago;
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

    private FichaServicioViewModel mFichaServicioViewModel;
    private Button actualizarFichaServicio;
    private Button eliminarFichaServicio;
    private int FichaServicioId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_ficha_servicio_vista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mediosPago = (Spinner) getActivity().findViewById(R.id.MedioPagoFichaServicioEditar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.mediosPago, android.R.layout.simple_spinner_item);
        mediosPago.setAdapter(adapter);

        final FichaServicioViewModel mFichaServicioViewProvider = ViewModelProviders.of(this).get(FichaServicioViewModel.class);

        buscarCliente = (Button) getActivity().findViewById(R.id.BuscarFichaServicioClienteEditar);
        nombreCliente = (TextView) getActivity().findViewById(R.id.NombreClienteFichaServicioEditar);
        apellidoCliente = (TextView) getActivity().findViewById(R.id.ApellidoClienteFichaServicioEditar);
        fechayHoraBotonFichaServicio = (Button) getActivity().findViewById(R.id.FechayHoraBotonFichaServicioEditar);
        fechayHoraSeleccionadaFichaServicios = (TextView) getActivity().findViewById(R.id.FechayHoraSeleccionadaFichaServiciosEditar);
        tratamientoFichaServicio = (EditText) getActivity().findViewById(R.id.TratamientoFichaServicioEditar);

        precioFichaServicio = (MaskEditText) getActivity().findViewById(R.id.PrecioFichaServicioEditar);
        medioPagoFichaServicio = (Spinner) getActivity().findViewById(R.id.MedioPagoFichaServicioEditar);
        comentarioFichaServicio = (EditText) getActivity().findViewById(R.id.ComentarioFichaServicioEditar);

        Bundle bundle = getArguments();
        FichaServicioId = bundle.getInt("FichaServicioId");

        mFichaServicioViewModel = ViewModelProviders.of(this).get(FichaServicioViewModel.class);

        FichaServicio fichaServicio = null;
        try {
            fichaServicio = mFichaServicioViewModel.SelectFichaServicioByFichaServicioId(FichaServicioId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (bundle.getBoolean("DatosNuevos")){
            ClienteId = bundle.getInt("ClienteId");
            precioFichaServicio.setText(Integer.toString(bundle.getInt("precio")));
            nombreCliente.setText(bundle.getString("ClienteNombre"));
            apellidoCliente.setText(bundle.getString("ClienteApellido"));
            anio = bundle.getInt("anio");
            mes = bundle.getInt("mes");
            dia = bundle.getInt("dia");
            hora = bundle.getInt("hora");
            minuto = bundle.getInt("minuto");

            fechayHoraSeleccionadaFichaServicios.setText(dia+"/"+mes+"/"+anio+" "+String.format("%02d", hora) + ":" + String.format("%02d", minuto));
            medioPagoFichaServicio.setSelection(bundle.getInt("MedioPago"));
            tratamientoFichaServicio.setText(bundle.getString("Tratamiento"));
            comentarioFichaServicio.setText(bundle.getString("Comentario"));


        } else{
            ClienteId = fichaServicio.getId_cliente();
            nombreCliente.setText(bundle.getString("ClienteNombre"));
            apellidoCliente.setText(bundle.getString("ClienteApellido"));
            tratamientoFichaServicio.setText(fichaServicio.getTratamiento());
            precioFichaServicio.setText(Integer.toString(fichaServicio.getPrecio()));
            String[] fecha_db = fichaServicio.getFecha().split("/");
            String[] hora_db = fichaServicio.getHora().split(":");

            dia = Integer.parseInt(fecha_db[0]);
            mes = Integer.parseInt(fecha_db[1]);
            anio = Integer.parseInt(fecha_db[2]);
            hora = Integer.parseInt(hora_db[0]);
            minuto = Integer.parseInt(hora_db[1]);

            fechayHoraSeleccionadaFichaServicios.setText(dia+"/"+mes+"/"+anio+" "+String.format("%02d", hora) + ":" + String.format("%02d", minuto));
            medioPagoFichaServicio.setSelection(fichaServicio.getMedio_pago());
            tratamientoFichaServicio.setText(fichaServicio.getTratamiento());
            comentarioFichaServicio.setText(fichaServicio.getComentario());
        }

        fechayHoraBotonFichaServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), EditarFichaServicioVistaFragment.this, anio, mes, dia);
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
                if (precio.equals("")){
                    bundle.putInt("precio", 0);
                } else {
                    bundle.putInt("precio", Integer.parseInt(precio));
                }
                bundle.putString("Tratamiento", tratamientoFichaServicio.getText().toString());
                bundle.putInt("MedioPago", (int) medioPagoFichaServicio.getSelectedItemId());
                bundle.putString("Comentario", comentarioFichaServicio.getText().toString());
                bundle.putInt("FichaServicioId",FichaServicioId);
                bundle.putString("Estado", "Editando");


                buscarClienteFichaServicioFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, buscarClienteFichaServicioFragment,"buscarClienteFichaServicioFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        actualizarFichaServicio = (Button) getActivity().findViewById(R.id.ActualizarFichaServicio);
        eliminarFichaServicio  = (Button) getActivity().findViewById(R.id.EliminarFichaServicio);

        actualizarFichaServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id_cliente = ClienteId;
                String fecha_tratamiento = fechayHoraSeleccionadaFichaServicios.getText().toString();
                String tratamiento = tratamientoFichaServicio.getText().toString();
                int MedioPago = medioPagoFichaServicio.getSelectedItemPosition();
                int precio = 0;
                if (!precioFichaServicio.getRawText().equals("")) {
                    precio = Integer.parseInt(precioFichaServicio.getRawText());
                }
                String comentario = comentarioFichaServicio.getText().toString();

                if (id_cliente != 0 && !tratamiento.equals("")) {
                    String x = "";
                    FichaServicio fichaServicio = new FichaServicio();
                    fichaServicio.setId_cliente(id_cliente);
                    fichaServicio.setFecha(fecha_tratamiento);
                    fichaServicio.setTratamiento(tratamiento);
                    fichaServicio.setMedio_pago(MedioPago);
                    fichaServicio.setPrecio(precio);
                    fichaServicio.setComentario(comentario);


                    mFichaServicioViewProvider.updateFichaServicio(fichaServicio);
                    Toast.makeText(getActivity(), "Tratamiento actualizado con exito", Toast.LENGTH_LONG).show();

                    Fragment agendaVistaFragment= new AgendaVistaFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContent, agendaVistaFragment,"agendaVistaFragment")
                            .addToBackStack(null)
                            .commit();

                } else if (id_cliente == 0 && tratamiento.equals("")){
                    Toast dato_cliente_y_tratamiento = Toast.makeText(getActivity(), "El cliente y el tratamiento son campos obligatorios", Toast.LENGTH_SHORT);
                    dato_cliente_y_tratamiento.show();
                } else if (id_cliente == 0){
                    Toast dato_cliente = Toast.makeText(getActivity(), "El cliente es un campo obligatorio", Toast.LENGTH_SHORT);
                    dato_cliente.show();
                } else if (tratamiento.equals("")){
                    Toast dato_tratamiento = Toast.makeText(getActivity(), "El tratamiento es un campo obligatorio", Toast.LENGTH_SHORT);
                    dato_tratamiento.show();
                }

            }
        });
        eliminarFichaServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFichaServicioViewModel.deleteFichaServicioByFichaServicioId(FichaServicioId);
                Toast.makeText(getActivity(), "Ficha de Servicio eliminada con exito", Toast.LENGTH_LONG).show();
                Fragment agendaVistaFragment= new AgendaVistaFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, agendaVistaFragment,"agendaVistaFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        anio = year;
        mes = month;
        dia = dayOfMonth;

        Calendar calendarFichaServicio = Calendar.getInstance();
        hora = calendarFichaServicio.get(Calendar.HOUR_OF_DAY);
        minuto = calendarFichaServicio.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                EditarFichaServicioVistaFragment.this, hora, minuto, DateFormat.is24HourFormat(getActivity().getBaseContext()));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hora = hourOfDay;
        minuto = minute;

        fechayHoraSeleccionadaFichaServicios.setText(dia+"/"+mes+"/"+anio+" "+hora+":"+minuto);
    }
}
