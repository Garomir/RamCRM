package ru.ramich.ramcrm.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DaoClients {

    DbHelper dbHelper;
    SQLiteDatabase sdb;
    Context context;

    public DaoClients(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void open(){
        sdb = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    //ADD CLIENT
    public boolean addClient(Client client){
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME_CLIENTS, client.getName());
            cv.put(Constants.PHONE_CLIENTS, client.getPhone());

            long result = sdb.insert(Constants.TABLE_CLIENTS, null, cv);
            if (result > 0) {
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //READ_FROM_DB
    public List<Client> getAllClients(){
        List<Client> clients = new ArrayList<>();
        String[] columns = {Constants.ID_CLIENTS, Constants.NAME_CLIENTS, Constants.PHONE_CLIENTS};

        Cursor c = sdb.query(
                Constants.TABLE_CLIENTS,
                columns,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(Constants.ID_CLIENTS));
            String name = c.getString(c.getColumnIndex(Constants.NAME_CLIENTS));
            String phone = c.getString(c.getColumnIndex(Constants.PHONE_CLIENTS));

            clients.add(new Client(id, name, phone));
        }
        c.close();
        return clients;
    }

    //Delete From DB
    public void deleteClient(int id){
        sdb.delete(Constants.TABLE_CLIENTS, Constants.ID_CLIENTS + "=?", new String[]{String.valueOf(id)});
    }

    //Update Client
    public void updateClient(Client client){
        ContentValues cv = new ContentValues();
        cv.put(Constants.NAME_CLIENTS, client.getName());
        cv.put(Constants.PHONE_CLIENTS, client.getPhone());
        sdb.update(Constants.TABLE_CLIENTS, cv, Constants.ID_CLIENTS + "=?", new String[]{String.valueOf(client.getId())});
    }

    public void dropClients(){
        sdb.execSQL(Constants.DROP_CLIENTS);
    }

    public void createClients(){
        sdb.execSQL(Constants.CREATE_CLIENTS);
    }
}
