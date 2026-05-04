package com.student.Foodly.model;

// Module: Member 6 - Review Moderation

/**
 * Model class representing a user Review for Foodly.
 */
public class Review extends AppEntity {
    
    // Encapsulation: Private properties
    private String reviewText;
    private int rating;

    // Default constructor
    public Review() {
        super();
    }

    // Parameterized constructor
    public Review(String id, String authorName, String reviewText, int rating) {
        super(id, authorName); // OOP Concept: INHERITANCE
        this.reviewText = reviewText;
        this.rating = rating;
    }

    // Getters and Setters for legacy compat (wraps AppEntity name)
    public String getAuthorName() { return getName(); }
    public void setAuthorName(String authorName) { setName(authorName); }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    /**
     * Serializes review to a pipe-delimited string for file storage.
     */
    @Override
    public String toFileLine() {
        return getId() + "|" + getName() + "|" + reviewText + "|" + rating;
    }

    /**
     * Deserializes a pipe-delimited line back into a Review object.
     */
    public static Review fromFileLine(String line) {
        if (line == null || line.isBlank()) return null;
        String[] parts = line.split("\\|", -1);
        if (parts.length < 4) return null;
        
        return new Review(
            parts[0].trim(),
            parts[1].trim(),
            parts[2].trim(),
            Integer.parseInt(parts[3].trim())
        );
    }
}
