<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%!
    public String formatDuration(int durationMinute) {
        int hours = durationMinute / 60;
        int minutes = durationMinute % 60;
        String formattedDuration = String.format("%02d hours %02d minutes", hours, minutes);
        return formattedDuration;
    }
%>  
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quizzenia</title>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body>
        <div>
            <%@include file="/components/navbar.jsp" %>
        </div>
        <div class="container mx-auto p-8">
            <div class="p-6 flex justify-between items-center">
                <h1 class="text-3xl font-bold mb-4">Quizzes for class K${user.classId}</h1>
                <a href="${pageContext.request.contextPath}/lecturer/quiz/new" class="inline-block px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition duration-300">+ New Quiz</a>
            </div>
            <table class="w-full border-separate border-spacing-2 border border-gray-800">
                <thead>
                    <tr>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">No</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Quiz</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Description</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">No of Questions</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Start Time</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">End Time</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Duration</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Status</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Attempts</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="quiz" items="${quizzes}" varStatus="loop">
                        <tr>
                            <td class="px-4 py-2 border border-gray-800">${loop.index + 1}</td>
                            <td class="px-4 py-2 border border-gray-800">${quiz.name}</td>
                            <td class="px-4 py-2 border border-gray-800">${quiz.description}</td>
                            <td class="px-4 py-2 border border-gray-800">${quiz.noOfQuestion}</td>
                            <td class="px-4 py-2 border border-gray-800">${quiz.startTime}</td>
                            <td class="px-4 py-2 border border-gray-800">${quiz.endTime}</td>
                            <td class="px-4 py-2 border border-gray-800"><%= formatDuration(((model.Quiz) pageContext.getAttribute("quiz")).getDurationMinutes())%></td>
                            <td class="px-4 py-2 border border-gray-800">${quiz.status}</td>
                            <td class="px-4 py-2 border border-gray-800">${quiz.attempts}</td>
                            <td class="px-4 py-2 border border-gray-800 justify-around">
                                <c:choose>
                                    <c:when test="${quiz.status == 'draft'}">
                                        <a href="${pageContext.request.contextPath}/lecturer/quiz/publish/${quiz.id}" class="block m-1 w-20 px-4 py-2 bg-yellow-800 text-white rounded-lg hover:bg-yellow-900 transition duration-300">Publish</a>
                                    </c:when>
                                    <c:when test="${quiz.status == 'published'}">
                                        <a href="${pageContext.request.contextPath}/lecturer/quiz/hide/${quiz.id}" class="block m-1 w-20 px-4 py-2 bg-yellow-800 text-white rounded-lg hover:bg-yellow-900 transition duration-300">Hide</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/lecturer/quiz/review/${quiz.id}" class="block m-1 w-20 px-4 py-2 bg-yellow-800 text-white rounded-lg hover:bg-yellow-900 transition duration-300">Review</a>
                                    </c:otherwise>
                                </c:choose>
                                <a href="${pageContext.request.contextPath}/lecturer/quiz/edit/${quiz.id}" class="block m-1 w-20 px-4 py-2 bg-purple-400 text-white rounded-lg hover:bg-purple-500 transition duration-300">Update</a>
                                <a href="${pageContext.request.contextPath}/lecturer/quiz/delete/${quiz.id}" class="block m-1 w-20 px-4 py-2 bg-pink-600 text-white rounded-lg hover:bg-pink-700 transition duration-300">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>

