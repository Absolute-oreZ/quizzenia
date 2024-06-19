/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Answer;
import util.DBUtil;

/**
 *
 * @author User
 */
public class AnswerDAO {

    private final String SAVE_ANSWER = "insert into answer (questionId, answerText, isCorrect) VALUES (?, ?, ?)";
    private final String GET_ANSWERS_BY_QUESTION_ID = "SELECT * FROM answer WHERE questionId = ?";

    public boolean saveAnswer(Answer answer) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DBUtil.getConnection();
            stmt = connection.prepareStatement(SAVE_ANSWER);

            stmt.setInt(1, answer.getQuestionId());
            stmt.setString(2, answer.getAnswerText());
            stmt.setInt(3, (answer.isIsCorrect()) ? 1 : 0);
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                stmt.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Answer> getAnswersByQuestionId(int questionId) {
        List<Answer> answers = new ArrayList<>();

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            stmt = connection.prepareStatement(GET_ANSWERS_BY_QUESTION_ID);
            stmt.setInt(1, questionId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Answer answer = new Answer();
                answer.setId(rs.getInt("id"));
                answer.setQuestionId(questionId);
                answer.setAnswerText(rs.getString("answerText"));
                answer.setIsCorrect(rs.getInt("isCorrect") == 1);
                answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return answers;
    }
}
