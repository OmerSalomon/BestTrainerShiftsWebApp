<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../../resources/navbar.css">
    <title>Algorithm Dashboard</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(0.25turn, #2a5d78, #f4f9f9, #ff7e67); /* Updated colors for a cooler background */
            color: #333;
        }

        .container {
            max-width: 900px;
            margin: auto;
            background-color: #ffffff; /* Lighter background for the container */
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1); /* Softer shadow */
        }
        h2, h3 {
            background-color: #4a8da8; /* Smoother, less stark yellow */
            color: #ffffff; /* White text for better contrast */
            text-align: center;
            padding: 10px 0; /* Added padding for better spacing */
            margin-top: 20px; /* Added space above headers */
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
            background-color: #ff7e67; /* Harmonizing with the gradient end color */
            color: white;
            cursor: pointer;
        }
        input[type=submit]:hover {
            background-color: #e66a56; /* Darker shade on hover */
            color: #ffffff;
        }
        .scrollable-list {
            background-color: #f9f9f9; /* Lighter background for readability */
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
        }
        tr:nth-child(even) {background-color: #e0f4fd;} /* Lighter, less intrusive row color */
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
            margin-right: 10px;
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
    <a href="${pageContext.request.contextPath}/">Home</a>
    <a href="${pageContext.request.contextPath}/trainermanager/trainer-dashboard">Trainer Dashboard</a>
    <a href="${pageContext.request.contextPath}/sectormanager/sector-dashboard">Sector Dashboard</a>
    <a class="active" href="${pageContext.request.contextPath}/algorithmmanager/algorithm-dashboard">Algorithm Execution</a>
</div>

<div class="container">
    <h2>Algorithm Dashboard</h2>

    <h3>Sectors</h3>
        <div class="scrollable-list">
            <table border="3">
                <tr>
                    <th>Name</th>
                    <th>Morning Shift</th>
                    <th>Noon Shift</th>
                    <th>Evening Shift</th>
                </tr>
                <c:forEach items="${sectors}" var="sector">
                    <tr>
                        <td>${sector.name}</td>
                        <td>${sector.morningShiftSize}</td>
                        <td>${sector.noonShiftSize}</td>
                        <td>${sector.eveningShiftSize}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    <h3>Trainer List</h3>
    <div class="scrollable-list">

        <table border="3">
            <tr>
                <th>Name</th>
                <th>Availability</th>
                <th>Manager</th>
            </tr>
            <c:forEach items="${trainers}" var="trainer">
                <tr>
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

    <form action="${pageContext.request.contextPath}/algorithmmanager/execute-algorithm" method="post">
        <input type="submit" value="Execute Algorithm">
    </form>


    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

</div>
</body>
</html>
