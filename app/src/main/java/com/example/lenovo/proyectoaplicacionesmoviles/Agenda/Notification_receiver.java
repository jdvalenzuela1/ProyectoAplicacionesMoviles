package com.example.lenovo.proyectoaplicacionesmoviles.Agenda;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;

import com.example.lenovo.proyectoaplicacionesmoviles.R;
import com.example.lenovo.proyectoaplicacionesmoviles.db.AppDatabase;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteDao;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteViewModel;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicio;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicioDao;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicioRepository;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio.FichaServicioViewModel;

import java.util.concurrent.ExecutionException;

/**
 * Created by lenovo on 02-07-2018.
 */

public class Notification_receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id_notificacion = intent.getIntExtra("id_notificacion", 0);

        AppDatabase db = AppDatabase.getDatabase(context);

        AsyncTask<Integer, Void, FichaServicio> asyncTaskFicha = new Notification_receiver.selectFichaServicioByNotificationIdAsyncTask(db.fichaServicioDao());
        FichaServicio fichaServicio = null;

        try {
            fichaServicio = asyncTaskFicha.execute(id_notificacion).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (fichaServicio != null) {


            AsyncTask<Integer, Void, Cliente> asyncTaskCliente = new Notification_receiver.selectClienteByClienteIdAsyncTask(db.clienteDao());
            Cliente clienteSeleccionado = null;

            try {
                clienteSeleccionado = asyncTaskCliente.execute(fichaServicio.getId_cliente()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


            String canalNotificacion = "FichaServicios";

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                    Context.NOTIFICATION_SERVICE);
            Intent r_intent = new Intent(context, AgendaVistaFragment.class);

            String nombreCliente = clienteSeleccionado.getNombre();
            String apellidoCliente = clienteSeleccionado.getApellido();
            String tratamiento = fichaServicio.getTratamiento();

            String[] fecha_db = fichaServicio.getFecha().split("/");
            String[] hora_db = fichaServicio.getHora().split(":");

            int anio = Integer.parseInt(fecha_db[2]);
            int mes = Integer.parseInt(fecha_db[1]);
            int dia = Integer.parseInt(fecha_db[0]);
            int hora = Integer.parseInt(hora_db[0]);
            int minuto = Integer.parseInt(hora_db[1]);

            r_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, id_notificacion, r_intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder notificacion = new NotificationCompat.Builder(context, canalNotificacion)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(tratamiento)
                    .setContentText(nombreCliente + " " + apellidoCliente + " " + String.format("%02d", dia) + "/" + String.format("%02d", mes) + "/" + anio + " " + String.format("%02d", hora) + ":" + String.format("%02d", minuto))
                    .setPriority(Notification.PRIORITY_MAX)
                    .setAutoCancel(true);

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(canalNotificacion,
                        "Canal de notificacion",
                        NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager.createNotificationChannel(channel);
            }

            mNotificationManager.notify(id_notificacion, notificacion.build());

        }
    }

    private static class selectFichaServicioByNotificationIdAsyncTask extends AsyncTask<Integer, Void, FichaServicio> {

        private FichaServicioDao mAsyncTaskDao;

        selectFichaServicioByNotificationIdAsyncTask(FichaServicioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected FichaServicio doInBackground(Integer... integers) {
            FichaServicio fichaServicio = mAsyncTaskDao.SelectFichaServicioByNotificationId(integers[0]);
            return fichaServicio;
        }
    }

    private static class selectClienteByClienteIdAsyncTask extends AsyncTask<Integer, Void, Cliente> {

        private ClienteDao mAsyncTaskDao;

        selectClienteByClienteIdAsyncTask(ClienteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Cliente doInBackground(Integer... integers) {
            Cliente cliente = mAsyncTaskDao.SelectClienteByClienteId(integers[0]);
            return cliente;
        }
    }
}
