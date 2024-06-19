/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecturer;

import dao.QuestionDAO;
import dao.QuizDAO;
import dao.RecentActivityDAO;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "UpdateQuizServlet", urlPatterns = {"/lecturer/quiz/edit/*"})
public class UpdateQuizServlet extends HttpServlet {

    QuizDAO quizDAO = new QuizDAO();

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
            Quiz quizToBeEdited = (Quiz) quizDAO.getQuizById(id);
            request.setAttribute("quiz", quizToBeEdited);
            RequestDispatcher rd = request.getRequestDispatcher("/lecturer/editQuiz.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int noOfQuestion = Integer.parseInt(request.getParameter("noOfQuestion"));
        String startTimeStr = request.getParameter("startTime");
        Timestamp startTime = datetimeLocalToTimestamp(startTimeStr);
        String endTimeStr = request.getParameter("endTime");
        Timestamp endTime = datetimeLocalToTimestamp(endTimeStr);
        int durationMinutes = (int) ((endTime.getTime() - startTime.getTime()) / 1000) / 60;
        System.out.println("hahshhs" + id);

        String userId = request.getParameter("userId");
        String action = "Updated quiz titled \"" + name + "\"";

        Quiz quiz = new Quiz();
        RecentActivity newActivity = new RecentActivity();

        quiz.setId(id);
        quiz.setName(name);
        quiz.setDescription(description);
        quiz.setNoOfQuestion(noOfQuestion);
        quiz.setStartTime(startTime);
        quiz.setEndTime(endTime);
        quiz.setDurationMinutes(durationMinutes);

        newActivity.setUserId(userId);
        newActivity.setAction(action);
        newActivity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        QuizDAO quizDAO = new QuizDAO();
        RecentActivityDAO recentActivityDAO = new RecentActivityDAO();
        QuestionDAO questionDAO = new QuestionDAO();

        int currentQuestionCount = questionDAO.getQuestionCountForQuiz(id);

        boolean quizUpdated = quizDAO.updateQuizById(quiz);
        boolean recentActivityAdded = recentActivityDAO.newActivity(newActivity);

        if (quizUpdated && recentActivityAdded) {
            if (currentQuestionCount < quiz.getNoOfQuestion()) {
                response.sendRedirect(request.getContextPath() + "/lecturer/quiz/" + id + "/questions");
            } else {
                response.sendRedirect(request.getContextPath() + "/lecturer/quizzes");
            }
        } else {
            request.setAttribute("error", "Failed to create create");
            response.sendRedirect(request.getContextPath() + "/lecturer/quiz/edit/" + id);
        }
    }

    protected Timestamp datetimeLocalToTimestamp(String dateTimeStr) {
        Timestamp parsedDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        try {
            Date parsed = formatter.parse(dateTimeStr);
            parsedDate = new Timestamp(parsed.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parsedDate;
    }
}
