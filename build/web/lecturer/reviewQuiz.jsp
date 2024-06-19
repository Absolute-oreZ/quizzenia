<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="dao.UserDAO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quizzenia</title>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body>
        <div>
            <%@include file="/components/navbar.jsp" %>
        </div>
        <div class="container mx-auto p-8">
            <h2 class="text-2xl font-bold mb-8">Quiz Review For Quiz: ${currentQuiz.name}</h2>
            <table class="w-full border-separate border-spacing-2 border border-gray-800">
                <thead>
                    <tr>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">No</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Student</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Start Time</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">End Time</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Marks</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="userDAO" value="<%= new dao.UserDAO()%>" />
                    <c:forEach var="quizAttempt" items="${quizAttempts}" varStatus="loop">
                        <tr>
                            <td class="px-4 py-2 border border-gray-800">${loop.index + 1}</td>
                            <td class="px-4 py-2 border border-gray-800">
                                ${userDAO.getStudentNameById(quizAttempt.studentId)}
                            </td>
                            <td class="px-4 py-2 border border-gray-800">${quizAttempt.startTime}</td>
                            <td class="px-4 py-2 border border-gray-800">${quizAttempt.endTime}</td>
                            <td class="px-4 py-2 border border-gray-800">${quizAttempt.marks}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
