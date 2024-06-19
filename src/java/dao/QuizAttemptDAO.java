/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import model.QuizAttempt;
import util.DBUtil;

/**
 *
 * @author User
 */
public class QuizAttemptDAO {

    private final String GET_QUIZ_ATTEMPTS_BY_QUIZ_ID = "SELECT * FROM quizAttempt WHERE quizId = ?";
    private final String GET_ATTEMPT_BY_ATTEMPT_ID = "SELECT * FROM quizattempt WHERE id = ?";
    private final String CREATE_NEW_QUIZ_ATTEMPT = "INSERT INTO quizAttempt (studentId, quizId, startTime) VALUES (?, ?, ?)";
    private final String COMPLETE_QUIZ_ATTEMPT = "UPDATE quizattempt SET endTime = ?,timeTakenMinutes = ?, marks = ? WHERE id = ?";
//    private static final String HAS_COMPLETED_ATTEMPT_SQL = "SELECT COUNT(*) FROM quizattempt WHERE quizid = ? AND studentid = ? AND endtime IS NOT NULL";
    private final String GET_ATTEMP_BY_USR_AND_QUIZ_ID = "SELECT * FROM quizattempt WHERE studentid = ? AND quizid = ?";

    public int createNewQuizAttempt(QuizAttempt quizAttempt) {
        int generatedQuizAttemptId = -1;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(false);  // Start transaction

            stmt = connection.prepareStatement(CREATE_NEW_QUIZ_ATTEMPT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, quizAttempt.getStudentId());
            stmt.setInt(2, quizAttempt.getQuizId());
            stmt.setTimestamp(3, quizAttempt.getStartTime());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedQuizAttemptId = generatedKeys.getInt(1);
                }
            }

            connection.commit();  // Commit transaction
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();  // Rollback in case of error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            //close the connection to prevent memory leak
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.setAutoCommit(true);  // Reset auto-commit
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return generatedQuizAttemptId;
    }

    public QuizAttempt getAttemptById(int id) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            stmt = connection.prepareStatement(GET_ATTEMPT_BY_ATTEMPT_ID);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            QuizAttempt quizAttempt = new QuizAttempt();

            while (rs.next()) {
                quizAttempt.setId(rs.getInt("id"));
                quizAttempt.setQuizId(rs.getInt("quizId"));
                quizAttempt.setStudentId(rs.getString("studentId"));
                quizAttempt.setStartTime(rs.getTimestamp("startTime"));
                quizAttempt.setEndTime(rs.getTimestamp("endTime"));
                quizAttempt.setMarks(rs.getDouble("marks"));
            }

            return quizAttempt;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public QuizAttempt getAttemptByUserIdAndQuizId(String userId, int quizId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            stmt = connection.prepareStatement(GET_ATTEMP_BY_USR_AND_QUIZ_ID);
            stmt.setString(1, userId);
            stmt.setInt(2, quizId);

            rs = stmt.executeQuery();

            QuizAttempt quizAttempt = new QuizAttempt();

            while (rs.next()) {
                quizAttempt.setId(rs.getInt("id"));
                quizAttempt.setQuizId(rs.getInt("quizId"));
                quizAttempt.setStudentId(rs.getString("studentId"));
                quizAttempt.setStartTime(rs.getTimestamp("startTime"));
                quizAttempt.setEndTime(rs.getTimestamp("endTime"));
                quizAttempt.setMarks(rs.getDouble("marks"));
            }

            return quizAttempt;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<QuizAttempt> getAllQuizAttempts(int quizId) {
        List<QuizAttempt> quizAttempts = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            stmt = connection.prepareStatement(GET_QUIZ_ATTEMPTS_BY_QUIZ_ID);
            stmt.setInt(1, quizId);

            rs = stmt.executeQuery();

            while (rs.next()) {
                QuizAttempt quizAttempt = new QuizAttempt();
                quizAttempt.setId(rs.getInt("id"));
                quizAttempt.setQuizId(rs.getInt("quizId"));
                quizAttempt.setStudentId(rs.getString("studentId"));
                quizAttempt.setStartTime(rs.getTimestamp("startTime"));
                quizAttempt.setEndTime(rs.getTimestamp("endTime"));
                quizAttempt.setMarks(rs.getDouble("marks"));

                quizAttempts.add(quizAttempt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return quizAttempts;
    }

    public boolean completeQuiz(int quizAttemptId, Timestamp endTime, int timeTaken, double marks) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(false);

            stmt = connection.prepareStatement(COMPLETE_QUIZ_ATTEMPT);
            stmt.setTimestamp(1, endTime);
            stmt.setInt(2, timeTaken);
            stmt.setDouble(3, marks);
            stmt.setInt(4, quizAttemptId);

            int affectedRows = stmt.executeUpdate();

            connection.commit();  // Commit transaction
            return affectedRows > 0;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();  // Rollback in case of error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            // Close the connection to prevent memory leak
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.setAutoCommit(true);  // Reset auto-commit
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasCompletedAttempt(int quizId, String studentId) {
        String query = "SELECT COUNT(*) FROM quizAttempt WHERE quizId = ? AND studentId = ? AND endTime IS NOT NULL";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quizId);
            stmt.setString(2, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
