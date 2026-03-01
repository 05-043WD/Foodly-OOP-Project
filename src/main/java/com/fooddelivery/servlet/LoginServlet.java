package com.fooddelivery.servlet;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String enteredEmail = request.getParameter("email");
        String enteredPassword = request.getParameter("password");

        // Locate the exact same file we wrote to earlier
        String userHome = System.getProperty("user.home");
        File file = new File(userHome + "/FoodDeliveryData", "users.txt");

        boolean loginSuccess = false;
        String loggedInUser = "";

        // The "Read" Operation
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                // Read the file line by line
                while ((line = reader.readLine()) != null) {
                    // Split the line by commas: [0] is Username, [1] is Email, [2] is Password
                    String[] userDetails = line.split(",");

                    // Check if the file data matches what the user typed in the HTML form
                    if (userDetails.length == 3 && userDetails[1].equals(enteredEmail) && userDetails[2].equals(enteredPassword)) {
                        loginSuccess = true;
                        loggedInUser = userDetails[0]; // Grab their username
                        break; // Stop reading the file, we found them!
                    }
                }
            }
        }

        // Direct the user based on whether we found a match
        if (loginSuccess) {
            // Send them to a success page (you can create dashboard.jsp later)
            response.getWriter().println("<h1>Welcome back, " + loggedInUser + "!</h1>");
        } else {
            // Send them back to login with an error message
            response.getWriter().println("<h1>Invalid Email or Password. Try again.</h1>");
        }
    }
}