<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Sector</title>
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
    <h2>Edit Sector</h2>
    <form action="updateSector" method="post">
        <input type="hidden" name="id" value="${sector.id}">
        <div class="section">
            <label for="name" class="bold">Name:</label>
            <input type="text" id="name" name="name" value="${sector.name}" required>
        </div>

        <!-- Replace single trainer field with three shift sizes -->
        <div class="section">
            <label for="morningShiftSize" class="bold">Morning Shift Size:</label>
            <input type="number" id="morningShiftSize" name="morningShiftSize" step="1" min="0" value="${sector.morningShiftSize}" required>
        </div>
        <div class="section">
            <label for="noonShiftSize" class="bold">Noon Shift Size:</label>
            <input type="number" id="noonShiftSize" name="noonShiftSize" step="1" min="0" value="${sector.noonShiftSize}" required>
        </div>
        <div class="section">
            <label for="eveningShiftSize" class="bold">Evening Shift Size:</label>
            <input type="number" id="eveningShiftSize" name="eveningShiftSize" step="1" min="0" value="${sector.eveningShiftSize}" required>
        </div>

        <input type="submit" value="Update Sector">
    </form>
</div>

</body>
</html>
