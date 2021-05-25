package ru.ramich.ramcrm.model;

public class Constants {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "ramcrm.db";

    public static final String TABLE_PRODUCTS = "products";
    public static final String ID_PRODUCTS = "_id";
    public static final String NAME_PRODUCTS = "name";
    public static final String COST_PRODUCTS = "cost";
    public static final String IMAGEPATH_PRODUCTS = "image_path";

    public static final String TABLE_ORDERS = "orders";
    public static final String ID_ORDERS = "_id";
    /*public static final String PRODUCT_ID = "product_id";
    public static final String CLIENT_ID = "client_id";*/
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_COST = "product_cost";
    public static final String CLIENT_NAME = "client_name";
    public static final String CLIENT_PHONE = "client_phone";
    public static final String DATE_ORDERS = "date_order";

    public static final String TABLE_CLIENTS = "clients";
    public static final String ID_CLIENTS = "_id";
    public static final String NAME_CLIENTS = "name";
    public static final String PHONE_CLIENTS = "phone";

    public static final String CREATE_PRODUCTS = "create table "
            + TABLE_PRODUCTS + " ( "
            + ID_PRODUCTS + " integer primary key autoincrement, "
            + NAME_PRODUCTS + " text not null, "
            + COST_PRODUCTS + " integer not null, "
            + IMAGEPATH_PRODUCTS + " text" + ");";

    public static final String CREATE_ORDERS = "create table "
            + TABLE_ORDERS + " ( "
            + ID_ORDERS + " integer primary key autoincrement, "
            /*+ PRODUCT_ID + " integer not null, "
            + CLIENT_ID + " integer not null, "*/
            + PRODUCT_NAME + " text not null,"
            + PRODUCT_COST + " integer not null, "
            + CLIENT_NAME + " text not null,"
            + CLIENT_PHONE + " text not null,"
            + DATE_ORDERS + " text not null" + ");";

    public static final String CREATE_CLIENTS = "create table "
            + TABLE_CLIENTS + " ( "
            + ID_CLIENTS + " integer primary key autoincrement, "
            + NAME_CLIENTS + " text not null, "
            + PHONE_CLIENTS + " text not null" + ");";

    public static final String DROP_PRODUCTS = "drop table if exists " + TABLE_PRODUCTS + ";";
    public static final String DROP_ORDERS = "drop table if exists " + TABLE_ORDERS + ";";
    public static final String DROP_CLIENTS = "drop table if exists " + TABLE_CLIENTS + ";";
}
