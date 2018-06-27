package com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;

/**
 * Created by lenovo on 26-06-2018.
 */

@Entity
public class FichaServicio {
    @PrimaryKey(autoGenerate = true)
    private int id_ficha_servicio;

    @ForeignKey(entity = Cliente.class, parentColumns = "id_cliente", childColumns = "cliente")
    private int id_cliente;

    @ColumnInfo(name = "tratamiento")
    private String tratamiento;

    @ColumnInfo(name = "fecha")
    private String fecha;

    @ColumnInfo(name = "hora")
    private String hora;

    @ColumnInfo(name = "medio_pago")
    private String medio_pago;

    @ColumnInfo(name = "comentario")
    private String comentario;

    @ColumnInfo(name = "pagado")
    private boolean pagado;

    public FichaServicio() {
        this.id_cliente = id_cliente;
        this.tratamiento = tratamiento;
        this.fecha = fecha;
        this.hora = hora;
        this.medio_pago = medio_pago;
        this.comentario = comentario;
        this.pagado = false;
    }

    public int getId_ficha_servicio() {
        return id_ficha_servicio;
    }

    public void setId_ficha_servicio(int id_ficha_servicio) {
        this.id_ficha_servicio = id_ficha_servicio;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String email) {
        this.hora = hora;
    }

    public String getMedio_pago() {
        return medio_pago;
    }

    public void setMedio_pago(String medio_pago) {

        this.medio_pago = medio_pago;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean getPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }
}