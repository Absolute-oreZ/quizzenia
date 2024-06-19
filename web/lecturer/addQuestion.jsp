<%-- 
    Document   : addQuestion.jsp
    Created on : 17 Jun 2024, 11:14:53 pm
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <div class="flex p-2">
            <div class="container max-h-4/5 mx-auto w-1/2 px-8 py-2">
                <div class="flex justify-between">
                    <h2 class="text-2xl font-bold mb-3">Add Question for Quiz: ${currentQuiz.name}</h2>
                    <h6 class="text-lg font-semibold">Question ${currentQuestionCount + 1} of ${currentQuiz.noOfQuestion}</h6>
                </div>
                <form id="newQuestionForm" action="${pageContext.request.contextPath}/lecturer/quiz/${currentQuiz.id}/questions" method="POST" class="space-y-4">
                    <fieldset>
                        <div id="createQuestionError" class="text-red-500 mb-4"></div>
                        <div class="grid grid-cols-1 gap-4">
                            <div>
                                <label for="questionText" class="block text-sm font-medium text-gray-700">Question:</label>
                                <textarea id="questionText" name="questionText" placeholder="Enter quiz text" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500" required></textarea>
                            </div>

                            <div>
                                <label for="answer1" class="block text-sm font-medium text-gray-700">Answer 1:</label>
                                <div class="flex flex-1 p-2">
                                    <input type="text" id="answer1" name="answers" placeholder="Enter first answer for this question" class=" p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 m-2" required>
                                    <input type="radio" name="correctAnswer" value="0" required class="items-center justify-around p-1 " > <span class="self-center ml-1">Correct</span>
                                </div>
                            </div>
                            <div>
                                <label for="answer2" class="block text-sm font-medium text-gray-700">Answer 2:</label>
                                <div class="flex flex-1 p-2">
                                    <input type="text" id="answer2" name="answers" placeholder="Enter second answer for this question" class=" p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 m-2" required>
                                    <input type="radio" name="correctAnswer" value="1" required class="items-center justify-around p-1 " > <span class="self-center ml-1">Correct</span>
                                </div>
                            </div>
                            <div>
                                <label for="answer3" class="block text-sm font-medium text-gray-700">Answer 3:</label>
                                <div class="flex flex-1 p-2">
                                    <input type="text" id="answer3" name="answers" placeholder="Enter third answer for this question" class=" p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 m-2" required>
                                    <input type="radio" name="correctAnswer" value="2" required class="items-center justify-around p-1 " > <span class="self-center ml-1">Correct</span>
                                </div>
                            </div
                            <div>
                                <label for="answer4" class="block text-sm font-medium text-gray-700">Answer 4:</label>
                                <div class="flex flex-1 p-2">
                                    <input type="text" id="answer4" name="answers" placeholder="Enter fourth answer for this question" class=" p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 m-2" required>
                                    <input type="radio" name="correctAnswer" value="3" required class="items-center justify-around p-1 " > <span class="self-center ml-1">Correct</span>
                                </div>
                            </div>
                    </fieldset>
                    <div class="mt-4">
                        <button type="submit" class="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600">Add Question</button>
                    </div>
                </form>
            </div>
            <div class="hidden xl:flex xl:flex-col p-8 bg-white w-1/2 items-center justify-center">
                <img src="${pageContext.request.contextPath}/images/Word Cloud.png" alt="Image" class="h-3/5 w-3/5 object-contain bg-no-repeat px-10">
                <div class="text-slate-800 text-center py-4 px-6 font-montserrat font-bold text-3xl">
                    <span class="text-7xl font-extrabold">"</span>Education is the most powerful weapon you can use to change the world.
                </div>
            </div>
        </div>
    </body>
</html>