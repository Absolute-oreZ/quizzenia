/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dao.QuizAttemptDAO;
import dao.QuizDAO;
import dao.RecentActivityDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Quiz;
import model.QuizAttempt;
import model.RecentActivity;
import model.User;

/**
 *
 * @author User
 */
@WebServlet(name = "StudentServlet", urlPatterns = {"/student"})
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            User currentStudent = (User) request.getSession().getAttribute("user");
            if (currentStudent != null) {
                QuizDAO quizDAO = new QuizDAO();
                QuizAttemptDAO quizAttemptDAO = new QuizAttemptDAO();
                RecentActivityDAO recentActivityDAO = new RecentActivityDAO();
                List<Quiz> quizzes = quizDAO.getQuizzesByClassId(currentStudent.getClassId());
                List<RecentActivity> recentActivities = recentActivityDAO.getRecentActivitiesByUser(currentStudent.getId());

                request.setAttribute("currentStudent", currentStudent);
                request.setAttribute("quizzes", quizzes);
                request.setAttribute("recentActivities", recentActivities);
                request.setAttribute("quizAttemptDAO", quizAttemptDAO);

                RequestDispatcher rd = request.getRequestDispatcher("/student/home.jsp");
                rd.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
