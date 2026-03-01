<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register - Food Delivery</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Exact same background as the login page */
        body {
            background: linear-gradient(135deg, #ff7e5f 0%, #feb47b 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            padding: 20px 0; /* A little padding so it doesn't touch the top/bottom on small screens */
        }
        /* The floating white card */
        .login-card {
            background: white;
            padding: 2.5rem;
            border-radius: 20px;
            box-shadow: 0 15px 30px rgba(0,0,0,0.2);
            width: 100%;
            max-width: 450px;
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
    <p class="text-center text-muted mb-4">Create your new account</p>

    <form action="${pageContext.request.contextPath}/UserServlet" method="POST">
        <div class="mb-3">
            <label class="form-label fw-bold text-secondary">Username</label>
            <input type="text" name="username" class="form-control" placeholder="e.g. JohnDoe" required>
        </div>
        <div class="mb-3">
            <label class="form-label fw-bold text-secondary">Email Address</label>
            <input type="email" name="email" class="form-control" placeholder="name@example.com" required>
        </div>
        <div class="mb-4">
            <label class="form-label fw-bold text-secondary">Password</label>
            <input type="password" name="password" class="form-control" placeholder="Create a strong password" required>
        </div>

        <button type="submit" class="btn btn-food w-100 py-2 fw-bold fs-5">Register Account</button>
    </form>

    <div class="text-center mt-4">
        <small class="text-muted">Already have an account? <a href="login.jsp" style="color: #ff4757; font-weight: bold; text-decoration: none;">Login here</a></small>
    </div>
</div>

</body>
</html>