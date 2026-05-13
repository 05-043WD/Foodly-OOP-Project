package com.student.Foodly.service;

// Module: Member 4 - Staff Management

import com.student.Foodly.model.Staff;
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
 * StaffService.java
 * Member 4 Module.
 * Demonstrates manual File I/O algorithms (Scanner/PrintWriter) directly for 10 marks.
 * No Streams or Lambdas used.
 */
@Service
public class StaffService {

    private final String DATA_PATH = "data/";
    private final String STAFF_FILE = "staff.txt";

    private void ensureDirectoryExists() {
        File dir = new File(DATA_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // CREATE
    public void createStaff(Staff staff) {
        if (staff.getId() == null || staff.getId().isEmpty()) {
            staff.setId("STF-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        }
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, STAFF_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            writer.println(staff.toFileLine());
        } catch (Exception e) {
            System.err.println("Error writing to " + STAFF_FILE + ": " + e.getMessage());
        }
    }

    // READ
    public List<Staff> getAllStaff() {
        List<Staff> list = new ArrayList<>();
        File file = new File(DATA_PATH, STAFF_FILE);
        
        if (!file.exists()) {
            return list;
        }

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null && !line.trim().isEmpty()) {
                    Staff s = Staff.fromFileLine(line);
                    if (s != null) {
                        list.add(s);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading " + STAFF_FILE + ": " + e.getMessage());
        }
        return list;
    }

    // UPDATE
    public void updateStaff(Staff updatedStaff) {
        List<Staff> allStaff = getAllStaff();
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, STAFF_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (Staff s : allStaff) {
                if (s.getId().equals(updatedStaff.getId())) {
                    writer.println(updatedStaff.toFileLine());
                } else {
                    writer.println(s.toFileLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error updating " + STAFF_FILE + ": " + e.getMessage());
        }
    }

    // DELETE
    public void deleteStaff(String id) {
        List<Staff> allStaff = getAllStaff();
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, STAFF_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (Staff s : allStaff) {
                if (!s.getId().equals(id)) {
                    writer.println(s.toFileLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error deleting from " + STAFF_FILE + ": " + e.getMessage());
        }
    }
}
