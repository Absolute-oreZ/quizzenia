/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import dao.AnswerDAO;
import dao.QuizAttemptDAO;
import dao.QuizDAO;
import dao.RecentActivityDAO;
import dao.UserAnswersDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Answer;
import model.Question;
import model.Quiz;
import model.QuizAttempt;
import model.RecentActivity;
import model.User;
import model.UserAnswers;

/**
 *
 * @author User
 */
@WebServlet(name = "AnswerQuizServlet", urlPatterns = {"/student/quiz/attempt/*"})
public class AnswerQuizServlet extends HttpServlet {

    AnswerDAO answerDAO = new AnswerDAO();
    QuizDAO quizDAO = new QuizDAO();
    UserAnswersDAO userAnswersDAO = new UserAnswersDAO();
    QuizAttemptDAO quizAttemptDAO = new QuizAttemptDAO();
    RecentActivityDAO recentActivityDAO = new RecentActivityDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int obtainedMarks = (int) request.getSession().getAttribute("obtainedMarks");
        int attemptId = Integer.parseInt(request.getPathInfo().split("/")[1]);
        QuizAttempt currentAttempt = quizAttemptDAO.getAttemptById(attemptId);
        Quiz currentQuiz = quizDAO.getQuizById(currentAttempt.getQuizId());
        List<Question> questions = (List<Question>) request.getSession().getAttribute("questions");
        int currentQuestionIndex = (int) request.getSession().getAttribute("currentQuestionIndex");

        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            List<Answer> answers = answerDAO.getAnswersByQuestionId(currentQuestion.getId());

            request.setAttribute("currentAttempt", currentAttempt);
            request.setAttribute("currentQuestion", currentQuestion);
            request.setAttribute("answers", answers);
            request.setAttribute("currentQuiz", currentQuiz);
            request.setAttribute("questionIndex", currentQuestionIndex + 1);
            request.setAttribute("totalQuestions", questions.size());

            request.getRequestDispatcher("/student/answerQuiz.jsp").forward(request, response);
        } else {
            User currentStudent = (User) request.getSession().getAttribute("user");
            RecentActivity recentActivity = new RecentActivity();
            String action = "Finished quiz \"" + currentQuiz.getName() + "\" with marks " + obtainedMarks + " / " + currentQuiz.getNoOfQuestion();
            recentActivity.setUserId(currentStudent.getId());
            recentActivity.setAction(action);
            recentActivity.setTimestamp(new Timestamp(System.currentTimeMillis()));
            Timestamp startTime = currentAttempt.getStartTime();
            Timestamp endTime = new Timestamp(System.currentTimeMillis());

            int timeTaken = (int) ((endTime.getTime() - startTime.getTime()) / 1000) / 60;

            boolean quizAttemptCompleted = quizAttemptDAO.completeQuiz(attemptId, endTime, timeTaken, obtainedMarks);
            boolean newActivityAdded = recentActivityDAO.newActivity(recentActivity);

            if (quizAttemptCompleted && newActivityAdded) {
                response.sendRedirect(request.getContextPath() + "/student/quiz/attempt/completed/" + attemptId);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle saving the answer and incrementing the question index
        int attemptId = Integer.parseInt(request.getPathInfo().split("/")[1]);
        int selectedAnswerId = Integer.parseInt(request.getParameter("answer"));
        int currentQuestionIndex = (int) request.getSession().getAttribute("currentQuestionIndex");
        List<Question> questions = (List<Question>) request.getSession().getAttribute("questions");

        // Retrieve obtainedMarks from the session
        int obtainedMarks = (int) request.getSession().getAttribute("obtainedMarks");

        UserAnswers userAnswers = new UserAnswers();
        userAnswers.setQuestionId(questions.get(currentQuestionIndex).getId());
        userAnswers.setQuizAttemtId(attemptId);
        userAnswers.setAnswerId(selectedAnswerId);
        // Save the user's answer
        int userAnsId = userAnswersDAO.saveUserAnswer(userAnswers);
        userAnswers.setId(userAnsId);

        boolean isCorrect = userAnswersDAO.validateAnswer(attemptId, questions.get(currentQuestionIndex).getId());

        if (isCorrect) {
            obtainedMarks++;
        }

        // Update obtainedMarks in the session
        request.getSession().setAttribute("obtainedMarks", obtainedMarks);

        // Move to next question
        currentQuestionIndex++;
        request.getSession().setAttribute("currentQuestionIndex", currentQuestionIndex);

        // Redirect to the same servlet to show the next question
        response.sendRedirect(request.getContextPath() + "/student/quiz/attempt/" + attemptId);
    }
}
