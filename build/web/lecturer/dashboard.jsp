<%@page import="model.User"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.RecentActivity" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quizzenia - Lecturer Dashboard</title>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body>
        <div>
            <%@include file="/components/navbar.jsp" %>
        </div>
        <div class="container mx-auto p-8">
            <h1 class="text-3xl font-bold mb-4">Lecturer Dashboard</h1>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <!-- Student List Card -->
                <div class="bg-white space-between rounded-lg shadow-md">
                    <div class="p-6 flex justify-between items-center border-b">
                        <h2 class="text-xl font-semibold mb-2">Students</h2>
                        <a href="${pageContext.request.contextPath}/lecturer/student/new" class="inline-block px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition duration-300">+ New Student</a>
                    </div>
                    <div class="overflow-x-auto">
                        <table class="w-full table-auto">
                            <thead class="bg-gray-50">
                                <tr>
                                    <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                                    <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
                                    <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Class</th>
                                </tr>
                            </thead>
                            <tbody class="bg-white divide-y divide-gray-200">
                                <c:forEach var="student" items="${students}">
                                    <tr>
                                        <td class="px-4 py-2 font-semibold">${student.name}</td>
                                        <td class="px-4 py-2">
                                            <a 
                                                class="hover:underline hover:text-indigo-600" 
                                                href="mailto:${student.email}"
                                                >
                                                ${student.email}
                                            </a>
                                        </td>
                                        <td class="px-4 py-2">${student.classId}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Quizzes Card -->
                <div class="bg-white  rounded-lg shadow-md">
                    <div class="p-6 flex justify-between items-center border-b">
                        <h2 class="text-xl font-semibold mb-2">Quizzes</h2>
                        <a href="${pageContext.request.contextPath}/lecturer/quiz/new" class="inline-block px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition duration-300">+ New Quiz</a>
                    </div>
                    <div class="overflow-x-auto">
                        <table class="w-full table-auto">
                            <thead class="bg-gray-50">
                                <tr>
                                    <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                                    <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Attempts</th>
                                    <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                                </tr>
                            </thead>
                            <tbody class="bg-white divide-y divide-gray-200">
                                <c:forEach var="quiz" items="${quizzes}">
                                    <tr>
                                        <td class="px-4 py-2 font-semibold">${quiz.name}</td>
                                        <td class="px-4 py-2">${quiz.attempts}</td>
                                        <td class="px-4 py-2">${quiz.status}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="mt-6">
                <h2 class="text-2xl font-bold mb-2">Recent Activities</h2>
                <ul class="bg-white rounded-lg shadow-md divide-y divide-gray-200">
                    <c:forEach var="recentActivity" items="${recentActivities}">
                        <li class="px-6 py-4">
                            <p class="text-gray-700">${recentActivity.action}</p>
                            <p class="text-gray-500">${recentActivity.timestamp}</p>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </body>
</html>
