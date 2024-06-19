/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecturer;

import dao.QuizDAO;
import dao.RecentActivityDAO;
import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Quiz;
import model.RecentActivity;
import model.User;
import util.DBUtil;

/**
 *
 * @author User
 */
@WebServlet(name = "PublishQuizServlet", urlPatterns = {"/lecturer/quiz/publish/*"})
public class PublishQuizServlet extends HttpServlet {

    private final String PUBLISH_QUIZ = "UPDATE quiz SET STATUS = 'published' WHERE id = ?";

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

            int quizId = Integer.parseInt(request.getPathInfo().split("/")[1]);

            Connection connection = null;
            PreparedStatement stmt = null;
            try {
                connection = DBUtil.getConnection();
                stmt = connection.prepareStatement(PUBLISH_QUIZ);

                stmt.setInt(1, quizId);
                RecentActivityDAO recentActivityDAO = new RecentActivityDAO();
                QuizDAO quizDAO = new QuizDAO();

                Quiz quiz = quizDAO.getQuizById(quizId);
                User user = (User) request.getSession().getAttribute("user");

                String action = "Publised quiz \"" + quiz.getName() + "\"";

                RecentActivity recentActivity = new RecentActivity();
                recentActivity.setUserId(user.getId());
                recentActivity.setAction(action);
                recentActivity.setTimestamp(new Timestamp(System.currentTimeMillis()));

                int affectedRows = stmt.executeUpdate();
                boolean newActivityAdded = recentActivityDAO.newActivity(recentActivity);

                if (affectedRows >= 1 && newActivityAdded) {
                    response.sendRedirect(request.getContextPath() + "/lecturer/quizzes");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        stmt.close();
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
