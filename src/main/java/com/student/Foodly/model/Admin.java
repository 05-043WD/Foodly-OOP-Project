package com.student.Foodly.model;

public class Admin extends User {

        public Admin() {
        super();
        this.setRole("ADMIN");
    }

        public Admin(String id, String name, String email, String phone, String address, String password) {
        super(id, name, email, phone, address, "ADMIN", password);
    }
    
    // Admin specific method (Example for Viva)
    public boolean canManageMenu() {
        return true;
    }
}
