package dao;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Quiz;
import util.DBUtil;

public class QuizDAO {

    private final String CREATE_NEW_QUIZ = "INSERT INTO quiz (name, description, classId, noOfQuestion, startTime, endTime, durationMinutes) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String GET_QUIZZES_BY_CLASS_ID = "SELECT * FROM quiz WHERE classId = ? ORDER BY startTime DESC";
    private final String GET_QUIZ_BY_ID = "SELECT * FROM quiz WHERE id = ?";
    private final String UPDATE_QUIZ_BY_ID = "UPDATE quiz SET name = ?, description = ?, noOfQuestion = ?, startTime = ?, endTime = ?, durationMinutes = ? WHERE id = ?";
    private final String DELETE_QUIZ = "DELETE FROM quiz where id = ?";

    public int createQuiz(Quiz quiz) {
        int generatedQuizId = -1;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(false);  // Start transaction

            stmt = connection.prepareStatement(CREATE_NEW_QUIZ, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, quiz.getName());
            stmt.setString(2, quiz.getDescription());
            stmt.setInt(3, quiz.getClassId());
            stmt.setInt(4, quiz.getNoOfQuestion());
            stmt.setTimestamp(5, quiz.getStartTime());
            stmt.setTimestamp(6, quiz.getEndTime());
            stmt.setInt(7, quiz.getDurationMinutes());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedQuizId = generatedKeys.getInt(1);
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
        return generatedQuizId;
    }

    public List<Quiz> getQuizzesByClassId(int classId) throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = DBUtil.getConnection();
        try {
            // Prepare the SQL query
            statement = connection.prepareStatement(GET_QUIZZES_BY_CLASS_ID);
            statement.setInt(1, classId);

            // Execute the query
            resultSet = statement.executeQuery();

            // Process the results
            while (resultSet.next()) {
                // Create a Quiz object from the result set
                Quiz quiz = new Quiz();
                quiz.setId(resultSet.getInt("id"));
                quiz.setName(resultSet.getString("name"));
                quiz.setDescription(resultSet.getString("description"));
                quiz.setClassId(resultSet.getInt("classId"));
                quiz.setNoOfQuestion(resultSet.getInt("noOfQuestion"));
                quiz.setStartTime(resultSet.getTimestamp("startTime"));
                quiz.setEndTime(resultSet.getTimestamp("endTime"));
                quiz.setDurationMinutes(resultSet.getInt("durationMinutes"));
                quiz.setAttempts(resultSet.getInt("attempts"));
                quiz.setStatus(resultSet.getString("status"));
                quiz.setCreatedAt(resultSet.getTimestamp("createdAt"));
                quiz.setLastEditedAt(resultSet.getTimestamp("lastEditedAt"));

                // Add the Quiz object to the list
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Close connection
            if (connection != null) {
                connection.close();
            }
        }

        return quizzes;
    }

    public Quiz getQuizById(int quizId) {
        Quiz quiz = new Quiz();
        try (Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(GET_QUIZ_BY_ID)) {
            stmt.setInt(1, quizId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                quiz.setId(quizId);
                quiz.setName(rs.getString("name"));
                quiz.setDescription(rs.getString("description"));
                quiz.setClassId(rs.getInt("classId"));
                quiz.setNoOfQuestion(rs.getInt("noOfQuestion"));
                quiz.setStartTime(rs.getTimestamp("startTime"));
                quiz.setEndTime(rs.getTimestamp("endTime"));
                quiz.setDurationMinutes(rs.getInt("durationMinutes"));
                quiz.setAttempts(rs.getInt("attempts"));
                quiz.setStatus(rs.getString("status"));
                quiz.setCreatedAt(rs.getTimestamp("createdAt"));
                quiz.setLastEditedAt(rs.getTimestamp("lastEditedAt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quiz;
    }

    public boolean updateQuizById(Quiz quiz) {
        try (Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE_QUIZ_BY_ID)) {
            stmt.setString(1, quiz.getName());
            stmt.setString(2, quiz.getDescription());
            stmt.setInt(3, quiz.getNoOfQuestion());
            stmt.setTimestamp(4, quiz.getStartTime());
            stmt.setTimestamp(5, quiz.getEndTime());
            stmt.setInt(6, quiz.getDurationMinutes());
            stmt.setInt(7, quiz.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteQuiz(int quizId) {
        try (Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(DELETE_QUIZ)) {
            stmt.setInt(1, quizId);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
