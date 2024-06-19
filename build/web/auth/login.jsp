<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Quizzenia - Login</title>
        <link rel="icon" type="image/png" href="images/logo.png" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="flex flex-1">
            <div class="flex flex-1 justify-center items-center flex-col py-10">
                <h2 class="text-2xl font-bold mb-4">Login</h2>
                <!-- Display error message if present -->
                <div id="loginError" class="text-red-500 mb-4">
                    <% String loginError = (String) request.getAttribute("loginError");
                        if (loginError != null) {
                            out.print(loginError);
                        }
                    %>
                </div>
                <form id="loginForm" action="login" method="POST" onsubmit="return validateLogin(event)" class="space-y-4">
                    <div>
                        <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
                        <input type="email" placeholder="Enter your email" id="email" name="email" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                    </div>
                    <div>
                        <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
                        <input type="password" id="password" placeholder="Enter your password" name="password" class="mt-1 p-2 block w-full border-2 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500">
                    </div>
                    <button type="submit" class="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600">Login</button>
                </form>
            </div>
            <div class=" w-1/2 h-screen">
                <img src="images/Word Cloud.png" alt="Image" class="h-full w-full object-contain bg-no-repeat px-10">
            </div>
        </div>
        <script>
            function validateLogin(event) {
                event.preventDefault(); // Prevent default form submission

                var email = document.getElementById("email").value.trim(); // Trim whitespace
                var password = document.getElementById("password").value.trim();
                var errorElement = document.getElementById("loginError");

                // Clear previous error message
                errorElement.innerText = "";

                // Basic checks for empty fields
                if (email === "" || password === "") {
                    errorElement.innerText = "All fields are required.";
                    return false;
                }

                // Validate email format using regex
                var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailRegex.test(email)) {
                    errorElement.innerText = "Invalid email format.";
                    return false;
                }
                document.getElementById("loginForm").submit();
            }
        </script>

    </body>
</html>
