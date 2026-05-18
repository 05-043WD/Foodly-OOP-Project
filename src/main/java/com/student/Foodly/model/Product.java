package com.student.Foodly.model;

public abstract class Product {

    private String id;
    private String name;
    private String description;
    private double price;

        public Product() {}

        public Product(String id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

        public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Product{id='" + id + "', name='" + name + "', price=" + price + "}";
    }
}
