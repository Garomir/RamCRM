package ru.ramich.ramcrm.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DaoOrders {

    DbHelper dbHelper;
    SQLiteDatabase sdb;
    Context context;

    public DaoOrders(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void open(){
        sdb = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    //ADD Order
    public boolean addOrder(Order order){
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.PRODUCT_ID, order.getProductId());
            cv.put(Constants.CLIENT_ID, order.getClientId());
            cv.put(Constants.DATE_ORDERS, order.getCreationDate());

            long result = sdb.insert(Constants.TABLE_ORDERS, null, cv);
            if (result > 0) {
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //READ_FROM_DB
    public List<Order> getAllOrders(){
        List<Order> orders = new ArrayList<>();
        String[] columns = {Constants.ID_ORDERS, Constants.PRODUCT_ID, Constants.CLIENT_ID, Constants.DATE_ORDERS};

        Cursor c = sdb.query(
                Constants.TABLE_ORDERS,
                columns,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(Constants.ID_ORDERS));
            int productId = c.getInt(c.getColumnIndex(Constants.PRODUCT_ID));
            int clientId = c.getInt(c.getColumnIndex(Constants.CLIENT_ID));
            String date = c.getString(c.getColumnIndex(Constants.DATE_ORDERS));

            orders.add(new Order(id, productId, clientId, date));
        }
        c.close();
        return orders;
    }

    //Delete From DB
    public void deleteOrder(int id){
        sdb.delete(Constants.TABLE_ORDERS, Constants.ID_ORDERS + "=?", new String[]{String.valueOf(id)});
    }

    public void dropOrders(){
        sdb.execSQL(Constants.DROP_ORDERS);
    }
    public void createOrders(){
        sdb.execSQL(Constants.CREATE_ORDERS);
    }
}
