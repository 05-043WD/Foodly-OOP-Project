package com.student.Foodly.model;

// Module: Member 4 - Staff Management

/**
 * Staff.java
 *
 * Member 4 Module.
 * Extends AppEntity (Inheritance) for the 20 Marks.
 */
public class Staff extends AppEntity {

    // Encapsulation (private fields)
    private String email;
    private String role;
    private String shift;
    private double salary;

    public Staff() {
        super();
    }

    public Staff(String id, String name, String email, String role, String shift, double salary) {
        super(id, name);
        this.email = email;
        this.role = role;
        this.shift = shift;
        this.salary = salary;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public static Staff fromFileLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 6) {
            return new Staff(
                    parts[0], parts[1], parts[2], parts[3], parts[4],
                    Double.parseDouble(parts[5])
            );
        }
        return null;
    }

    @Override
    public String toFileLine() {
        return getId() + "|" + getName() + "|" + email + "|" + role + "|" + shift + "|" + salary;
    }
}
