package com.student.Foodly.model;

// Module: Member 1 - User Management

/**
 * User model demonstrating Encapsulation (OOP).
 * All fields are private, accessed only via public getters/setters.
 */
public class User extends AppEntity {

    // OOP Concept: ENCAPSULATION with private fields
    private String email;
    private String phone;
    private String address;
    private String role; // USER or ADMIN
    private String password;

    // Default constructor
    public User() {
        this.role = "USER";
    }

    // Parameterized constructor
    public User(String id, String name, String email, String phone, String address, String role, String password) {
        super(id, name); // OOP Concept: INHERITANCE
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.password = password;
    }

    // Encapsulation: private fields with public getters/setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    /**
     * Serialize to pipe-delimited string for file storage.
     * Format: id|name|email|phone|address|role|password
     */
    public String toFileLine() {
        // Handle null password for backward compatibility
        String pwd = (password == null) ? "" : password;
        return getId() + "|" + getName() + "|" + email + "|" + phone + "|" + address + "|" + role + "|" + pwd;
    }

    /**
     * Deserialize from a pipe-delimited file line.
     */
    public static User fromFileLine(String line) {
        if (line == null || line.isBlank()) return null;
        String[] parts = line.split("\\|", -1);
        if (parts.length < 6) return null;
        
        String pwd = (parts.length > 6) ? parts[6].trim() : "";
        
        return new User(
                parts[0].trim(),
                parts[1].trim(),
                parts[2].trim(),
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim(),
                pwd
        );
    }

    @Override
    public String toString() {
        return "User{id='" + getId() + "', name='" + getName() + "', email='" + email + "', role='" + role + "'}";
    }
}
