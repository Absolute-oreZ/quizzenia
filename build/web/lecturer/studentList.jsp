<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div class="p-6 flex justify-between items-center">
                <h1 class="text-3xl font-bold mb-4">Students in class K${user.classId}</h1>
                <a href="${pageContext.request.contextPath}/lecturer/student/new" class="inline-block px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition duration-300">+ New Student</a>
            </div>
            <table class="w-full border-separate border-spacing-2 border border-gray-50">
                <thead>
                    <tr>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">No</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Student ID</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Student Name</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Student Email</th>
                        <th class="px-4 py-2 border border-gray-800 text-left text-xs font-medium text-gray-800 uppercase tracking-wider">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="student" items="${students}" varStatus="loop">
                        <tr>
                            <td class="px-4 py-2 border border-gray-800">${loop.index + 1}</td>
                            <td class="px-4 py-2 border border-gray-800">${student.id}</td>
                            <td class="px-4 py-2 border border-gray-800">${student.name}</td>
                            <td class="px-4 py-2 border border-gray-800">${student.email}</td>
                            <td class="px-4 py-2 border border-gray-800 justify-around">
                                <a href="mailto:${student.email}" class="inline-block px-4 py-2 bg-yellow-400 text-white rounded-lg hover:bg-yellow-500 transition duration-300">Contact</a>
                                <a href="${pageContext.request.contextPath}/lecturer/student/delete/${student.id}" class="inline-block px-4 py-2 bg-pink-600 text-white rounded-lg hover:bg-pink-700 transition duration-300">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
