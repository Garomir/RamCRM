package ru.ramich.ramcrm.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DaoProducts {

    DbHelper dbHelper;
    SQLiteDatabase sdb;
    Context context;

    public DaoProducts(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void open(){
        sdb = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    //ADD PRODUCT
    public boolean addProduct(Product product){
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.NAME_PRODUCTS, product.getName());
            cv.put(Constants.COST_PRODUCTS, product.getCost());

            long result = sdb.insert(Constants.TABLE_PRODUCTS, null, cv);
            if (result > 0) {
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //READ_FROM_DB
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        String[] columns = {Constants.ID_PRODUCTS, Constants.NAME_PRODUCTS, Constants.COST_PRODUCTS};

        Cursor c = sdb.query(
                Constants.TABLE_PRODUCTS,
                columns,
                null,
                null,
                null,
                null,
                null);

        while (c.moveToNext()){
            int id = c.getInt(c.getColumnIndex(Constants.ID_PRODUCTS));
            String name = c.getString(c.getColumnIndex(Constants.NAME_PRODUCTS));
            int cost = c.getInt(c.getColumnIndex(Constants.COST_PRODUCTS));

            products.add(new Product(id, name, cost));
        }
        c.close();
        return products;
    }

    public void dropProducts(){
        sdb.execSQL(Constants.DROP_PRODUCTS);
    }
    public void createProducts(){
        sdb.execSQL(Constants.CREATE_PRODUCTS);
    }
}
