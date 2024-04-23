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
            background: linear-gradient(135deg, #6e8efb, #a777e3);
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            flex-direction: column;
        }
        .container {
            max-width: 600px;
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
        input[type=text], input[type=number], select, input[type=submit] {
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
        .section {
            margin-bottom: 20px;
        }
        .properties {
            display: flex; /* Aligns items in a row */
            align-items: center; /* Aligns items vertically */
            margin-bottom: 20px;
        }
        .properties > * {
            margin-right: 10px; /* Adds spacing between items */
        }
        .property-label {
            display: flex;
            align-items: center;
        }
        .bold {
            font-weight: bold;
            margin-right: 10px; /* Ensures the label is clearly separated from input */
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
