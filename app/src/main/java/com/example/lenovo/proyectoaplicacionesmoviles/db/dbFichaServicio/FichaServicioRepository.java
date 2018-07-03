package com.example.lenovo.proyectoaplicacionesmoviles.db.dbFichaServicio;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.lenovo.proyectoaplicacionesmoviles.db.AppDatabase;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.Cliente;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteDao;
import com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente.ClienteRepository;

import java.util.ArrayList;
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

    public LiveData<List<FichaServicio>> getAllFichaServicioByParameters(int dia, int mes, int anio) throws ExecutionException, InterruptedException {
        List<Integer> parametros = new ArrayList<Integer>();
        parametros.add(dia);
        parametros.add(mes);
        parametros.add(anio);

        AsyncTask<List<Integer>, Void, LiveData<List<FichaServicio>>> asyncTask = new FichaServicioRepository.getAllFichaServicioByParametersAsyncTask(mFichaServicioDao).execute(parametros);
        return asyncTask.get();
    }

    public LiveData<List<FichaServicio>> getAllFichaServicioByParametersMonthAndYear(int mes, int anio) throws ExecutionException, InterruptedException {
        List<Integer> parametros = new ArrayList<Integer>();
        parametros.add(mes);
        parametros.add(anio);

        AsyncTask<List<Integer>, Void, LiveData<List<FichaServicio>>> asyncTask = new FichaServicioRepository.getAllFichaServicioByParametersMonthAndYearAsyncTask(mFichaServicioDao).execute(parametros);
        return asyncTask.get();
    }

    public FichaServicio SelectFichaServicioByNotificationId(int id_notificacion) throws ExecutionException, InterruptedException {
        AsyncTask<Integer, Void, FichaServicio> asyncTask = new FichaServicioRepository.selectFichaServicioByNotificationIdAsyncTask(mFichaServicioDao);
        FichaServicio fichaServicio = asyncTask.execute(id_notificacion).get();
        return fichaServicio;
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

    private static class selectFichaServicioByNotificationIdAsyncTask extends AsyncTask<Integer, Void, FichaServicio> {

        private FichaServicioDao mAsyncTaskDao;

        selectFichaServicioByNotificationIdAsyncTask(FichaServicioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected FichaServicio doInBackground(Integer... integers) {
            FichaServicio fichaServicio = mAsyncTaskDao.SelectFichaServicioByNotificationId(integers[0]);
            return fichaServicio;
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

    public class getAllFichaServicioByParametersAsyncTask extends AsyncTask<List<Integer>,  Void, LiveData<List<FichaServicio>>> {

        private FichaServicioDao mAsyncTaskDao;

        getAllFichaServicioByParametersAsyncTask(FichaServicioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<FichaServicio>> doInBackground(List<Integer>[] lists) {
            List<Integer> parametros = new ArrayList<Integer>();
            parametros = lists[0];
            Integer dia = parametros.get(0);
            Integer mes = parametros.get(1);
            Integer anio = parametros.get(2);
            String fecha_tratamiento = String.format("%02d", dia)+"/"+String.format("%02d", mes)+"/"+String.format("%02d", anio);
            return mAsyncTaskDao.getAllFichaServicioByParameters(fecha_tratamiento);
        }
    }
    public class getAllFichaServicioByParametersMonthAndYearAsyncTask extends AsyncTask<List<Integer>,  Void, LiveData<List<FichaServicio>>> {

        private FichaServicioDao mAsyncTaskDao;

        getAllFichaServicioByParametersMonthAndYearAsyncTask(FichaServicioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<FichaServicio>> doInBackground(List<Integer>[] lists) {
            List<Integer> parametros = new ArrayList<Integer>();
            parametros = lists[0];
            Integer mes = parametros.get(0);
            Integer anio = parametros.get(1);
            String fecha_tratamiento = "%"+String.format("%02d", mes)+"/"+anio+"%";
            return mAsyncTaskDao.getAllFichaServicioByParametersMonthAndYear(fecha_tratamiento);
        }
    }
}