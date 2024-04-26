<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Guard Schedule by Sector</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f0f0f0;
        }
        table {
            margin-bottom: 20px;
        }
    </style>
</head>
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
        <c:forEach begin="0" end="6" var="day">
            <tr>
                <td>Day ${day + 1}</td>
                    <%-- Loop over shifts for this day, 3 shifts per day --%>
                <c:forEach begin="0" end="2" var="shift">
                    <td>
                            <%-- Collecting guards for each shift --%>
                        <c:forEach var="guard" items="${trainers}" varStatus="trainerStatus">
                            <c:if test="${scheduleMatrix[trainerStatus.index][day * 3 + shift] == sectorStatus.index}">
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
</body>
</html>
