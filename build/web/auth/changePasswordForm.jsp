<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Change Password</title>
        <link rel="icon" type="image/png" href="images/logo.png" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
        <style>
            /* Additional custom styles can be added here */
        </style>
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
                                <img class="w-8 h-8 rounded-full" src="${pageContext.request.contextPath}/images/defaultProfile.png" alt="user photo">
                                <a href="${pageContext.request.contextPath}/logout" class="block px-3 text-gray-900 rounded">Log Out</a>
                            </div>
                        </div>
                    </nav>
                </c:otherwise>
            </c:choose>
            <div class="container mx-auto py-8">
                <div class="max-w-lg mx-auto bg-white p-6 rounded-lg shadow-lg">
                    <h1 class="text-2xl font-bold mb-4">Change Password</h1>
                    <form id="changePasswordForm" action="${pageContext.request.contextPath}/profile/change-password" method="POST" class="space-y-4">
                        <input type="hidden" id="userId" name="userId" value="${user.id}">
                        <div class="mb-4">
                            <label for="currentPassword" class="block text-sm font-medium text-gray-700">Current Password:</label>
                            <input type="password" id="currentPassword" name="currentPassword" placeholder="Enter current password" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                        </div>
                        <div class="mb-4">
                            <label for="newPassword" class="block text-sm font-medium text-gray-700">New Password:</label>
                            <input type="password" id="newPassword" name="newPassword" placeholder="Enter new password" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                            <div id="passwordStrength" class="text-sm mt-1"></div>
                        </div>
                        <div class="mb-4">
                            <label for="confirmNewPassword" class="block text-sm font-medium text-gray-700">Confirm New Password:</label>
                            <input type="password" id="confirmNewPassword" name="confirmNewPassword" placeholder="Confirm new password" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                        </div>
                        <div id="changePasswordError" class="text-red-500">
                            <%-- Display error messages here --%>
                            <% if (request.getParameter("error") != null) { %>
                            <% if (request.getParameter("error").equals("invalid")) { %>
                            Incorrect current password.
                            <% } else if (request.getParameter("error").equals("db")) { %>
                            Database error. Please try again later.
                            <% } %>
                            <% }%>
                        </div>
                        <button type="submit" class="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600">Change Password</button>
                    </form>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const passwordField = document.getElementById('newPassword');
                const passwordStrengthText = document.getElementById('passwordStrength');

                passwordField.addEventListener('input', function () {
                    const password = passwordField.value;
                    const strength = checkPasswordStrength(password);

                    switch (strength) {
                        case 0:
                            passwordStrengthText.textContent = 'Password strength: Very weak';
                            break;
                        case 1:
                            passwordStrengthText.textContent = 'Password strength: Weak';
                            break;
                        case 2:
                            passwordStrengthText.textContent = 'Password strength: Medium';
                            break;
                        case 3:
                            passwordStrengthText.textContent = 'Password strength: Strong';
                            break;
                        case 4:
                            passwordStrengthText.textContent = 'Password strength: Very strong';
                            break;
                        default:
                            passwordStrengthText.textContent = '';
                    }
                });

                function checkPasswordStrength(password) {
                    // Regular expressions for password strength
                    const regex = [
                        /[A-Z]/, // Uppercase letters
                        /[a-z]/, // Lowercase letters
                        /[0-9]/, // Numbers
                        /[@$!%*?&]/ // Special characters
                    ];

                    let strength = 0;

                    // Check each regex pattern
                    regex.forEach(pattern => {
                        if (pattern.test(password)) {
                            strength++;
                        }
                    });

                    // Bonus point for length
                    if (password.length >= 8) {
                        strength++;
                    }

                    return strength;
                }
            });
        </script>
    </body>
</html>
