package controller.lecturer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dao.AnswerDAO;
import dao.QuestionDAO;
import dao.QuizDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Answer;
import model.Quiz;
import model.User;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/lecturer/quiz/*"})
public class AddQuizQuestionServlet extends HttpServlet {

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

            //Exrract quiz id from path
            int quizId = Integer.parseInt(request.getPathInfo().split("/")[1]);

            QuizDAO quizDAO = new QuizDAO();
            QuestionDAO questionDAO = new QuestionDAO();

            Quiz currentQuiz = quizDAO.getQuizById(quizId);
            int currentQuestionCount = questionDAO.getQuestionCountForQuiz(quizId);

            request.setAttribute("currentQuiz", currentQuiz);
            request.setAttribute("currentQuestionCount", currentQuestionCount); // to track the curret ques

            RequestDispatcher rd = request.getRequestDispatcher("/lecturer/addQuestion.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getPathInfo().split("/")[1]);

        //get the question and answers from form
        String questionText = request.getParameter("questionText");
        String[] answers = request.getParameterValues("answers");
        int corretAnswerIndex = Integer.parseInt(request.getParameter("correctAnswer"));

        //save question and ans to db
        QuestionDAO questionDAO = new QuestionDAO();
        AnswerDAO answerDAO = new AnswerDAO();

        int questionId = questionDAO.saveQuestion(quizId, questionText);
        boolean answersSaved = false;

        for (int i = 0; i < answers.length; i++) {
            boolean isCorrect = (i == corretAnswerIndex); //check if current ans if the correct ans and save its state
            Answer answer = new Answer();
            answer.setQuestionId(questionId);
            answer.setAnswerText(answers[i]);
            answer.setIsCorrect(isCorrect);
            answersSaved = answerDAO.saveAnswer(answer);
        }

        //check if a;; questions have been added
        QuizDAO quizDAO = new QuizDAO();
        Quiz quiz = quizDAO.getQuizById(quizId);
        int currentQuestionCount = questionDAO.getQuestionCountForQuiz(quizId);

        if (answersSaved) {
            if (currentQuestionCount < quiz.getNoOfQuestion()) {
                //redirect back to add another question
                response.sendRedirect(request.getContextPath() + "/lecturer/quiz/" + quizId + "/questions");
            } else {
                //all ques added, redirecto quiz list
                response.sendRedirect(request.getContextPath() + "/lecturer/quizzes");
            }
        } else {
            request.setAttribute("createQuestionError", "Question Not Created, Please Retry");
            response.sendRedirect(request.getContentType() + "/lecturer/quiz/" + quizId + "/questions");
        }
    }
}
