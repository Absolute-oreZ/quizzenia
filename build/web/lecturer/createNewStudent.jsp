<%@page import="model.User"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quizzenia - New Student</title>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/logo.png" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body>
        <div>
            <%@ include file="/components/navbar.jsp" %>
        </div>
        <div class="w-full flex flex-1">
            <div class="flex flex-1 justify-center items-center flex-col w-1/2 py-10">
                <div id="error" class="text-red-500 mb-4"></div>
                <h2 class="text-2xl font-bold mb-8">Register New Student</h2>
                <form id="signupForm" action="${pageContext.request.contextPath}/lecturer/student/new" method="POST" onsubmit="return validateSignup()" class="space-y-4">
                    <div>
                        <label for="name" class="block text-sm font-medium text-gray-700">Name</label>
                        <input type="text" id="name" name="name" placeholder="Enter name" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                    </div>
                    <div>
                        <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
                        <input type="email" id="email" name="email" placeholder="Enter email" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                    </div>
                    <div>
                        <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
                        <input type="password" id="password" name="password" placeholder="Enter password" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                    </div>
                    <div>
                        <label for="confirmPassword" class="block text-sm font-medium text-gray-700">Confirm Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm password" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                    </div>
                    <div>
                        <label for="class" class="block text-sm font-medium text-gray-700">Class</label>
                        <select id="class" name="class" required class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                            <option value="" selected disabled hidden>Choose here</option>
                            <option value="1">K1</option>
                            <option value="2">K2</option>
                        </select>
                    </div>
                    <div>
                        <input type="hidden" id="userId" name="userId" value="${user.id}" >
                    </div>
                    <div id="signupError" class="text-red-500"></div>
                    <button type="submit" class="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600">Register User</button>
                </form>
            </div>
            <div class="flex flex-col p-8 bg-white w-1/2 items-center justify-center">
                <img src="${pageContext.request.contextPath}/images/logo.png" alt="logo" />
                <div class="text-slate-800 text-center py-4 px-6 font-montserrat font-bold text-3xl">
                    <span class="text-7xl font-extrabold">"</span>Welcoming Excellence: Where Every New Quiz Elevates Our Knwoledege!
                </div>
            </div>
        </div>

        <script>
            function validateSignup() {
                var name = document.getElementById("name").value;
                var email = document.getElementById("email").value;
                var password = document.getElementById("password").value;
                var confirmPassword = document.getElementById("confirmPassword").value; // Fetch confirmPassword value
                var errorElement = document.getElementById("signupError");

                // Clear previous error message
                errorElement.innerText = "";

                if (name === "" || email === "" || password === "" || confirmPassword === "") {
                    errorElement.innerText = "All fields are required.";
                    return false;
                }

                // Validate email format
                var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailRegex.test(email)) {
                    errorElement.innerText = "Invalid email format.";
                    return false;
                }

                // Validate password length
                if (password.length < 8) {
                    errorElement.innerText = "Password must be at least 8 characters long.";
                    return false;
                }

                // Validate password strength
                var passwordStrengthRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
                if (!passwordStrengthRegex.test(password)) {
                    errorElement.innerText = "Password must contain at least one uppercase letter, one lowercase letter, one number, one special character, and be at least 8 characters long.";
                    return false;
                }

                // Validate password match
                if (password !== confirmPassword) {
                    errorElement.innerText = "Passwords do not match.";
                    return false;
                }

                return true;
            }

        </script>
    </body>
</html>
