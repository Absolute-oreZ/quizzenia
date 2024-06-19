/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecturer;

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
@WebServlet(name = "CreateNewQuizServlet", urlPatterns = {"/lecturer/quiz/new"})
public class CreateNewQuizServlet extends HttpServlet {

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

            RequestDispatcher rd = request.getRequestDispatcher("/lecturer/createNewQuiz.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int classId = Integer.parseInt(request.getParameter("classId"));
        int noOfQuestion = Integer.parseInt(request.getParameter("noOfQuestion"));
        String startTimeStr = request.getParameter("startTime");
        Timestamp startTime = datetimeLocalToTimestamp(startTimeStr);
        String endTimeStr = request.getParameter("endTime");
        Timestamp endTime = datetimeLocalToTimestamp(endTimeStr);
        int durationMinutes = (int) ((endTime.getTime() - startTime.getTime()) / 1000) / 60;
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        String userId = request.getParameter("userId");
        String action = "Created a new quiz titled \"" + name + "\"";

        Quiz quiz = new Quiz();
        RecentActivity newActivity = new RecentActivity();

        quiz.setName(name);
        quiz.setDescription(description);
        quiz.setClassId(classId);
        quiz.setNoOfQuestion(noOfQuestion);
        quiz.setStartTime(startTime);
        quiz.setEndTime(endTime);
        quiz.setDurationMinutes(durationMinutes);
        quiz.setCreatedAt(createdAt);

        newActivity.setUserId(userId);
        newActivity.setAction(action);
        newActivity.setTimestamp(createdAt);

        QuizDAO quizDAO = new QuizDAO();
        RecentActivityDAO recentActivityDAO = new RecentActivityDAO();

        int newQuizId = quizDAO.createQuiz(quiz);

        if (newQuizId != -1) {
            quiz.setId(newQuizId);  // Set the generated ID to the quiz object
            boolean recentActivityAdded = recentActivityDAO.newActivity(newActivity);

            if (recentActivityAdded) {
                response.sendRedirect(request.getContextPath() + "/lecturer/quiz/" + newQuizId + "/questions");
            } else {
                // Handle case where recent activity couldn't be added
                request.setAttribute("error", "Quiz created but failed to log activity");
                RequestDispatcher rd = request.getRequestDispatcher("/lecturer/quizList.jsp");
                rd.forward(request, response);
            }
        } else {
            request.setAttribute("error", "Failed to create quiz");
            RequestDispatcher rd = request.getRequestDispatcher("/lecturer/createNewQuiz.jsp");
            rd.forward(request, response);
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
