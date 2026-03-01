<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Security check: If there is no session, kick them back to login
    if (session.getAttribute("loggedEmail") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Dashboard - Foodly</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #ff7e5f 0%, #feb47b 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', sans-serif;
        }
        .dashboard-card {
            background: white;
            padding: 3rem;
            border-radius: 20px;
            box-shadow: 0 15px 30px rgba(0,0,0,0.2);
            text-align: center;
            width: 100%;
            max-width: 500px;
        }
    </style>
</head>
<body>
<div class="dashboard-card">
    <h2 style="color: #ff4757; font-weight: 800;">🍔 Foodly Dashboard</h2>

    <h4 class="mt-4 mb-4">Welcome back, <%= session.getAttribute("loggedName") %>!</h4>

    <p class="text-muted">Logged in as: <%= session.getAttribute("loggedEmail") %></p>

    <hr class="my-4">

    <form action="${pageContext.request.contextPath}/DeleteUserServlet" method="POST" onsubmit="return confirm('Are you sure you want to permanently delete your account?');">
        <button type="submit" class="btn btn-outline-danger w-100 py-2 fw-bold">🗑️ Delete My Account</button>
    </form>
</div>
</body>
</html>