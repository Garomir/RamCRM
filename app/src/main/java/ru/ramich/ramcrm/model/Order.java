package ru.ramich.ramcrm.model;

import java.util.Date;

public class Order {

    private int id;
    private int productId;
    private int clientId;
    /*private String productName;
    private int productCost;
    private String clientName;
    private String clientPhone;*/
    private String creationDate;

    public Order() {
    }

    public Order(int productId, int clientId, String creationDate) {
        this.productId = productId;
        this.clientId = clientId;
        this.creationDate = creationDate;
    }

    public Order(int id, int productId, int clientId, String creationDate) {
        this.id = id;
        this.productId = productId;
        this.clientId = clientId;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
