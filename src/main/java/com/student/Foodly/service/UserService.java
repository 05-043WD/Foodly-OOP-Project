package com.student.Foodly.service;

// Module: Member 1 - User Management

import com.student.Foodly.model.User;
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
 * UserService.java
 * Member 1 Module.
 * Demonstrates manual File I/O algorithms (Scanner/PrintWriter) directly for 10 marks.
 * No Streams or Lambdas used.
 */
@Service
public class UserService implements Manageable<User> {

    private final String DATA_PATH = "data/";
    private final String USERS_FILE = "users.txt";

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
    public void create(User user) {
        if (user.getId() == null || user.getId().isBlank()) {
            user.setId("USR" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        }
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, USERS_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            writer.println(user.toFileLine());
        } catch (Exception e) {
            System.err.println("Error writing to " + USERS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * READ ALL
     */
    @Override
    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        File file = new File(DATA_PATH, USERS_FILE);
        
        if (!file.exists()) {
            return users;
        }

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null && !line.trim().isEmpty()) {
                    User user = User.fromFileLine(line);
                    if (user != null) {
                        users.add(user);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading " + USERS_FILE + ": " + e.getMessage());
        }
        return users;
    }

    /**
     * UPDATE / EDIT
     */
    @Override
    public void update(User updated) {
        List<User> allUsers = readAll();
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, USERS_FILE);
        
        // Completely overwrite the file with the new data
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (User user : allUsers) {
                if (user.getId().equals(updated.getId())) {
                    writer.println(updated.toFileLine());
                } else {
                    writer.println(user.toFileLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error updating " + USERS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * DELETE
     */
    @Override
    public void delete(String id) {
        List<User> allUsers = readAll();
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, USERS_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (User user : allUsers) {
                if (!user.getId().equals(id)) {
                    writer.println(user.toFileLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error deleting from " + USERS_FILE + ": " + e.getMessage());
        }
    }

    public User findById(String id) {
        List<User> allUsers = readAll();
        for (User user : allUsers) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public User findByEmail(String email) {
        List<User> allUsers = readAll();
        for (User user : allUsers) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public User loginCheck(String email, String password) {
        File file = new File(DATA_PATH, USERS_FILE);
        if (!file.exists()) return null;

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) continue;
                
                User user = User.fromFileLine(line.trim());
                if (user != null && user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                    return user;
                }
            }
        } catch (Exception e) {
            System.err.println("Error during login check: " + e.getMessage());
        }
        return null;
    }
}
