package com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by lenovo on 03-06-2018.
 */

@Entity
public class Cliente {
    @PrimaryKey(autoGenerate = true)
    private int id_cliente;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "apellido")
    private String apellido;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "fecha_creacion")
    private String fecha_creacion;

    @ColumnInfo(name = "comentario")
    private String comentario;

    public Cliente() {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fecha_creacion = fecha_creacion;
        this.comentario = comentario;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
