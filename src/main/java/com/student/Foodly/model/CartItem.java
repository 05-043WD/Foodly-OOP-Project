package com.student.Foodly.model;


public class CartItem {

    private String foodId;
    private String name;
    private double price;
    private int quantity;

    public CartItem() {}

    public CartItem(String foodId, String name, double price, int quantity) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getFoodId() { return foodId; }
    public void setFoodId(String foodId) { this.foodId = foodId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getSubTotal() {
        return this.price * this.quantity;
    }
}
