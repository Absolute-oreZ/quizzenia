package controller.lecturer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Quiz;
import dao.QuizDAO;
import dao.RecentActivityDAO;
import dao.UserDAO;
import model.RecentActivity;
import model.User;

@WebServlet(name = "LecturerServlet", urlPatterns = {"/lecturer"})
public class LecturerServlet extends HttpServlet {

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

            try {
                User currentLecturer = (User) request.getSession().getAttribute("user");
                int classId = currentLecturer.getClassId();
                QuizDAO quizDAO = new QuizDAO();
                RecentActivityDAO recentActivityDao = new RecentActivityDAO();
                UserDAO userDAO = new UserDAO();
                List<User> students = userDAO.getStudentsByClass(classId);
                List<Quiz> quizzes = quizDAO.getQuizzesByClassId(currentLecturer.getClassId());
                List<RecentActivity> recentActivities = recentActivityDao.getRecentActivitiesByUser(currentLecturer.getId());

                request.getSession().setAttribute("students", students);
                request.getSession().setAttribute("quizzes", quizzes);
                request.getSession().setAttribute("recentActivities", recentActivities);

                // Forward the request to the lecturer page JSP
                RequestDispatcher dispatcher = request.getRequestDispatcher("/lecturer/dashboard.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(LecturerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
