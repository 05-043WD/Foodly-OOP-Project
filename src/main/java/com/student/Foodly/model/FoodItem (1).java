package com.student.Foodly.model;

// Module: Member 2 - Food Menu Management

/**
 * Demonstrates Inheritance: FoodItem extends Product (base class).
 * Encapsulation: all fields are private with public getters/setters.
 */
public class FoodItem extends AppEntity {

    // OOP Concept: ENCAPSULATION
    private String description;
    private double price;
    private String category;
    private String imageUrl;
    private boolean available;

    // Default constructor
    public FoodItem() {
        super();
        this.available = true;
    }

    // Full parameterized constructor
    public FoodItem(String id, String name, String description, double price,
                    String category, String imageUrl, boolean available) {
        super(id, name); // OOP Concept: INHERITANCE
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.available = available;
    }

    // Encapsulation: getters and setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // Encapsulation: getters and setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    /**
     * Serialize to pipe-delimited string for file storage.
     * Format: id|name|description|price|category|imageUrl|available
     */
    @Override
    public String toFileLine() {
        return getId() + "|" + getName() + "|" + description + "|"
                + price + "|" + category + "|" + imageUrl + "|" + available;
    }

    /**
     * Deserialize from a pipe-delimited file line.
     */
    public static FoodItem fromFileLine(String line) {
        if (line == null || line.isBlank()) return null;
        String[] parts = line.split("\\|", -1);
        if (parts.length < 7) return null;
        return new FoodItem(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                Double.parseDouble(parts[3].trim()),
                parts[4].trim(),
                parts[5].trim(),
                Boolean.parseBoolean(parts[6].trim())
        );
    }

    @Override
    public String toString() {
        return "FoodItem{id='" + getId() + "', name='" + getName()
                + "', category='" + category + "', price=" + price + "}";
    }
}
