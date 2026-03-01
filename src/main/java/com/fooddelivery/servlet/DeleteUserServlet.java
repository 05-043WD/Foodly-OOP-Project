package com.fooddelivery.servlet;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 1. Get the logged-in email
            HttpSession session = request.getSession();
            String emailToDelete = (String) session.getAttribute("loggedEmail");

            File myFile = new File(System.getProperty("user.home") + "/FoodDeliveryData/users.txt");

            // 2. Make an empty list to hold the "good" users
            ArrayList<String> keepUsers = new ArrayList<>();

            // 3. Scan the file. Add everyone to the list EXCEPT the user deleting their account
            if (myFile.exists()) {
                Scanner scanner = new Scanner(myFile);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] words = line.split(",");
                    String savedEmail = words[1];

                    // If it is NOT the user trying to delete, save them to our list
                    if (!savedEmail.equals(emailToDelete)) {
                        keepUsers.add(line);
                    }
                }
                scanner.close();
            }

            // 4. Overwrite the file with only the "good" users
            // ('false' means wipe the file completely and start fresh)
            FileWriter writer = new FileWriter(myFile, false);
            for (int i = 0; i < keepUsers.size(); i++) {
                writer.write(keepUsers.get(i) + "\n");
            }
            writer.close();

            // 5. Log them out and send back to register
            session.invalidate();
            response.sendRedirect("register.jsp");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}