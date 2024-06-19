<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Quizzenia</title>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body class="bg-gray-100 min-h-screen flex items-center justify-center">
        <div class="max-w-xl mx-auto bg-white p-8 rounded shadow-md w-full">
            <h1 class="text-3xl font-bold mb-4 text-center">Quiz Completed!</h1>
            <div class="mb-4">
                <p class="text-lg"><span class="font-bold">Quiz:</span> ${completedQuiz.name}</p>
                <p><span class="font-bold">Start At:</span> ${completedAttempt.startTime}</p>
                <p><span class="font-bold">End At:</span> ${completedAttempt.endTime}</p>
                <p><span class="font-bold">Time Taken:</span> ${completedAttempt.timeTakenMinute} minutes</p>
                <p><span class="font-bold">Score:</span> ${completedAttempt.marks} / ${completedQuiz.noOfQuestion}</p>
            </div>
            <a href="${pageContext.request.contextPath}/student" class="block text-center text-white bg-blue-500 hover:bg-blue-600 py-2 px-4 rounded">Back to Dashboard</a>
        </div>
    </body>
</html>
