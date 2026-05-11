package com.student.Foodly.model;

// Module: Member 5 - Promos Management

/**
 * Promo.java
 *
 * Member 5 Module.
 * OOP Concept: INHERITANCE is demonstrated here as Promo extends AppEntity
 * to inherit 'id' and 'name' fields, fulfilling the OOP criteria.
 */
public class Promo extends AppEntity {

    // OOP Concept: ENCAPSULATION using private properties
    private String promoCode;
    private double discountPercentage;
    private boolean isActive;

    public Promo() {
        super();
    }

    public Promo(String id, String name, String promoCode, double discountPercentage, boolean isActive) {
        super(id, name);
        this.promoCode = promoCode;
        this.discountPercentage = discountPercentage;
        this.isActive = isActive;
    }

    // Getters and Setters
    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Decoding string line back to Object.
     */
    public static Promo fromFileLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 5) {
            Promo promo = new Promo();
            promo.setId(parts[0]);
            promo.setName(parts[1]);
            promo.setPromoCode(parts[2]);
            promo.setDiscountPercentage(Double.parseDouble(parts[3]));
            promo.setActive(Boolean.parseBoolean(parts[4]));
            return promo;
        }
        return null;
    }

    /**
     * Saving to File I/O. (Abstract method implementation from AppEntity)
     */
    @Override
    public String toFileLine() {
        // String concatenation for plain text storage
        return getId() + "|" + getName() + "|" + getPromoCode() + "|" + getDiscountPercentage() + "|" + isActive();
    }
}
