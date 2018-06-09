package com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.lenovo.proyectoaplicacionesmoviles.db.AppDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by lenovo on 03-06-2018.
 */

public class ClienteRepository {

    private ClienteDao mClienteDao;
    private LiveData<List<Cliente>> mAllClientes;

    ClienteRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mClienteDao = db.clienteDao();
    }

    LiveData<List<Cliente>> getAllClientes() throws ExecutionException, InterruptedException {
        AsyncTask<Void, Void, LiveData<List<Cliente>>> asyncTask = new getAllClientesAsyncTask(mClienteDao).execute();
        return asyncTask.get();
    }

    public Cliente SelectClienteByClienteId(int id_cliente) throws ExecutionException, InterruptedException {
        AsyncTask<Integer, Void, Cliente> asyncTask = new selectClienteByClienteIdAsyncTask(mClienteDao);
        Cliente cliente = asyncTask.execute(id_cliente).get();
        return cliente;
    }

    public void insert(List<Cliente> clientes) {
        Cliente[] clientesArray = new Cliente[clientes.size()];
        clientesArray = clientes.toArray(clientesArray);
        new insertAsyncTask(mClienteDao).execute(clientesArray);
    }

    public void deleteClienteByClienteId(int id_cliente) {
        new deleteClienteByClienteIdAsyncTask(mClienteDao).execute(id_cliente);
    }

    public void updateCliente(Cliente cliente) {
        new updateClienteIdAsyncTask(mClienteDao).execute(cliente);
    }


    public LiveData<List<Cliente>> getAllClientesBySearchParameters(String nombre, String apellido, String comentario) throws ExecutionException, InterruptedException {
        List<String> parametros = new ArrayList<String>();
        parametros.add(nombre);
        parametros.add(apellido);
        parametros.add(comentario);

        AsyncTask<List<String>, Void, LiveData<List<Cliente>>> asyncTask = new getAllClientesBySearchParametersAsyncTask(mClienteDao).execute(parametros);
        return asyncTask.get();

    }

    private static class insertAsyncTask extends AsyncTask<Cliente, Void, Void> {

        private ClienteDao mAsyncTaskDao;

        insertAsyncTask(ClienteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cliente... params) {
            mAsyncTaskDao.insertAll(params);
            return null;
        }
    }

    private static class getAllClientesAsyncTask extends AsyncTask<Void, Void, LiveData<List<Cliente>>> {

        private ClienteDao mAsyncTaskDao;

        getAllClientesAsyncTask(ClienteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<Cliente>> doInBackground(Void... voids) {
            LiveData<List<Cliente>> Cliente = mAsyncTaskDao.getAllClientes();
            return Cliente;
        }
    }

    private static class selectClienteByClienteIdAsyncTask extends AsyncTask<Integer, Void, Cliente> {

        private ClienteDao mAsyncTaskDao;

        selectClienteByClienteIdAsyncTask(ClienteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Cliente doInBackground(Integer... integers) {
            Cliente cliente = mAsyncTaskDao.SelectClienteByClienteId(integers[0]);
            return cliente;
        }
    }

    private static class deleteClienteByClienteIdAsyncTask extends AsyncTask<Integer, Void, Void> {

        private ClienteDao mAsyncTaskDao;

        deleteClienteByClienteIdAsyncTask(ClienteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.deleteClienteByClienteId(integers[0]);
            return null;
        }
    }
    private static class updateClienteIdAsyncTask extends AsyncTask<Cliente, Void, Void> {

        private ClienteDao mAsyncTaskDao;

        updateClienteIdAsyncTask(ClienteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cliente... params) {
            Integer id_cliente = params[0].getId_cliente();
            String nombre = params[0].getNombre();
            String apellido = params[0].getApellido();
            String email = params[0].getEmail();
            String comentario = params[0].getComentario();

            mAsyncTaskDao.update(params);

            // mAsyncTaskDao.updateCliente(id_cliente, nombre, apellido, email, comentario);
            return null;
        }
    }
    private static class getAllClientesBySearchParametersAsyncTask extends AsyncTask<List<String>, Void, LiveData<List<Cliente>>> {

        private ClienteDao mAsyncTaskDao;

        getAllClientesBySearchParametersAsyncTask(ClienteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<Cliente>> doInBackground(List<String>[] lists) {
            List<String> parametros = new ArrayList<String>();
            parametros = lists[0];
            String nombre = parametros.get(0);
            String apellido = parametros.get(1);
            String comentario = parametros.get(2);
            return mAsyncTaskDao.getAllClientesBySearchParameters(nombre, apellido, comentario);
        }
    }
}

