<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #6e8efb, #a777e3);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-size: 18px; /* Base font size increased for larger overall text */
            color: #333;
        }
        .container {
            background-color: #F6F6F6;
            padding: 40px; /* Increased padding */
            border-radius: 5px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2); /* Enhanced shadow for depth */
            height: 430px;
            width: 100%;
            max-width: 450px; /* Larger form width */
            font-size: 1.1rem; /* Slightly larger font size within the container */
        }
        h2 {
            text-align: center;
            color: #333;
            font-size: 2rem; /* Larger heading */
            margin-bottom: 30px; /* Added more space below heading */
        }
        input[type="text"],
        input[type="password"],
        input[type="submit"] {
            width: 100%;
            padding: 15px 20px; /* Increased padding for larger fields and button */
            margin: 15px 0 20px; /* Increased spacing */
            display: block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 1.1rem; /* Larger font size for inputs */
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
            border-radius: 4px;
            margin-bottom: 25px; /* More space before the sign-up prompt */
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .signup-prompt {
            text-align: center;
            margin-top: 20px;
            font-size: 0.9rem; /* Smaller font size for the sign-up prompt */
        }
        .signup-link {
            color: #4CAF50;
            text-decoration: none;
        }
        .signup-link:hover {
            text-decoration: underline;
        }
        .error-message {
            color: red;
            text-align: center;
            margin-top: 20px;
            font-size: 1.1rem; /* Increased font size for readability */
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Login to BestTrainerShifts</h2>
    <form action="${pageContext.request.contextPath}/login" method="POST">
        <div>Username: <input type="text" name="username" required maxlength="45"></div>
        <div>Password: <input type="password" name="password" required maxlength="50"></div>
        <div><input type="submit" value="Login"></div>
    </form>
    <div class="signup-prompt">
        If you don't have an account, <a href="${pageContext.request.contextPath}/signup" class="signup-link">sign up here</a>.
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>
</div>

</body>
</html>
