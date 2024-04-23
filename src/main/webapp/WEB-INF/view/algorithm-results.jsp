<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Algorithm Results</title>
    <link rel="stylesheet" href="../../resources/style.css"> <!-- Ensure your styles are correctly linked -->
</head>
<body>
<div class="container">
    <h1>Allocation Results</h1>
    <c:forEach items="${allocationResults}" var="entry">
        <h2>Sector: ${entry.key.name}</h2>
        <c:forEach items="${entry.value}" var="dayEntry">
            <h3>${dayEntry.key}</h3> <!-- Day of the week -->
            <table>
                <tr>
                    <th>Shift</th>
                    <th>Trainers</th>
                </tr>
                <c:forEach items="${dayEntry.value}" varStatus="status" var="shift">
                    <tr>
                        <td>Shift ${status.index + 1}</td>
                        <td>
                            <c:forEach items="${shift}" var="trainer" varStatus="status">
                                ${trainer.name}<c:if test="${not status.last}">, </c:if>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:forEach>
    </c:forEach>
</div>
</body>
</html>
