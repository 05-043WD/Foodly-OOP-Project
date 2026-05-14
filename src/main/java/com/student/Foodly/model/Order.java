package com.student.Foodly.model;


public class Order extends AppEntity {

        private String userEmail;
    private String deliveryAddress;
    private String itemsSummary;
    private double totalAmount;
    private String status;

    public Order() {
        super();
    }

    public Order(String id, String customerName, String userEmail, String deliveryAddress, String itemsSummary, double totalAmount, String status) {
        super(id, customerName); // sets the parent id and name
        this.userEmail = userEmail;
        this.deliveryAddress = deliveryAddress;
        this.itemsSummary = itemsSummary;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getters and Setters
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getItemsSummary() { return itemsSummary; }
    public void setItemsSummary(String itemsSummary) { this.itemsSummary = itemsSummary; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public static Order fromFileLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 7) {
            Order order = new Order();
            order.setId(parts[0]);
            order.setName(parts[1]); // setting the customer name
            order.setUserEmail(parts[2]);
            order.setDeliveryAddress(parts[3]);
            order.setItemsSummary(parts[4]);
            order.setTotalAmount(Double.parseDouble(parts[5]));
            order.setStatus(parts[6]);
            return order;
        }
        return null;
    }

    @Override
    public String toFileLine() {
        // ID | CustomerName | Email | Address | ItemsSummary | Total | Status
        return getId() + "|" + getName() + "|" + getUserEmail() + "|" + getDeliveryAddress() + "|" + getItemsSummary() + "|" + getTotalAmount() + "|" + getStatus();
    }
}
