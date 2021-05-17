package ru.ramich.ramcrm.model;

import java.util.Date;

public class Order {

    private int id;
    private String productName;
    private int productCost;
    private String clientName;
    private String clientPhone;
    private String creationDate;

    public Order() {
    }

    public Order(String productName, int productCost, String clientName, String clientPhone, String creationDate) {
        this.productName = productName;
        this.productCost = productCost;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.creationDate = creationDate;
    }

    public Order(int id, String productName, int productCost, String clientName, String clientPhone, String creationDate) {
        this.id = id;
        this.productName = productName;
        this.productCost = productCost;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCost() {
        return productCost;
    }

    public void setProductCost(int productCost) {
        this.productCost = productCost;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }
}
