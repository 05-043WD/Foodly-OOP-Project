package com.student.Foodly.model;

/**
 * AppEntity.java
 *
 * This is the Master Base Class.
 * OOP Concept: INHERITANCE will be demonstrated as all other entity classes 
 * (User, FoodItem, Order, Staff, Promo, Review) will extend this base class.
 * 
 * OOP Concept: ABSTRACTION is demonstrated because this class is abstract 
 * and provides a shared blueprint.
 */
public abstract class AppEntity {

    // OOP Concept: ENCAPSULATION with private fields
    private String id;
    private String name;

    // Default constructor
    public AppEntity() {
    }

    // Parameterized constructor
    public AppEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters (Encapsulation)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Converts the entity to a comma-separated String for saving in .txt files.
     * All child classes must implement this to ensure File I/O works properly.
     */
    public abstract String toFileLine();
}
