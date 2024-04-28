<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(0.25turn, #2a5d78, #f4f9f9, #ff7e67); /* Updated colors for a cooler background */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-size: 18px; /* Base font size increased for larger overall text */
            color: #333;
        }
        .container {
            background-color: #ffffff; /* Consistent light background for the container */
            padding: 40px;
            border-radius: 5px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1); /* Softer shadow */
            height: auto; /* Adjusted for variable content height */
            width: 100%;
            max-width: 450px;
            font-size: 1.1rem;
        }
        h2 {
            text-align: center;
            color: #333;
            font-size: 2rem;
            margin-bottom: 30px;
        }
        input[type="text"],
        input[type="password"],
        input[type="submit"] {
            width: 100%;
            padding: 15px 20px;
            margin: 15px 0 20px;
            display: block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 1.1rem;
        }
        input[type="submit"] {
            background-color: #ff7e67; /* Harmonizing with the gradient end color */
            color: white;
            cursor: pointer;
            border-radius: 4px;
            margin-bottom: 25px;
        }
        input[type="submit"]:hover {
            background-color: #e66a56; /* Darker shade on hover */
        }
        .signup-prompt {
            text-align: center;
            margin-top: 20px;
            font-size: 0.9rem;
        }
        .signup-link {
            color: #4a8da8; /* Link color adjusted to fit theme */
            text-decoration: none;
        }
        .signup-link:hover {
            text-decoration: underline;
            color: #2a5d78; /* Darker shade for hover to maintain consistency */
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
