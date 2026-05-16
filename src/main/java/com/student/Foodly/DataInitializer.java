package com.student.Foodly;

import com.student.Foodly.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * DataInitializer.java
 * Runs on application startup — ensures all 6 data files exist and are seeded.
 * Member-specific demo data is provided so each module dashboard shows real records.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private FileService fileService;

    @Override
    public void run(String... args) {
        // Initialize users.txt, menu.txt, reviews.txt via FileService
        fileService.initDataFiles();

        try {
            String dataPath = "data/";
            Path dir = Paths.get(dataPath);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            // ── Member 3: Orders ──────────────────────────────────────────
            // Module: Member 3
            Path ordersFile = dir.resolve("orders.txt");
            if (!Files.exists(ordersFile)) {
                String seedOrders =
                    "ORD-001|2x Classic Burger|john@example.com|17.98|Pending\n" +
                    "ORD-002|1x Margherita Pizza|alice@test.com|12.49|Delivered\n";
                Files.writeString(ordersFile, seedOrders, StandardOpenOption.CREATE);
            }

            // ── Member 4: Staff ───────────────────────────────────────────
            // Module: Member 4
            Path staffFile = dir.resolve("staff.txt");
            if (!Files.exists(staffFile)) {
                String seedStaff =
                    "STF-001|Maria Gomez|maria@foodly.com|Head Chef|Morning|3500.0\n" +
                    "STF-002|James Lee|james@foodly.com|Delivery Driver|Evening|2200.0\n";
                Files.writeString(staffFile, seedStaff, StandardOpenOption.CREATE);
            }

            // ── Member 5: Promos ──────────────────────────────────────────
            // Module: Member 5
            Path promosFile = dir.resolve("promos.txt");
            if (!Files.exists(promosFile)) {
                String seedPromos =
                    "PRM-001|Student Discount|STUDENT15|15.0|true\n" +
                    "PRM-002|Weekend Special|WEEKEND10|10.0|false\n";
                Files.writeString(promosFile, seedPromos, StandardOpenOption.CREATE);
            }

            System.out.println("✅ Foodly: All 6 data files initialized successfully.");
        } catch (Exception e) {
            System.err.println("❌ DataInitializer error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

