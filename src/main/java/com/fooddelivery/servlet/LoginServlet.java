package com.fooddelivery.servlet;

import java.io.File;
import java.util.Scanner;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String enteredEmail = request.getParameter("email");
            String enteredPassword = request.getParameter("password");

            File myFile = new File(System.getProperty("user.home") + "/FoodDeliveryData/users.txt");

            boolean foundUser = false;
            String foundName = "";

            // Use a basic Scanner to read the file
            if (myFile.exists()) {
                Scanner scanner = new Scanner(myFile);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] words = line.split(","); // Break the line into 3 pieces at the commas

                    String savedName = words[0];
                    String savedEmail = words[1];
                    String savedPassword = words[2];

                    // Check if email and password match
                    if (savedEmail.equals(enteredEmail) && savedPassword.equals(enteredPassword)) {
                        foundUser = true;
                        foundName = savedName;
                        break; // Stop looking, we found them
                    }
                }
                scanner.close(); // Always close the scanner
            }

            // Decide where to send them
            if (foundUser == true) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedEmail", enteredEmail);
                session.setAttribute("loggedName", foundName);
                response.sendRedirect("dashboard.jsp");
            } else {
                response.sendRedirect("login.jsp");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}