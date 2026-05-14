package com.student.Foodly.service;


import com.student.Foodly.model.Order;
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

@Service
public class OrderService {

    private final String DATA_PATH = "data/";
    private final String ORDERS_FILE = "orders.txt";

    private void ensureDirectoryExists() {
        File dir = new File(DATA_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void createOrder(Order order) {
        if (order.getId() == null || order.getId().isEmpty()) {
            order.setId("ORD-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        }
        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            order.setStatus("Pending");
        }
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, ORDERS_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            writer.println(order.toFileLine());
        } catch (Exception e) {
            System.err.println("Error writing to " + ORDERS_FILE + ": " + e.getMessage());
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        File file = new File(DATA_PATH, ORDERS_FILE);
        
        if (!file.exists()) {
            return orders;
        }

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null && !line.trim().isEmpty()) {
                    Order order = Order.fromFileLine(line);
                    if (order != null) {
                        orders.add(order);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading " + ORDERS_FILE + ": " + e.getMessage());
        }
        return orders;
    }

    public void updateOrder(Order updatedOrder) {
        List<Order> allOrders = getAllOrders();
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, ORDERS_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (Order order : allOrders) {
                if (order.getId().equals(updatedOrder.getId())) {
                    writer.println(updatedOrder.toFileLine());
                } else {
                    writer.println(order.toFileLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error updating " + ORDERS_FILE + ": " + e.getMessage());
        }
    }

    public void deleteOrder(String orderId) {
        List<Order> allOrders = getAllOrders();
        
        ensureDirectoryExists();
        File file = new File(DATA_PATH, ORDERS_FILE);
        
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (Order order : allOrders) {
                if (!order.getId().equals(orderId)) {
                    writer.println(order.toFileLine());
                }
            }
        } catch (Exception e) {
            System.err.println("Error deleting from " + ORDERS_FILE + ": " + e.getMessage());
        }
    }

    public Order getOrderById(String orderId) {
        List<Order> allOrders = getAllOrders();
        for (Order order : allOrders) {
            if (order.getId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public List<Order> getOrdersByUser(String userEmail) {
        List<Order> allOrders = getAllOrders();
        List<Order> userOrders = new ArrayList<>();
        
        for (Order order : allOrders) {
            if (order.getUserEmail().equals(userEmail)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }
}
