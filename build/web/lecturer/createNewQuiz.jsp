<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quizzenia - New Quiz</title>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body>
        <div>
            <%@include file="/components/navbar.jsp" %>
        </div>
        <div class="container mx-auto p-8">
            <h2 class="text-2xl font-bold mb-8">New Quiz</h2>
            <form id="newQuizForm" action="${pageContext.request.contextPath}/lecturer/quiz/new" method="POST" onsubmit="return validateNewQuizForm()" class="space-y-4">
                <fieldset>
                    <legend class="text-lg font-medium mb-4">Quiz Details</legend>
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div>
                            <label for="name" class="block text-sm font-medium text-gray-700">Name</label>
                            <input type="text" id="name" name="name" placeholder="Enter quiz name" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500" required>
                        </div>
                        <div>
                            <label for="noOfQuestion" class="block text-sm font-medium text-gray-700">Number Of Questions</label>
                            <input type="number" id="noOfQuestion" name="noOfQuestion" placeholder="Enter the number of questions" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500" required>
                        </div>
                        <div class="md:col-span-2">
                            <label for="description" class="block text-sm font-medium text-gray-700">Quiz Description</label>
                            <input type="text" id="description" name="description" placeholder="Enter quiz description" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500" required>
                        </div>
                        <div>
                            <label for="startTime" class="block text-sm font-medium text-gray-700">Start Time</label>
                            <input type="datetime-local" id="startTime" name="startTime" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500" required>
                        </div>
                        <div>
                            <label for="endTime" class="block text-sm font-medium text-gray-700">End Time</label>
                            <input type="datetime-local" id="endTime" name="endTime" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500" required>
                        </div>
                        <div>
                            <input type="hidden" id="classId" name="classId" value="${user.classId}" />
                            <input type="hidden" id="userId" name="userId" value="${user.id}" >
                        </div>
                    </div>
                </fieldset>
                <div class="mt-4">
                    <button type="submit" class="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600">Create New Quiz</button>
                </div>
            </form>
        </div>

        <script>
            function validateNewQuizForm() {
                var name = document.getElementById("name").value.trim();
                var noOfQuestion = document.getElementById("noOfQuestion").value.trim();
                var description = document.getElementById("description").value.trim();
                var startTime = document.getElementById("startTime").value;
                var endTime = document.getElementById("endTime").value;
                var errorElement = document.getElementById("error");

                // Clear previous error message
                errorElement.innerText = "";

                // Basic checks for empty fields
                if (name === "" || noOfQuestion === "" || description === "" || startTime === "" || endTime === "") {
                    errorElement.innerText = "All fields are required.";
                    return false;
                }

                // Validate number of questions
                if (isNaN(noOfQuestion) || parseInt(noOfQuestion) <= 0) {
                    errorElement.innerText = "Number of questions must be a positive number.";
                    return false;
                }

                // Validate start time and end time
                var startDateTime = new Date(startTime);
                var endDateTime = new Date(endTime);

                if (endDateTime <= startDateTime) {
                    errorElement.innerText = "End time must be after start time.";
                    document.getElementById("newQuizForm").submit.disabled = true; // Disable submit button
                    return false;
                }

                // Enable submit button if validation passes
                document.getElementById("newQuizForm").submit.disabled = false;

                return true;
            }
        </script>
    </body>
</html>
