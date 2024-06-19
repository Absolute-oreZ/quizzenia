/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecturer;

import dao.QuizAttemptDAO;
import dao.QuizDAO;
import dao.UserDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Quiz;
import model.QuizAttempt;
import model.User;

/**
 *
 * @author User
 */
@WebServlet(name = "ReviewQuizServlet", urlPatterns = {"/lecturer/quiz/review/*"})
public class ReviewQuizServlet extends HttpServlet {

    QuizDAO quizDAO = new QuizDAO();
    QuizAttemptDAO quizAttemptDAO = new QuizAttemptDAO();
    UserDAO userDAO = new UserDAO();

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

            Quiz currentQuiz = quizDAO.getQuizById(quizId);

            List<QuizAttempt> quizAttempts = quizAttemptDAO.getAllQuizAttempts(quizId);

            request.setAttribute("currentQuiz", currentQuiz);
            request.setAttribute("quizAttempts", quizAttempts);

            RequestDispatcher rd = request.getRequestDispatcher("/lecturer/reviewQuiz.jsp");
            rd.forward(request, response);
        }
    }
}
