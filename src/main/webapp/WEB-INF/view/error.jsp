<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #36393f;
            color: #ffffff;
            padding: 20px;
        }
        .error-container {
            background-color: #2f3136;
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
        }
        h2 {
            color: #e74c3c;
        }
        .back-to-signin {
            display: inline-block;
            background-color: #7289da;
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }
        .back-to-signin:hover {
            background-color: #5b6eae;
            text-decoration: none;
        }
        p{
        color: white;
        }
    </style>
</head>
<body>

<div class="error-container">
    <h2>An Error Occurred</h2>
    <p>${errorMessage}</p>
    <a href="${pageContext.request.contextPath}/">Back to Sign In</a>

</div>

</body>
</html>
