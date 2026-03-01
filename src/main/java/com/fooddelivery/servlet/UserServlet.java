package com.fooddelivery.servlet;

import com.fooddelivery.model.User;
import java.io.File;
import java.io.FileWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. Get the data from the HTML form
            String userParam = request.getParameter("username");
            String emailParam = request.getParameter("email");
            String passParam = request.getParameter("password");

            // 2. Create the OOP Object
            User newUser = new User(userParam, emailParam, passParam);

            // 3. Find the folder and file
            String folderPath = System.getProperty("user.home") + "/FoodDeliveryData";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdir(); // Make the folder if it is missing
            }
            File myFile = new File(folder, "users.txt");

            // 4. Write to the file ('true' means add to the bottom, don't overwrite)
            FileWriter writer = new FileWriter(myFile, true);
            writer.write(newUser.getUsername() + "," + newUser.getEmail() + "," + newUser.getPassword() + "\n");
            writer.close(); // Always close it!

            // 5. Go back to login
            response.sendRedirect("login.jsp");

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}