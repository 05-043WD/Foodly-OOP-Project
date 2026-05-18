package com.student.Foodly.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Value("${foodly.data.path:data/}")
    private String dataPath;

    public void initDataFiles() {
        try {
            Path dir = Paths.get(dataPath);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            Path usersFile = dir.resolve("users.txt");
            if (!Files.exists(usersFile)) {
                // making dummy users
                // data matching the format
                String seedUsers =
                    "USR001|John Doe|john@example.com|555-0100|123 Maple St|USER|password123\n" +
                    "USR002|Admin User|admin@foodly.com|555-0999|Foodly HQ|ADMIN|admin123\n";
                Files.writeString(usersFile, seedUsers, StandardOpenOption.CREATE);
            }

            Path menuFile = dir.resolve("menu.txt");
            if (!Files.exists(menuFile)) {
                // making dummy food items
                String seed =
                    "FOOD001|Classic Burger|Juicy beef patty with fresh lettuce, tomato & cheese|8.99|Burgers|https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400|true\n" +
                    "FOOD002|Margherita Pizza|Stone-baked pizza with San Marzano tomato & mozzarella|12.49|Pizza|https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400|true\n" +
                    "FOOD003|Salmon Sushi Roll|Fresh Atlantic salmon over seasoned rice, 8 pieces|14.99|Sushi|https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=400|true\n" +
                    "FOOD004|Grilled Chicken Pasta|Penne with grilled chicken breast in creamy Alfredo sauce|11.49|Pasta|https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=400|true\n" +
                    "FOOD005|Veggie Tacos|Three soft tacos with seasoned veggies, salsa & guacamole|9.99|Mexican|https://images.unsplash.com/photo-1565299585323-38d6b0865b47?w=400|true\n" +
                    "FOOD006|Chocolate Lava Cake|Warm chocolate cake with a gooey molten centre & vanilla ice cream|6.99|Desserts|https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=400|true\n";
                Files.writeString(menuFile, seed, StandardOpenOption.CREATE);
            }
            
            Path reviewsFile = dir.resolve("reviews.txt");
            if (!Files.exists(reviewsFile)) {
                // making dummy reviews
                String seedReviews =
                    "REV001|Alice Smith|The classic burger was amazing! Highly recommend.|5\n" +
                    "REV002|Bob Johnson|Delivery was a bit late but the pizza was still hot.|4\n" +
                    "REV003|Charlie Davis|Best sushi I've ever had in the area!|5\n";
                Files.writeString(reviewsFile, seedReviews, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize data files: " + e.getMessage(), e);
        }
    }

    public List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        Path filePath = Paths.get(dataPath, fileName);
        if (!Files.exists(filePath)) return lines;
        
        try (java.util.Scanner scanner = new java.util.Scanner(filePath.toFile())) {
            // loop through each line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isBlank()) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file " + fileName + ": " + e.getMessage(), e);
        }
        return lines;
    }

    public void writeLines(String fileName, List<String> lines) {
        Path filePath = Paths.get(dataPath, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file " + fileName + ": " + e.getMessage(), e);
        }
    }

    public void appendLine(String fileName, String line) {
        Path filePath = Paths.get(dataPath, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error appending to file " + fileName + ": " + e.getMessage(), e);
        }
    }
}
