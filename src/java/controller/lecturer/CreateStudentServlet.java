package controller.lecturer;

import dao.RecentActivityDAO;
import dao.UserDAO;
import model.User;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.RecentActivity;

@WebServlet(name = "CreateUserServlet", urlPatterns = "/lecturer/student/new")
public class CreateStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentLect = (User) request.getSession().getAttribute("user");
        if (currentLect == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            if (currentLect.getRole().equals("lecturer")) {
                request.setAttribute("error", "Unauthorize Access");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            RequestDispatcher rd = request.getRequestDispatcher("/lecturer/createNewStudent.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = "Student";
        int classId = Integer.parseInt(request.getParameter("class"));
        String userId = request.getParameter("userId");
        String action = "Registed Account for \"" + name + "\"";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (name == null || email == null || password == null
                || name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            RequestDispatcher rd = request.getRequestDispatcher("/lecturer/createNewStudent.jsp");
            rd.forward(request, response);
            return;
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole(role);
        newUser.setClass_id(classId);

        RecentActivity newActivity = new RecentActivity();
        newActivity.setUserId(userId);
        newActivity.setAction(action);
        newActivity.setTimestamp(timestamp);

        UserDAO userDAO = new UserDAO();
        RecentActivityDAO recentActivityDAO = new RecentActivityDAO();

        boolean isUserCreated = userDAO.createUser(newUser);
        boolean newActivityAdded = recentActivityDAO.newActivity(newActivity);

        if (isUserCreated && newActivityAdded) {
            response.sendRedirect(request.getContextPath() + "/lecturer/students");
        } else {
            request.setAttribute("error", "Failed to create user. Email might already be in use.");
            response.sendRedirect(request.getContextPath() + "/lecturer/createNewUser.jsp");
        }
    }
}
