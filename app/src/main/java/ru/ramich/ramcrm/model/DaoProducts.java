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
            cv.put(Constants.IMAGEPATH_PRODUCTS, product.getImagePath());

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
        String[] columns = {Constants.ID_PRODUCTS, Constants.NAME_PRODUCTS, Constants.COST_PRODUCTS, Constants.IMAGEPATH_PRODUCTS};

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
            String imagePath = c.getString(c.getColumnIndex(Constants.IMAGEPATH_PRODUCTS));

            products.add(new Product(id, name, cost, imagePath));
        }
        c.close();
        return products;
    }

    //Delete From DB
    public void deleteProduct(int id){
        sdb.delete(Constants.TABLE_PRODUCTS, Constants.ID_PRODUCTS + "=?", new String[]{String.valueOf(id)});
    }

    //Update Product
    public void updateProduct(Product product){
        ContentValues cv = new ContentValues();
        cv.put(Constants.NAME_PRODUCTS, product.getName());
        cv.put(Constants.COST_PRODUCTS, product.getCost());
        cv.put(Constants.IMAGEPATH_PRODUCTS, product.getImagePath());
        sdb.update(Constants.TABLE_PRODUCTS, cv, Constants.ID_PRODUCTS + "=?", new String[]{String.valueOf(product.getId())});
    }

    //Update image
    public void updateImage(String imagePath, int id){
        ContentValues cv = new ContentValues();
        cv.put(Constants.IMAGEPATH_PRODUCTS, imagePath);
        sdb.update(Constants.TABLE_PRODUCTS, cv, Constants.ID_PRODUCTS + "=?", new String[]{String.valueOf(id)});
    }

    /*public void dropProducts(){
        sdb.execSQL(Constants.DROP_PRODUCTS);
    }
    public void createProducts(){
        sdb.execSQL(Constants.CREATE_PRODUCTS);
    }*/
}
