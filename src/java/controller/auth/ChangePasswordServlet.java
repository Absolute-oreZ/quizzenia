/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author User
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/profile/change-password"})
public class ChangePasswordServlet extends HttpServlet {

    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/auth/changePasswordForm.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");

        // Fetch current user from session or database (depending on your implementation)
        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser != null && currentUser.getId().equals(userId) && currentUser.getPassword().equals(currentPassword)) {
            boolean passwordChanged = userDAO.updateUserPassword(userId, newPassword);
            if (passwordChanged) {
                // Update session or user object with new password
                currentUser.setPassword(newPassword);
                request.getSession().setAttribute("user", currentUser);
                // Redirect to profile page with success message
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                // Handle database update failure
                response.sendRedirect(request.getContextPath() + "/auth/changePassword.jsp?error=db");
            }
        } else {
            // Handle incorrect current password scenario
            response.sendRedirect(request.getContextPath() + "/auth/changePassword.jsp?error=invalid");
        }
    }

}
