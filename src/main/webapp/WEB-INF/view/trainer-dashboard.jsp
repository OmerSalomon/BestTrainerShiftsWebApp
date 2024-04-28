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
            background: linear-gradient(0.25turn, #3f87a6, #ebf8e1, #f69d3c);            color: #333;
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

    <h2>Trainers</h2>

    <!-- Add Trainer Form -->
    <h3>Add Trainer</h3>
    <form action="addTrainer" method="post">
        <div class="section">
            <label class="bold">Name:</label>
            <input type="text" name="name" required maxlength="30">
        </div>
        <div class="section">
            <label class="bold">Availability:</label>
            <table>
                <tr>
                    <th>Day / Shift</th>
                    <th>Morning</th>
                    <th>Noon</th>
                    <th>Night</th>
                </tr>
                <c:forEach var="day" items="${['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']}" varStatus="dayStatus">
                    <tr>
                        <td><c:out value="${day}"/></td>
                        <td><input type="checkbox" name="availability[${dayStatus.index}][0]"></td>
                        <td><input type="checkbox" name="availability[${dayStatus.index}][1]"></td>
                        <td><input type="checkbox" name="availability[${dayStatus.index}][2]"></td>
                    </tr>
                </c:forEach>

            </table>
        </div>

        <div class="section properties">
            <span class="bold">Properties:</span>
            <div class="property-label">
                <input type="checkbox" id="isManager" name="isManager">
                <label for="isManager">Manager</label>
            </div>
        </div>
        <input type="submit" value="Add Trainer">
    </form>

    <!-- Trainer List -->
    <h3>Trainer List</h3>
    <form action="selectedTrainer" method="post">
        <div class="scrollable-list">

            <table border="3">
                <tr>
                    <th>Select</th>
                    <th>Name</th>
                    <th>Availability</th>
                    <th>Manager</th>
                </tr>
                <c:forEach items="${trainers}" var="trainer">
                    <tr>
                        <td><input type="radio" name="selectedTrainerId" value="${trainer.id}" required></td>
                        <td>${trainer.name}</td>
                        <td>
                            <table border="1">
                                <tr>
                                    <th>Day / Shift</th>
                                    <th>Morning</th>
                                    <th>Noon</th>
                                    <th>Night</th>
                                </tr>
                                <c:forEach var="dayIndex" begin="0" end="6">
                                    <tr>
                                        <td>${['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'][dayIndex]}</td>
                                        <c:forEach var="shiftIndex" begin="0" end="2">
                                            <td>
                                                <c:choose>
                                                    <c:when test="${fn:substring(trainer.availabilityString, dayIndex * 3 + shiftIndex, dayIndex * 3 + shiftIndex + 1) == '1'}">V</c:when>
                                                    <c:otherwise>&nbsp;</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                        <td>${trainer.isManager ? 'Yes' : 'No'}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <input type="submit" name="action" value="Edit">
        <input type="submit" name="action" value="Delete">
    </form>



    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
    </c:if>
</div>


</body>
</html>
