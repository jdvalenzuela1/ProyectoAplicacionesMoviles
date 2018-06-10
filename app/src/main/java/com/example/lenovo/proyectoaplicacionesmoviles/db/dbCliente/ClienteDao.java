package com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by lenovo on 03-06-2018.
 */
@Dao
public interface ClienteDao {

    @Query("SELECT * FROM Cliente ORDER BY nombre, apellido")
    LiveData<List<Cliente>> getAllClientes();

    @Query("SELECT * FROM Cliente WHERE nombre LIKE :nombre " +
            "and apellido LIKE :apellido and comentario LIKE :comentario order by nombre, apellido")
    LiveData<List<Cliente>> getAllClientesBySearchParameters(String nombre, String apellido, String comentario);

    @Query("SELECT * FROM Cliente WHERE id_cliente = :id_cliente")
    Cliente SelectClienteByClienteId (int id_cliente);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Cliente... clientes);

    @Insert
    void insertOnlySingleCliente(Cliente cliente);

    @Query("DELETE FROM Cliente")
    void deleteAll();

    @Query("UPDATE Cliente SET nombre = :nombre, apellido = :apellido, comentario = :comentario, email = :email WHERE id_cliente = :id_cliente")
    void updateCliente(int id_cliente, String nombre, String apellido, String email, String comentario);

    @Query("DELETE FROM Cliente WHERE id_cliente = :id_cliente")
    void deleteClienteByClienteId(int id_cliente);

    @Update
    void update(Cliente cliente);
}

