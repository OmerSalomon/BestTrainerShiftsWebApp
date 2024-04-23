<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="../../resources/navbar.css">
    <title>Trainers Management</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #6e8efb, #a777e3);
            color: #333;
        }
        .container {
            max-width: 900px;
            margin: auto;
            background-color: #F6F6F6;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px #cccccc;
        }
        h2, h3 {
            color: #4CAF50;
            text-align: center;
        }
        form {
            padding: 20px;
            margin-bottom: 20px;
        }
        input[type=text], select, input[type=submit] {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type=submit] {
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
        }
        input[type=submit]:hover {
            background-color: #45a049;
        }
        .scrollable-list {
            background-color: #f0f0f0;
            max-height: 500px;
            overflow-y: auto;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-shadow: inset 0 0 10px #cccccc;
        }
        .section {
            margin-bottom: 20px; /* Increased spacing between sections */
        }
        table {
            width: 100%;
            margin-bottom: 20px;
        }
        th, td {
            text-align: left;
            padding: 8px;
        }
        tr:nth-child(even) {background-color: #f2f2f2;}
        .properties {
            display: flex;
            align-items: center;
            margin-bottom: 20px; /* Adjust as needed */
        }
        .properties > * {
            margin-right: 10px; /* Reduced spacing for closer alignment */
        }
        .bold {
            font-weight: bold;
            margin-right: 10px; /* Ensure bold labels also have adjusted spacing */
        }
        .property-label {
            display: flex;
            align-items: center;
        }
        .error-message {
            color: red;
            text-align: center;
            margin-top: 20px;
        }





    </style>
</head>
<body>



<div class="navbar">
    <a class="active" href="${pageContext.request.contextPath}/">Home</a>
    <a href="${pageContext.request.contextPath}/trainermanager/trainer-dashboard">Trainer Dashboard</a>
    <a href="${pageContext.request.contextPath}/sectormanager/sector-dashboard">Sector Dashboard</a>
    <a href="${pageContext.request.contextPath}/algorithmmanager/algorithm-dashboard">Algorithm Execution</a>
</div>


<div class="container">
    <h2>Sectors</h2>

    <!-- Add Sector Form -->
    <h3>Add Sector</h3>
    <form action="addSector" method="post">
        <div class="section">
            <label class="bold">Name:</label>
            <input type="text" name="name" required>
        </div>
        <div class="section">
            <label class="bold">Morning Shift Size:</label>
            <input type="number" step="1" min="0" name="morningShiftSize" required>
        </div>
        <div class="section">
            <label class="bold">Noon Shift Size:</label>
            <input type="number" step="1" min="0" name="noonShiftSize" required>
        </div>
        <div class="section">
            <label class="bold">Evening Shift Size:</label>
            <input type="number" step="1" min="0" name="eveningShiftSize" required>
        </div>
        <input type="submit" value="Add Sector">
    </form>


    <!-- Sector List -->
    <h3>Sector List</h3>
    <form action="selectedSector" method="post">
        <div class="scrollable-list">
            <table border="3">
                <tr>
                    <th>Select</th>
                    <th>Name</th>
                    <th>Morning Shift</th>
                    <th>Noon Shift</th>
                    <th>Evening Shift</th>
                </tr>
                <c:forEach items="${sectors}" var="sector">
                    <tr>
                        <td><input type="radio" name="selectedSectorId" value="${sector.id}" required></td>
                        <td>${sector.name}</td>
                        <td>${sector.morningShiftSize}</td>
                        <td>${sector.noonShiftSize}</td>
                        <td>${sector.eveningShiftSize}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <input type="submit" name="action" value="Edit">
        <input type="submit" name="action" value="Delete">
    </form>


    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>
</div>


</body>
</html>
