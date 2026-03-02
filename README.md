# 🍔 Foodly - Online Food Delivery Management System

## 📖 Project Overview
This is a web-based application developed for the SE1020 Object-Oriented Programming module. It allows users to manage and interact with a food delivery network, applying core OOP concepts and utilizing file handling for data storage.

## 🛠️ Tech Stack
* **Backend:** Java, JSP Servlets
* **Frontend:** HTML, CSS (Bootstrap 5)
* **Data Storage:** File Read/Write operations (.txt files)
* **Server:** Apache Tomcat 10+
* **Build Tool:** Maven

## 👥 Team Members & Modules
Our team consists of 6 members, each handling a specific component of the system:

| Member Name           | Student ID | Assigned Module |
|:----------------------| :--- | :--- |
| 1.Menuja K.G.M.       | IT25102298 | User Management |
| 2.Lakshana M.R.S.     | IT25103284 | Restaurant Management |
| 3.Dinujaya I. P.      | IT25103525 | Menu & Food Items |
| 4.Lakshani J.D.C.     | IT25101957 | Order & Cart System |
| 5.Abeywickrama K.R.D. | IT25101860 | Delivery & Tracking |
| 6.Samaraweera L S     | IT25100495 | Reviews & Ratings |

## 🚀 How to Run Locally
1. **Clone the repository:**
   ```bash
   https://github.com/05-043WD/Foodly-OOP-Project.git
2. Open the project in IntelliJ IDEA Ultimate.

3. Configure Tomcat: Add a local Apache Tomcat Server in your Run Configurations.

4. Deploy the Artifact: Ensure the war exploded artifact is deployed.

5. Run the server and navigate to http://localhost:8080/[Your_Context_Path]/login.jsp.

## 📂 Data Storage Note

All data is stored locally in .txt files. The system automatically generates a FoodDeliveryData folder in the user's home directory (e.g., C:\Users\Name\FoodDeliveryData) to prevent data loss during server restarts.
