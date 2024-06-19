<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quizzenia</title>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
        <style>
            body {
                font-family: 'Arial', sans-serif;
                background-color: #f0f0f0;
            }
            .answer-box {
                cursor: pointer;
            }
        </style>
    </head>
    <body class="bg-gray-100">
        <div class="max-w-lg mx-auto mt-8 bg-white p-6 rounded-lg shadow-md">
            <h1 class="text-2xl font-bold text-center mb-4">${currentQuestion.questionText}</h1>
            <div class="text-center mb-4 text-gray-600">Question ${questionIndex} / ${totalQuestions}</div>
            <div id="countdown" class="text-center mb-4 text-red-600 font-bold"></div>
            <form action="${pageContext.request.contextPath}/student/quiz/attempt/${currentAttempt.id}" method="post">
                <c:forEach var="answer" items="${answers}">
                    <div class="answer-box p-4 mb-2 rounded-lg border border-gray-200 hover:border-blue-400 transition duration-300">
                        <label class="flex items-center">
                            <input type="radio" name="answer" value="${answer.id}" class="mr-2">
                            ${answer.answerText}
                        </label>
                    </div>
                </c:forEach>
                <div class="flex justify-center">
                    <button type="submit" class="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition duration-300">Submit Answer</button>
                </div>
            </form>
        </div>

        <script>
            // Retrieve endTime from currentQuiz passed from servlet
            var endTime = new Date("${currentQuiz.endTime}").getTime();

            // Update the count down every 1 second
            var x = setInterval(function () {
                // Get today's date and time
                var now = new Date().getTime();

                // Find the distance between now and the end time
                var distance = endTime - now;

                // Time calculations for days, hours, minutes and seconds
                var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                var seconds = Math.floor((distance % (1000 * 60)) / 1000);

                // Display the remaining time
                document.getElementById("countdown").innerHTML = "Time Remaining: " + minutes + "m " + seconds + "s ";

                // If the countdown is finished, redirect or handle as needed
                if (distance < 0) {
                    clearInterval(x);
                    document.getElementById("countdown").innerHTML = "Time's up!";
                    window.location.href = '${pageContext.request.contextPath}/student/quiz/attempt/completed/${currentAttempt.id}';
                }
            }, 1000);
        </script>

    </body>
</html>
