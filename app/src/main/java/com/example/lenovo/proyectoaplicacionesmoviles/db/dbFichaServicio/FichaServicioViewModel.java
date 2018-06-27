package com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lenovo on 26-06-2018.
 */

public class FichaServicioViewModel extends AndroidViewModel {
    private FichaServicioRepository mRepository;

    public FichaServicioViewModel(@NonNull Application application) {
        super(application);
        mRepository = new FichaServicioRepository(application);
    }

    public LiveData<List<FichaServicio>> getAllFichaServicio() throws ExecutionException, InterruptedException {
        return mRepository.getAllFichaServicio();
    }
}
