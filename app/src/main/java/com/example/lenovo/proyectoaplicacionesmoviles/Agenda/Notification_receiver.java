package com.example.lenovo.proyectoaplicacionesmoviles.Agenda;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.lenovo.proyectoaplicacionesmoviles.R;

/**
 * Created by lenovo on 02-07-2018.
 */

public class Notification_receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String canalNotificacion = "FichaServicios";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        Intent r_intent = new Intent(context, AgendaVistaFragment.class);

        String nombreCliente = intent.getStringExtra("nombreCliente");
        String apellidoCliente = intent.getStringExtra("apellidoCliente");
        String tratamiento = intent.getStringExtra("tratamiento");
        int anio = intent.getIntExtra("anio", 0);
        int mes = intent.getIntExtra("mes", 0);
        int dia = intent.getIntExtra("dia", 0);
        int hora = intent.getIntExtra("hora", 0);
        int minuto = intent.getIntExtra("minuto", 0);
        int id_notificacion = intent.getIntExtra("id_notificacion", 0);

        r_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id_notificacion, r_intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(context, canalNotificacion)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(tratamiento)
                .setContentText(nombreCliente+ " " + apellidoCliente + " " + String.format("%02d", dia)+"/"+String.format("%02d", mes)+"/"+anio+" "+String.format("%02d", hora) + ":" + String.format("%02d", minuto))
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
