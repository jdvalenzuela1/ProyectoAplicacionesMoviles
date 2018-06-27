package com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.lenovo.proyectoaplicacionesmoviles.db.AppDatabase;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lenovo on 26-06-2018.
 */

public class FichaServicioRepository {
    private FichaServicioDao mFichaServicioDao;
    private ClienteDao mClienteDao;

    FichaServicioRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mFichaServicioDao = db.fichaServicioDao();
        mClienteDao = db.clienteDao();
    }

    LiveData<List<FichaServicio>> getAllFichaServicio() throws ExecutionException, InterruptedException {
        AsyncTask<Void, Void, LiveData<List<FichaServicio>>> asyncTask = new FichaServicioRepository.getAllFichaServicioAsyncTask(mFichaServicioDao).execute();
        return asyncTask.get();
    }

    private static class getAllFichaServicioAsyncTask extends AsyncTask<Void, Void, LiveData<List<FichaServicio>>> {

        private FichaServicioDao mAsyncTaskDao;

        getAllFichaServicioAsyncTask(FichaServicioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<FichaServicio>> doInBackground(Void... voids) {
            LiveData<List<FichaServicio>> FichaServicio = mAsyncTaskDao.getAllFichaServicio();
            return FichaServicio;
        }
    }
}