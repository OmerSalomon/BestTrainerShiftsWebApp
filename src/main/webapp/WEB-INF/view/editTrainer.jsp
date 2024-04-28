<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Trainer</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(0.25turn, #2a5d78, #f4f9f9, #ff7e67); /* Updated colors for a cooler background */
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            flex-direction: column;
        }
        .container {
            max-width: 600px;
            background-color: #ffffff; /* Consistent light background for the container */
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1); /* Softer shadow for a sleeker look */
        }

        h2, h3 {
            background-color: #4a8da8; /* Smooth, less stark heading background */
            color: #ffffff; /* White text for better contrast */
            text-align: center;
            padding: 10px; /* Added padding for better spacing */
            border-radius: 5px; /* Rounded corners for the headers */
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
        }
        .section {
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #ccc;
            border-collapse: collapse;
            width: 100%;
            text-align: left;
            padding: 8px;
        }
        tr:nth-child(even) {
            background-color: #e0f4fd; /* Lighter, less intrusive row color */
        }
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
    </style>

</head>
<body>

<div class="container">
    <h2>Edit Trainer</h2>
    <form action="updateTrainer" method="post">
        <input type="hidden" name="id" value="${trainer.id}">
        <div class="section">
            <label for="name" class="bold">Name:</label>
            <input type="text" id="name" name="name" value="${trainer.name}" required maxlength="30">
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
                <c:forEach items="${['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday']}" var="day" varStatus="dayStatus">
                    <tr>
                        <td>${day}</td>
                        <c:forEach begin="0" end="2" var="shift">
                            <td>
                                <input type="checkbox" name="availability[${dayStatus.index}][${shift}]"
                                    ${fn:substring(trainer.availabilityString, (dayStatus.index * 3) + shift, (dayStatus.index * 3) + shift + 1) == '1' ? 'checked' : ''}>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div class="properties">
            <label class="bold">Properties:</label>
            <div class="property-label">
                <input type="checkbox" id="isManager" name="isManager" ${trainer.isManager ? 'checked' : ''}>
                <label for="isManager">Manager</label>
            </div>
        </div>

        <input type="submit" value="Update Trainer">
    </form>
</div>

</body>
</html>
