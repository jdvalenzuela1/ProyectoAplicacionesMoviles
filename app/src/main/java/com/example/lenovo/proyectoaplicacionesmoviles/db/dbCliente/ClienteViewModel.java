package com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by lenovo on 03-06-2018.
 */

public class ClienteViewModel  extends AndroidViewModel {

    private ClienteRepository mRepository;

    private LiveData<List<Cliente>> mAllClientes;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ClienteRepository(application);
        mAllClientes = mRepository.getAllClientes();
    }

    public LiveData<List<Cliente>> getAllClientes() {
        return mAllClientes;
    }

    public void insert(List<Cliente> clientes) {
        mRepository.insert(clientes);
    }
}
