package com.example.lenovo.proyectoaplicacionesmoviles.db.dbCliente;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lenovo.proyectoaplicacionesmoviles.db.AppDatabase;

import java.util.List;

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

    LiveData<List<Cliente>> getAllClientes() {
        return mClienteDao.getAllClientes();
    }

    public Cliente SelectClienteByClienteId(int id_cliente){
        return mClienteDao.SelectClienteByClienteId(id_cliente);
    }

    public void insert (List<Cliente> clientes) {
        Cliente[] clientesArray = new Cliente[clientes.size()];
        clientesArray = clientes.toArray(clientesArray);
        new insertAsyncTask(mClienteDao).execute(clientesArray);
    }
    public void deleteClienteByClienteId(int id_cliente){
        mClienteDao.deleteClienteByClienteId(id_cliente);
    }
    public void updateCliente(Cliente cliente){
        mClienteDao.updateCliente(cliente);
    }

    public LiveData<List<Cliente>> getAllClientesBySearchParameters(String nombre, String apellido, String comentario) {
        return mClienteDao.getAllClientesBySearchParameters(nombre, apellido, comentario);
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

}
