/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecturer;

import dao.UserDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "StudentsListServlet", urlPatterns = {"/lecturer/students"})
public class StudentsListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentLect = (User) request.getSession().getAttribute("user");
        if (currentLect == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            if (currentLect.getRole().equals("lecturer")) {
                request.setAttribute("error", "Unauthorize Access");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            try {
                User currentLecturer = (User) request.getSession().getAttribute("user");
                UserDAO userDAO = new UserDAO();
                List<User> students = userDAO.getStudentsByClass(currentLecturer.getClassId());

                request.setAttribute("students", students);

                RequestDispatcher rd = request.getRequestDispatcher("/lecturer/studentList.jsp");
                rd.forward(request, response);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception
            }
        }
    }
}
