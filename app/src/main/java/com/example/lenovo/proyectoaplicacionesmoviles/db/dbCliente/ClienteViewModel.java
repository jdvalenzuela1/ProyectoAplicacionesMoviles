package com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

/**
 * Created by lenovo on 03-06-2018.
 */

public class ClienteViewModel  extends AndroidViewModel {

    private ClienteRepository mRepository;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ClienteRepository(application);
    }

    public LiveData<List<Cliente>> getAllClientes() {
        return mRepository.getAllClientes();
    }

    public LiveData<List<Cliente>> getAllClientesBySearchParameters(String nombre, String apellido, String comentario) {
        return mRepository.getAllClientesBySearchParameters(nombre, apellido, comentario);
    }
    public Cliente SelectClienteByClienteId(int id_cliente){
        return mRepository.SelectClienteByClienteId(id_cliente);
    }
    public void deleteClienteByClienteId(int id_cliente){
        mRepository.deleteClienteByClienteId(id_cliente);
    }
    public void updateCliente(Cliente cliente){
        mRepository.updateCliente(cliente);
    }

    public void insert(List<Cliente> clientes) {
        mRepository.insert(clientes);
    }
}
