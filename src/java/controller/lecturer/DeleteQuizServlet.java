/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecturer;

import dao.QuizDAO;
import dao.RecentActivityDAO;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Quiz;
import model.RecentActivity;
import model.User;

/**
 *
 * @author User
 */
@WebServlet(name = "DeleteQuizServlet", urlPatterns = {"/lecturer/quiz/delete/*"})
public class DeleteQuizServlet extends HttpServlet {

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
            int id = Integer.parseInt(request.getPathInfo().substring(1));

            RecentActivityDAO recentActivityDAO = new RecentActivityDAO();
            QuizDAO quizDAO = new QuizDAO();
            Quiz quizTobeDeleted = (Quiz) quizDAO.getQuizById(id);

            String action = "Deleted quiz titled \"" + quizTobeDeleted.getName() + "\"";

            RecentActivity recentActivity = new RecentActivity();
            recentActivity.setUserId(currentLect.getId());
            recentActivity.setAction(action);
            recentActivity.setTimestamp(new Timestamp(System.currentTimeMillis()));

            boolean newActivityAdded = recentActivityDAO.newActivity(recentActivity);
            boolean isDeleted = quizDAO.deleteQuiz(id);

            if (isDeleted && newActivityAdded) {
                response.sendRedirect(request.getContextPath() + "/lecturer/quizzes");
            }
        }
    }
}
