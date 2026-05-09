package com.student.Foodly.service;

// Module: Member 2 - Food Menu Management

import com.student.Foodly.model.FoodItem;
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
 * FoodItemService.java
 * Member 2 Module.
 * Demonstrates manual File I/O algorithms (Scanner/PrintWriter) directly for 10 marks.
 * No Streams or Lambdas used.
 */
@Service
public class FoodItemService implements Manageable<FoodItem> {

    private final String DATA_PATH = "data/";
    private final String MENU_FILE = "menu.txt";

    private void ensureDirectoryExists() {
        File dir = new File(DATA_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * CREATE / ADD
     */
    @Override
    public void create(FoodItem item) {
        if (item.getId() == null || item.getId().isBlank()) {
            item.setId("FOOD" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        }
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, MENU_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            writer.println(item.toFileLine());
        } catch (Exception e) {
            System.err.println("Error writing to " + MENU_FILE + ": " + e.getMessage());
        }
    }

    /**
     * READ ALL
     */
    @Override
    public List<FoodItem> readAll() {
        List<FoodItem> items = new ArrayList<>();
        File file = new File(DATA_PATH, MENU_FILE);
        
        if (!file.exists()) {
            return items;
        }

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null && !line.trim().isEmpty()) {
                    FoodItem item = FoodItem.fromFileLine(line);
                    if (item != null) {
                        items.add(item);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading " + MENU_FILE + ": " + e.getMessage());
        }
        return items;
    }

    /**
     * UPDATE / EDIT
     */
    @Override
    public void update(FoodItem updated) {
        List<FoodItem> allItems = readAll();
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, MENU_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (FoodItem item : allItems) {
                if (item.getId().equals(updated.getId())) {
                    writer.println(updated.toFileLine());
                } else {
                    writer.println(item.toFileLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error updating " + MENU_FILE + ": " + e.getMessage());
        }
    }

    /**
     * DELETE
     */
    @Override
    public void delete(String id) {
        List<FoodItem> allItems = readAll();
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, MENU_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (FoodItem item : allItems) {
                if (!item.getId().equals(id)) {
                    writer.println(item.toFileLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error deleting from " + MENU_FILE + ": " + e.getMessage());
        }
    }

    public FoodItem findById(String id) {
        List<FoodItem> allItems = readAll();
        for (FoodItem item : allItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public List<FoodItem> findByCategory(String category) {
        List<FoodItem> result = new ArrayList<>();
        for (FoodItem item : readAll()) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                result.add(item);
            }
        }
        return result;
    }
}
