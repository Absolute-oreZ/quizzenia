/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dao.QuizAttemptDAO;
import dao.QuizDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Quiz;
import model.QuizAttempt;

/**
 *
 * @author User
 */
@WebServlet(name = "QuizCompletedServlet", urlPatterns = {"/student/quiz/attempt/completed/*"})
public class QuizCompletedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int quizAttemptId = Integer.parseInt(request.getPathInfo().split("/")[1]);
        
        QuizAttemptDAO quizAttemptDAO = new QuizAttemptDAO();
        QuizDAO quizDAO = new QuizDAO();
        QuizAttempt completedAttempt = quizAttemptDAO.getAttemptById(quizAttemptId);
        Quiz completedQuiz = quizDAO.getQuizById(completedAttempt.getQuizId());
        
        request.setAttribute("completedAttempt", completedAttempt);
        request.setAttribute("completedQuiz", completedQuiz);
        
        RequestDispatcher rd = request.getRequestDispatcher("/student/quizCompleted.jsp");
        rd.forward(request, response);
    }
}
