package com.example.lenovo.proyectoaplicacionesmoviles.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteDao;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;


@Database(entities = {Cliente.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClienteDao clienteDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}