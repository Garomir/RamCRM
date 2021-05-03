package ru.ramich.ramcrm.model;

public class Constants {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "ramcrm.db";

    public static final String TABLE_PRODUCTS = "products";
    public static final String ID_PRODUCTS = "_id";
    public static final String NAME_PRODUCTS = "name";
    public static final String COST_PRODUCTS = "cost";

    public static final String TABLE_ORDERS = "orders";
    public static final String ID_ORDERS = "_id";
    public static final String PRODUCT_ID_ORDERS = "product_id";
    public static final String DATE_ORDERS = "datetime_order";

    public static final String CREATE_PRODUCTS = "create table "
            + TABLE_PRODUCTS + " ( "
            + ID_PRODUCTS + " integer primary key autoincrement, "
            + NAME_PRODUCTS + " text not null, "
            + COST_PRODUCTS + " integer not null" + ");";

    public static final String CREATE_ORDERS = "create table "
            + TABLE_ORDERS + " ( "
            + ID_ORDERS + " integer primary key autoincrement, "
            + PRODUCT_ID_ORDERS + " integer not null, "
            + DATE_ORDERS + " text not null" + ");";

    public static final String DROP_PRODUCTS = "drop table if exists " + TABLE_PRODUCTS + ";";
    public static final String DROP_ORDERS = "drop table if exists " + TABLE_ORDERS + ";";
}
