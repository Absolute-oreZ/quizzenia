<%@page import="util.DurationUtil"%>
<%@page import="java.util.List"%>
<%@page import="model.Quiz"%>
<%@page import="dao.QuizAttemptDAO"%>
<%@page import="model.User"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html;" pageEncoding="UTF-8" %>
<%!
    public String formatDuration(int durationMinute) {
        int hours = durationMinute / 60;
        int minutes = durationMinute % 60;
        String formattedDuration = String.format("%02d hours %02d minutes", hours, minutes);
        return formattedDuration;
    }
%>  
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quizzenia - Student Dashboard</title>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body>
        <nav style="background-color: #F2F1EB" class="border-b-2">
            <div class="max-w-screen-xl bg-slate-500 flex items-center justify-between mx-auto p-4">
                <a href="${pageContext.request.contextPath}" class="flex items-center space-x-3 rtl:space-x-reverse">
                    <img src="${pageContext.request.contextPath}/images/logo.png" class="h-8" alt="logo" />
                    <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">Quizzenia</span>
                </a>
                <div class="flex items-center">
                    <a href="${pageContext.request.contextPath}/profile"><img class="w-8 h-8 rounded-full" src="${pageContext.request.contextPath}/images/defaultProfile.png" alt="user photo"></a>
                    <a href="${pageContext.request.contextPath}/logout" class="block px-3 text-gray-900 rounded">Log Out</a>
                </div>
            </div>
        </nav>

        <div class="container mx-auto p-8">
            <h1 class="text-3xl font-bold mb-4">Student Dashboard</h1>

            <div class="mt-6">
                <h2 class="text-2xl font-bold mb-2">All Quizzes</h2>
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
                            <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="quiz" items="${quizzes}">
                            <c:set var="hasCompletedAttempt" value="${quizAttemptDAO.hasCompletedAttempt(quiz.id, currentStudent.id)}" />
                            <c:if test="${quiz.status == 'published' && !hasCompletedAttempt}">
                                <tr>
                                    <c:set var="currentAttempt" value="${quizAttemptDAO.getAttemptByUserIdAndQuizId(currentStudent.id, quiz.id)}" />
                                    <td class="px-4 py-2 border border-gray-800">${loop.index + 1}</td>
                                    <td class="px-4 py-2 border border-gray-800">${quiz.name}</td>
                                    <td class="px-4 py-2 border border-gray-800">${quiz.description}</td>
                                    <td class="px-4 py-2 border border-gray-800">${quiz.startTime}</td>
                                    <td class="px-4 py-2 border border-gray-800">${quiz.noOfQuestion}</td>
                                    <td class="px-4 py-2 border border-gray-800">${quiz.endTime}</td>
                                    <td class="px-4 py-2 border border-gray-800"><%= formatDuration(((model.Quiz) pageContext.getAttribute("quiz")).getDurationMinutes())%></td>
                                    <td class="px-4 py-2 border border-gray-800">
                                        <a target="_blank" href="${pageContext.request.contextPath}/student/quiz/${quiz.id}/attempt/new" class="block m-1 w-20 px-4 py-2 bg-green-200 rounded-lg hover:bg-green-300 transition duration-300">Attempt</a>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="mt-4">
                <h2 class="text-2xl font-bold mb-2">Passed Quizzes</h2>
                <table class="w-full border-separate border-spacing-2 border border-gray-800">
                    <thead>
                        <tr>
                            <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">No</th>
                            <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Quiz</th>
                            <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Description</th>
                            <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Started At</th>
                            <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Ended At</th>
                            <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Obtained Marks</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="quiz" items="${quizzes}">
                            <c:set var="hasCompletedAttempt" value="${quizAttemptDAO.hasCompletedAttempt(quiz.id, currentStudent.id)}" />
                            <c:if test="${quiz.status == 'closed' || hasCompletedAttempt}">
                                <tr>
                                    <c:set var="currentAttempt" value="${quizAttemptDAO.getAttemptByUserIdAndQuizId(currentStudent.id, quiz.id)}" />
                                    <td class="px-4 py-2 border border-gray-800">${loop.index + 1}</td>
                                    <td class="px-4 py-2 border border-gray-800">${quiz.name}</td>
                                    <td class="px-4 py-2 border border-gray-800">${quiz.description}</td>
                                    <td class="px-4 py-2 border border-gray-800">${currentAttempt.startTime}</td>
                                    <td class="px-4 py-2 border border-gray-800">${currentAttempt.endTime}</td>
                                    <td class="px-4 py-2 border border-gray-800"><fmt:formatNumber value="${currentAttempt.marks}" pattern="#"/> / ${quiz.noOfQuestion}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
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
