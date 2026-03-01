<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login - Food Delivery</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* This creates the full-screen warm gradient background */
        body {
            background: linear-gradient(135deg, #ff7e5f 0%, #feb47b 100%);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        /* This styles the white box holding the form */
        .login-card {
            background: white;
            padding: 2.5rem;
            border-radius: 20px;
            box-shadow: 0 15px 30px rgba(0,0,0,0.2);
            width: 100%;
            max-width: 420px;
        }
        /* Custom styling for the main button */
        .btn-food {
            background-color: #ff4757;
            color: white;
            border: none;
            border-radius: 8px;
        }
        .btn-food:hover {
            background-color: #ff6b81;
            color: white;
        }
        .form-control {
            border-radius: 8px;
        }
    </style>
</head>
<body>

<div class="login-card">
    <h2 class="text-center mb-1" style="color: #ff4757; font-weight: 800;">🍔 Foodly</h2>
    <p class="text-center text-muted mb-4">Welcome back! Please sign in.</p>

    <form action="${pageContext.request.contextPath}/LoginServlet" method="POST">
        <div class="mb-3">
            <label class="form-label fw-bold text-secondary">Email Address</label>
            <input type="email" name="email" class="form-control" placeholder="name@example.com" required>
        </div>
        <div class="mb-4">
            <label class="form-label fw-bold text-secondary">Password</label>
            <input type="password" name="password" class="form-control" placeholder="••••••••" required>
        </div>
        <button type="submit" class="btn btn-food w-100 py-2 fw-bold fs-5">Login</button>
    </form>

    <div class="text-center mt-4">
        <small class="text-muted">Don't have an account? <a href="register.jsp" style="color: #ff4757; font-weight: bold; text-decoration: none;">Register here</a></small>
    </div>
</div>

</body>
</html>