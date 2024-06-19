/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dao.QuestionDAO;
import dao.QuizAttemptDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Question;
import model.QuizAttempt;
import model.User;

/**
 *
 * @author User
 */
@WebServlet(name = "AttemptQuizServlet", urlPatterns = {"/student/quiz/*"})
public class CreateNewAttemptServlet extends HttpServlet {

    QuizAttemptDAO quizAttemptDAO = new QuizAttemptDAO();
    QuestionDAO questionDAO = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int quizId = Integer.parseInt(request.getPathInfo().split("/")[1]);
        User currentStudent = (User) request.getSession().getAttribute("user");

        QuizAttempt newAttempt = new QuizAttempt();
        newAttempt.setQuizId(quizId);
        newAttempt.setStudentId(currentStudent.getId());
        newAttempt.setStartTime(new Timestamp(System.currentTimeMillis()));

        int newAttemptId = quizAttemptDAO.createNewQuizAttempt(newAttempt);
        newAttempt.setId(newAttemptId);
        
        // Reset obtainedMarks and store it in the session
        request.getSession().setAttribute("obtainedMarks", 0);

        // Fetch the questions and set it in session
        List<Question> questions = questionDAO.getQuestionsByQuiz(quizId);
        request.getSession().setAttribute("questions", questions);
        request.getSession().setAttribute("currentQuestionIndex", 0);
        request.getSession().setAttribute("quizAttemptId", newAttemptId);

        response.sendRedirect(request.getContextPath() + "/student/quiz/attempt/" + newAttemptId);
    }
}
