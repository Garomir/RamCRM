package ru.ramich.ramcrm.model;

public class Product {

    private int id;
    private String name;
    private int cost;
    private String imagePath;

    public Product() {
    }

    public Product(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public Product(String name, int cost, String imagePath) {
        this.name = name;
        this.cost = cost;
        this.imagePath = imagePath;
    }

    public Product(int id, String name, int cost, String imagePath) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
