package com.fooddelivery.servlet;

import com.fooddelivery.model.User;
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// This annotation links the HTML form to this specific Java file
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Grab what the user typed in the HTML form
        String userParam = request.getParameter("username");
        String emailParam = request.getParameter("email");
        String passParam = request.getParameter("password");

        // 2. Create the OOP User Object
        User newUser = new User(userParam, emailParam, passParam);

        // 3. File Handling: Create a permanent folder in the user's home directory
        String userHome = System.getProperty("user.home"); // Gets C:\Users\YourName automatically
        File dataFolder = new File(userHome, "FoodDeliveryData");

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File file = new File(dataFolder, "users.txt");

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println(newUser.getUsername() + "," + newUser.getEmail() + "," + newUser.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}