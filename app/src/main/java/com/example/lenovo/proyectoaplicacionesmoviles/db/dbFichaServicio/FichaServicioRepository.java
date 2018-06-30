package com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.lenovo.proyectoaplicacionesmoviles.db.AppDatabase;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteDao;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteRepository;

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

    public FichaServicio SelectFichaServicioByFichaServicioId(int id_fecha_servicio) throws ExecutionException, InterruptedException {
        AsyncTask<Integer, Void, FichaServicio> asyncTask = new FichaServicioRepository.selectFichaServicioByFichaServicioIdAsyncTask(mFichaServicioDao);
        FichaServicio fichaServicio = asyncTask.execute(id_fecha_servicio).get();
        return fichaServicio;
    }

    public void deleteFichaServicioByFichaServicioId(int id_fecha_servicio) {
        new FichaServicioRepository.deleteFichaServicioByFichaServicioIdAsyncTask(mFichaServicioDao).execute(id_fecha_servicio);
    }

    public void updateFichaServicio(FichaServicio fichaServicio) {
        new FichaServicioRepository.updateFichaServicioAsyncTask(mFichaServicioDao).execute(fichaServicio);
    }


    public void insert(List<FichaServicio> fichaServicios) {
        FichaServicio[] fichaServiciosArray = new FichaServicio[fichaServicios.size()];
        fichaServiciosArray = fichaServicios.toArray(fichaServiciosArray);
        new FichaServicioRepository.insertAsyncTask(mFichaServicioDao).execute(fichaServiciosArray);
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
    private static class insertAsyncTask extends AsyncTask<FichaServicio, Void, Void> {

        private FichaServicioDao mAsyncTaskDao;

        insertAsyncTask(FichaServicioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FichaServicio... params) {
            mAsyncTaskDao.insertAll(params);
            return null;
        }
    }
    private static class selectFichaServicioByFichaServicioIdAsyncTask extends AsyncTask<Integer, Void, FichaServicio> {

        private FichaServicioDao mAsyncTaskDao;

        selectFichaServicioByFichaServicioIdAsyncTask(FichaServicioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected FichaServicio doInBackground(Integer... integers) {
            FichaServicio fichaServicio = mAsyncTaskDao.SelectFichaServicioByFichaServicioId(integers[0]);
            return fichaServicio;
        }
    }

    private static class deleteFichaServicioByFichaServicioIdAsyncTask extends AsyncTask<Integer, Void, Void> {

        private FichaServicioDao mAsyncTaskDao;

        deleteFichaServicioByFichaServicioIdAsyncTask(FichaServicioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.deleteFichaServicioByFichaServicioId(integers[0]);
            return null;
        }
    }
    private static class updateFichaServicioAsyncTask extends AsyncTask<FichaServicio, Void, Void> {

        private FichaServicioDao mAsyncTaskDao;

        updateFichaServicioAsyncTask(FichaServicioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FichaServicio... params) {

            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}