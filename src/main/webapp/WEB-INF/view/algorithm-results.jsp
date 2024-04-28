<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Trainer Schedule by Sector</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 20px;
            background: linear-gradient(0.25turn, #2a5d78, #f4f9f9, #ff7e67); /* Consistent gradient theme */
            color: #333;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        h1, h2 {
            color: #4a8da8; /* Header color */
            text-align: center;
        }
        table {
            border-collapse: collapse;
            width: 90%; /* Adjusted width for better layout */
            margin-bottom: 20px;
            background-color: #ffffff; /* Light background for readability */
            box-shadow: 0 0 10px rgba(0,0,0,0.1); /* Soft shadow for depth */
        }
        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: left;
            color: #333;
        }
        th {
            background-color: #ffe062; /* Bright header color */
        }
        tr:nth-child(even) {
            background-color: #f4f9f9; /* Alternating row colors for better readability */
        }
        .container {
            max-width: 1200px; /* Container to center content and manage size */
            padding: 20px;
            background-color: #f6f6f6; /* Light container background */
            border-radius: 5px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2); /* Enhanced shadow for depth */
        }
    </style>
</head>
<h1>Sectors Schedule</h1>

<body>
<c:forEach var="sector" items="${sectors}" varStatus="sectorStatus">
    <h2>Sector: ${sector.name}</h2>
    <table>
        <thead>
        <tr>
            <th>Day</th>
            <th>Morning</th>
            <th>Noon</th>
            <th>Afternoon</th>
        </tr>
        </thead>
        <tbody>
            <%-- Loop over each day (assuming 7 days, 0-indexed) --%>
        <c:forEach var="dayIndex" begin="0" end="6">
            <tr>
                <td>${['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'][dayIndex]}</td>
                    <%-- Loop over shifts for this day, 3 shifts per day --%>
                <c:forEach begin="0" end="2" var="shift">
                    <td>
                            <%-- Collecting guards for each shift --%>
                        <c:forEach var="trainer" items="${trainers}" varStatus="trainerStatus">
                            <c:if test="${scheduleMatrix[trainerStatus.index][dayIndex * 3 + shift] == sectorStatus.index}">
                                ${trainer.name}<br>
                            </c:if>
                        </c:forEach>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:forEach>

<h1>Trainers Schedule</h1>

<c:forEach var="trainer" items="${trainers}" varStatus="trainerStatus">
    <h2>Trainer: ${trainer.name}</h2>
    <table>
        <thead>
        <tr>
            <th>Day</th>
            <th>Morning</th>
            <th>Noon</th>
            <th>Afternoon</th>
        </tr>
        </thead>
        <tbody>
            <%-- Loop over each day (assuming 7 days, 0-indexed) --%>
        <c:forEach var="dayIndex" begin="0" end="6">
            <tr>
                <td>${['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'][dayIndex]}</td>
                    <%-- Loop over shifts for this day, 3 shifts per day --%>
                <c:forEach begin="0" end="2" var="shift">
                    <td>
                            <%-- Collecting guards for each shift --%>
                        <c:if test="${scheduleMatrix[trainerStatus.index][dayIndex * 3 + shift] != -1}">
                            ${"V"}<br>
                        </c:if>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:forEach>

</body>
</html>