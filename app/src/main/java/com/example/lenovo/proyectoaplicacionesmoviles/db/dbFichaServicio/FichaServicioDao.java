package com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;

import java.util.List;

/**
 * Created by lenovo on 26-06-2018.
 */
@Dao
public interface FichaServicioDao {

    @Query("SELECT * FROM FichaServicio")
    LiveData<List<FichaServicio>> getAllFichaServicio();

    @Query("SELECT * FROM FichaServicio WHERE id_ficha_servicio = :id_ficha_servicio")
    FichaServicio SelectFichaServicioByFichaServicioId (int id_ficha_servicio);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(FichaServicio... fichaServicio);

    @Query("DELETE FROM FichaServicio")
    void deleteAll();

    @Query("DELETE FROM FichaServicio WHERE id_ficha_servicio = :id_ficha_servicio")
    void deleteFichaServicioByFichaServicioId(int id_ficha_servicio);

    @Update
    void update(FichaServicio fichaServicio);

    @Query("SELECT * FROM FichaServicio WHERE fecha LIKE :fecha order by hora")
    LiveData<List<FichaServicio>> getAllFichaServicioByParameters(String fecha);

    @Query("SELECT * FROM FichaServicio WHERE fecha LIKE :fecha")
    LiveData<List<FichaServicio>> getAllFichaServicioByParametersMonthAndYear(String fecha);

    @Query("SELECT * FROM FichaServicio WHERE id_notificacion = :id_notificacion")
    FichaServicio SelectFichaServicioByNotificationId(Integer id_notificacion);
}
