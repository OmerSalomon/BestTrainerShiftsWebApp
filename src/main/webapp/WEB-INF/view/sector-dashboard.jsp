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
            background: linear-gradient(0.25turn, #2a5d78, #f4f9f9, #ff7e67); /* Consistent gradient theme */
            color: #333;
        }
        .container {
            max-width: 900px;
            margin: auto;
            background-color: #ffffff; /* Light background for container */
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1); /* Softer shadow */
        }
        h2, h3 {
            background-color: #4a8da8; /* Consistent header color */
            color: #ffffff; /* White text for better contrast */
            text-align: center;
            padding: 10px; /* Enhanced padding */
            border-radius: 5px; /* Rounded corners for headers */
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
            background-color: #ff7e67; /* Button color consistent with theme */
            color: white;
            cursor: pointer;
            width: auto; /* Auto width for buttons */

        }
        input[type=submit]:hover {
            background-color: #e66a56; /* Darker shade on hover */
            color: #ffffff;
        }
        .scrollable-list {
            background-color: #f9f9f9; /* Lighter shade for better readability */
            max-height: 500px;
            overflow-y: auto;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-shadow: inset 0 0 10px rgba(0,0,0,0.05); /* Lighter inset shadow */
        }
        .section {
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            margin-bottom: 20px;
        }
        th, td {
            text-align: left;
            padding: 8px;
            border-radius: 3px; /* Slight rounding on table cells */
        }
        tr:nth-child(even) {background-color: #e0f4fd;} /* Consistent row coloring */
        .properties {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }
        .properties > * {
            margin-right: 10px;
        }
        .bold {
            font-weight: bold;
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
