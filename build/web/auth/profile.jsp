<%-- 
    Document   : profile
    Created on : 19 Jun 2024, 8:24:49 pm
    Author     : User
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body class="bg-gray-100">
        <div>
            <c:choose>
                <c:when test="${user.role == 'Lecturer'}">
                    <jsp:include page="/components/navbar.jsp" />
                </c:when>
                <c:otherwise>
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
                </c:otherwise>
            </c:choose>
        </div>
        <div class="container mx-auto py-8">
            <div class="max-w-lg mx-auto bg-white p-6 rounded-lg shadow-lg">
                <h1 class="text-2xl font-bold mb-4">User Profile</h1>
                <div class="mb-4">
                    <label for="name" class="block text-sm font-medium text-gray-700">Name:</label>
                    <span id="name" class="text-lg font-medium text-gray-900">${user.name}</span>
                </div>
                <div class="mb-4">
                    <label for="email" class="block text-sm font-medium text-gray-700">Email:</label>
                    <span id="email" class="text-lg font-medium text-gray-900">${user.email}</span>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/profile/change-password" class="text-blue-500 hover:underline">Change Password</a>
                </div>
            </div>
        </div>
    </body>
</html>
