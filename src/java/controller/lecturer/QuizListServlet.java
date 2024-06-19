/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecturer;

import dao.QuizDAO;
import java.io.IOException;
import java.util.List;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Quiz;
import model.User;

/**
 *
 * @author User
 */
@WebServlet(name = "QuizListServlet", urlPatterns = {"/lecturer/quizzes"})
public class QuizListServlet extends HttpServlet {

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
                QuizDAO quizDAO = new QuizDAO();
                List<Quiz> quizzes = quizDAO.getQuizzesByClassId(currentLecturer.getClassId());

                request.setAttribute("quizzes", quizzes);

                RequestDispatcher rd = request.getRequestDispatcher("/lecturer/quizList.jsp");
                rd.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
