package com.student.Foodly.service;

// Module: Member 5 - Promos Management

import com.student.Foodly.model.Promo;
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
 * PromoService.java
 * Member 5 Module.
 * Demonstrates manual File I/O algorithms (Scanner/PrintWriter) directly for 10 marks.
 * No Streams or Lambdas used.
 */
@Service
public class PromoService {

    private final String DATA_PATH = "data/";
    private final String PROMOS_FILE = "promos.txt";

    private void ensureDirectoryExists() {
        File dir = new File(DATA_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 1. CREATE Operation
     */
    public void createPromo(Promo promo) {
        if (promo.getId() == null || promo.getId().isEmpty()) {
            promo.setId("PRM-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        }
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, PROMOS_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            writer.println(promo.toFileLine());
        } catch (Exception e) {
            System.err.println("Error writing to " + PROMOS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * 2. READ Operation
     */
    public List<Promo> getAllPromos() {
        List<Promo> promos = new ArrayList<>();
        File file = new File(DATA_PATH, PROMOS_FILE);
        
        if (!file.exists()) {
            return promos;
        }

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null && !line.trim().isEmpty()) {
                    Promo promo = Promo.fromFileLine(line);
                    if (promo != null) {
                        promos.add(promo);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading " + PROMOS_FILE + ": " + e.getMessage());
        }
        return promos;
    }

    /**
     * 3. UPDATE Operation
     */
    public void updatePromo(Promo updatedPromo) {
        List<Promo> allPromos = getAllPromos();
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, PROMOS_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (Promo promo : allPromos) {
                if (promo.getId().equals(updatedPromo.getId())) {
                    writer.println(updatedPromo.toFileLine());
                } else {
                    writer.println(promo.toFileLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error updating " + PROMOS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * 4. DELETE Operation
     */
    public void deletePromo(String promoId) {
        List<Promo> allPromos = getAllPromos();
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, PROMOS_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (Promo promo : allPromos) {
                if (!promo.getId().equals(promoId)) {
                    writer.println(promo.toFileLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error deleting from " + PROMOS_FILE + ": " + e.getMessage());
        }
    }
}
