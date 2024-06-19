<%-- 
    Document   : navabr
    Created on : 14 Jun 2024, 9:31:46 pm
    Author     : User
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        <nav style="background-color: #F2F1EB" class="border-b-2">
            <div class="max-w-screen-xl bg-slate-500 flex items-center justify-between mx-auto p-4">
                <a href="${pageContext.request.contextPath}" class="flex items-center space-x-3 rtl:space-x-reverse">
                    <img src="${pageContext.request.contextPath}/images/logo.png" class="h-8" alt="logo" />
                    <span class="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">Quizzenia</span>
                </a>
                <div class="flex-1 flex justify-end items-center space-x-10">
                    <ul class="flex items-center space-x-5">
                        <li>
                            <a href="${pageContext.request.contextPath}" class="block py-2 px-3">Dashboard</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/lecturer/quizzes" class="block py-2 px-3">Quizzes</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/lecturer/students" class="block py-2 px-3 text-gray-900 rounded">Students</a>
                        </li>
                    </ul>
                    <div class="flex items-center">
                        <a href="${pageContext.request.contextPath}/profile"><img class="w-8 h-8 rounded-full" src="${pageContext.request.contextPath}/images/defaultProfile.png" alt="user photo"></a>
                        <a href="${pageContext.request.contextPath}/logout" class="block py-2 px-3 text-gray-900 rounded">Log Out</a>
                    </div>
                </div>
            </div>
        </nav>
    </body>
</html>
