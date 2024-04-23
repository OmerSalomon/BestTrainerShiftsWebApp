<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home - BestTrainerShifts</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #6e8efb, #a777e3);
            color: #333;
        }
        .main-content {
            padding: 40px 20px;
            text-align: center;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .logo {
            width: 200px; /* Adjust based on your logo size */
            margin-bottom: 30px;
        }
        .dashboard-link {
            background-color: #fff;
            color: #4CAF50;
            padding: 12px 20px;
            margin: 10px;
            border: none;
            display: inline-block;
            text-decoration: none;
            border-radius: 25px;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 4px 14px 0 rgba(0,0,0,.1);
        }
        .dashboard-link:hover {
            background-color: #4CAF50;
            color: #fff;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>

<div class="main-content">
    <img src="../../resources/logo_no_bg.png" alt="BestTrainerShifts Logo" class="logo">
    <h1>Welcome to BestTrainerShifts</h1>
    <p>Your centralized platform for trainer scheduling and management.</p>
    <a href="/trainermanager/trainer-dashboard" class="dashboard-link">Trainer Dashboard</a>
    <a href="/sectormanager/sector-dashboard" class="dashboard-link">Sector Dashboard</a>
    <a href="/algorithmmanager/algorithm-dashboard" class="dashboard-link">Algorithm Execution</a>
</div>

</body>
</html>
