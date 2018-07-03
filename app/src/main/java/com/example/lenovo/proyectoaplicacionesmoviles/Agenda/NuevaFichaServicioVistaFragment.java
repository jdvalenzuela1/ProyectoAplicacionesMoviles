package com.example.lenovo.proyectoaplicacionesmoviles.Agenda;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by lenovo on 26-06-2018.
 */

public class NuevaFichaServicioVistaFragment extends Fragment implements TimePickerDialog.OnTimeSetListener{
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
        FloatingActionButton buscarCliente = getActivity().findViewById(R.id.BuscarFichaServicioCliente);
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

        fechayHoraSeleccionadaFichaServicios.setText(String.format("%02d", dia)+"/"+String.format("%02d", mes)+"/"+anio+" "+String.format("%02d", hora) + ":" + String.format("%02d", minuto));

        fechayHoraBotonFichaServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendarFichaServicio = Calendar.getInstance();
                hora = calendarFichaServicio.get(Calendar.HOUR_OF_DAY);
                minuto = calendarFichaServicio.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        NuevaFichaServicioVistaFragment.this, hora, minuto, DateFormat.is24HourFormat(getActivity().getBaseContext()));
                timePickerDialog.show();
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
                        bundle.putInt("hora", hora);
                        bundle.putInt("minuto", minuto);
                        String precio = precioFichaServicio.getRawText();
                        if (precio.equals("")){
                            bundle.putInt("precio", 0);
                        } else {
                            bundle.putInt("precio", Integer.parseInt(precio));
                        }
                        bundle.putString("Tratamiento", tratamientoFichaServicio.getText().toString());
                        bundle.putInt("MedioPago", (int) medioPagoFichaServicio.getSelectedItemId());
                        bundle.putString("Comentario", comentarioFichaServicio.getText().toString());
                        bundle.putString("Estado", "Agregando");


                        buscarClienteFichaServicioFragment.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flContent, buscarClienteFichaServicioFragment,"buscarClienteFichaServicioFragment")
                                .addToBackStack(null)
                                .commit();
                    }
                });
        guardarFichaServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_cliente = ClienteId;

                String fecha_tratamiento = String.format("%02d", dia)+"/"+String.format("%02d", mes)+"/"+String.format("%02d", anio);
                String hora_tratamiento = String.format("%02d", hora) + ":" + String.format("%02d", minuto);

                String tratamiento = tratamientoFichaServicio.getText().toString();
                int MedioPago = medioPagoFichaServicio.getSelectedItemPosition();
                int precio = 0;
                if (!precioFichaServicio.getRawText().equals("")) {
                    precio = Integer.parseInt(precioFichaServicio.getRawText());
                }
                String comentario = comentarioFichaServicio.getText().toString();

                if (id_cliente != 0 && !tratamiento.equals("")) {

                    // Se obtiene y actualiza la informacion de SharedPreferences
                    SharedPreferences prefs = getActivity().getSharedPreferences("notificaciones", MODE_PRIVATE);
                    int id_notificacion = prefs.getInt("id_notificacion", 0);

                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("notificaciones", MODE_PRIVATE).edit();
                    editor.putInt("id_notificacion", id_notificacion+=1);
                    editor.commit();

                    //Se crea la ficha de servicio
                    FichaServicio fichaServicio = new FichaServicio();
                    fichaServicio.setId_cliente(id_cliente);
                    fichaServicio.setFecha(fecha_tratamiento);
                    fichaServicio.setHora(hora_tratamiento);
                    fichaServicio.setTratamiento(tratamiento);
                    fichaServicio.setMedio_pago(MedioPago);
                    fichaServicio.setPrecio(precio);
                    fichaServicio.setComentario(comentario);
                    fichaServicio.setId_notificacion(id_notificacion);

                    // Se integra la informacion de la notificacion
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, anio);
                    cal.set(Calendar.MONTH, mes);
                    cal.set(Calendar.DAY_OF_MONTH, dia);
                    cal.set(Calendar.HOUR_OF_DAY, hora);
                    cal.set(Calendar.MINUTE, minuto);

                    Intent intent = new Intent(getActivity(), Notification_receiver.class);
                    intent.putExtra("nombreCliente",nombreCliente.getText());
                    intent.putExtra("apellidoCliente", apellidoCliente.getText());
                    intent.putExtra("tratamiento", tratamiento);
                    intent.putExtra("anio", anio);
                    intent.putExtra("mes", mes);
                    intent.putExtra("dia", dia);
                    intent.putExtra("hora", hora);
                    intent.putExtra("minuto", minuto);
                    intent.putExtra("id_notificacion", id_notificacion);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id_notificacion, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent);


                    ArrayList<FichaServicio> listaFichaServicio = new ArrayList<FichaServicio>();
                    listaFichaServicio.add(fichaServicio);

                    mFichaServicioViewProvider.insert(listaFichaServicio);
                    Toast.makeText(getActivity(), "Tratamiento Guardado con exito", Toast.LENGTH_LONG).show();

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
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hora = hourOfDay;
        minuto = minute;

        fechayHoraSeleccionadaFichaServicios.setText(String.format("%02d", dia)+"/"+String.format("%02d", mes)+"/"+anio+" "+String.format("%02d", hora) + ":" + String.format("%02d", minuto));
    }
}
