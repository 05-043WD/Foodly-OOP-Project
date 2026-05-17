package com.student.Foodly.service;

// Module: Member 6 - Review Moderation

import com.student.Foodly.model.Review;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Service to manage Reviews within reviews.txt
 * Member 6 Module.
 * Demonstrates manual File I/O algorithms. No update method as per ethics requirement.
 */
@Service
public class ReviewService implements Manageable<Review> {

    private final String DATA_PATH = "data/";
    private final String REVIEWS_FILE = "reviews.txt";

    private void ensureDirectoryExists() {
        File dir = new File(DATA_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void create(Review review) {
        if (review.getId() == null || review.getId().isBlank()) {
            review.setId("REV" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        }
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, REVIEWS_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            writer.println(review.toFileLine());
        } catch (Exception e) {
            System.err.println("Error writing to " + REVIEWS_FILE + ": " + e.getMessage());
        }
    }

    @Override
    public List<Review> readAll() {
        List<Review> reviews = new ArrayList<>();
        File file = new File(DATA_PATH, REVIEWS_FILE);
        
        if (!file.exists()) {
            return reviews;
        }

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null && !line.trim().isEmpty()) {
                    Review review = Review.fromFileLine(line);
                    if (review != null) {
                        reviews.add(review);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading " + REVIEWS_FILE + ": " + e.getMessage());
        }
        return reviews;
    }

    @Override
    public void update(Review updated) {
        // Disabled for ethics. Reviews should not be edited by admin, only deleted.
        throw new UnsupportedOperationException("Editing reviews is disabled for ethical moderation.");
    }

    @Override
    public void delete(String id) {
        List<Review> allReviews = readAll();
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, REVIEWS_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (Review review : allReviews) {
                if (!review.getId().equals(id)) {
                    writer.println(review.toFileLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error deleting from " + REVIEWS_FILE + ": " + e.getMessage());
        }
    }
}
