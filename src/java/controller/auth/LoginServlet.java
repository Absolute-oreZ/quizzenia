package controller.auth;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.UserDAO;
import model.User;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO(); // Initialize your DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/auth/login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate the email and password directly
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("loginError", "All fields are required.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Perform authentication against the database
        User user = userDAO.getUserByEmailAndPassword(email, password);

        if (user != null) {
            // Authentication successful
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath()); // Redirect to home or dashboard
        } else {
            // Authentication failed - Set error message
            request.setAttribute("loginError", "Invalid email or password");
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}
