/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.UserAnswers;
import util.DBUtil;

/**
 *
 * @author User
 */
public class UserAnswersDAO {

    private final String SAVE_USER_ANSWER = "INSERT INTO useranswers (quizAttemptId, questionId, answerId) VALUES (?, ?, ?)";
    private final String ANSWER_QUESTIONS_COUNT = "SEELECT COUNT(questionId) AS questionCount FROM useranswers WHERE quizAttemptId = ?";
    private final String VALIDATE_ANSWER = "SELECT answer.isCorrect from answer INNER JOIN useranswers ON answer.id = useranswers.answerid where useranswers.quizAttemptId = ? AND answer.questionId = ?";
    
    public int saveUserAnswer(UserAnswers userAnswers) {
        int generatedUserAnswersId = -1;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(false);  // Start transaction

            stmt = connection.prepareStatement(SAVE_USER_ANSWER, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, userAnswers.getQuizAttemtId());
            stmt.setInt(2, userAnswers.getQuestionId());
            stmt.setInt(3, userAnswers.getAnswerId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedUserAnswersId = generatedKeys.getInt(1);
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
        return generatedUserAnswersId;
    }

    public int currentAnsweringQuestion(int quizAttemptId) {
        int currentQuesCount = 0;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            stmt = connection.prepareStatement(ANSWER_QUESTIONS_COUNT);
            stmt.setInt(1, quizAttemptId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                currentQuesCount = rs.getInt("questionCount") + 1;
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

        return currentQuesCount;
    }
    
    public boolean validateAnswer(int quizAttemptId, int questionId){
        boolean isCorrect = false;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            stmt = connection.prepareStatement(VALIDATE_ANSWER);
            stmt.setInt(1, quizAttemptId);
            stmt.setInt(2, questionId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                isCorrect = rs.getInt("isCorrect") == 1;
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

        return isCorrect;
    }
}
