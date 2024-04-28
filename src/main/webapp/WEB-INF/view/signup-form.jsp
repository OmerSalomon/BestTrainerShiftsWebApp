<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Signup</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(0.25turn, #3f87a6, #ebf8e1, #f69d3c);            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-size: 18px; /* Base font size increased for larger overall text */
            color: #333;
        }
        .container {
            background-color: #F6F6F6;
            padding: 40px;
            border-radius: 5px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2);
            height: 430px;
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
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
            border-radius: 4px;
            margin-bottom: 25px;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .signin-prompt {
            text-align: center;
            margin-top: 20px;
            font-size: 0.9rem;
        }
        .signin-link {
            color: #4CAF50;
            text-decoration: none;
        }
        .signin-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Sign Up for BestTrainerShifts</h2>
    <form action="${pageContext.request.contextPath}/signup" method="post">
        <div>Username: <input type="text" name="username" required maxlength="45"></div>
        <div>Password: <input type="password" name="password" required maxlength="50"></div>
        <div><input type="submit" value="Sign Up"></div>
    </form>
    <div class="signin-prompt">
        Already have an account? <a href="${pageContext.request.contextPath}/" class="signin-link">Sign in here</a>.
    </div>
</div>

</body>
</html>
