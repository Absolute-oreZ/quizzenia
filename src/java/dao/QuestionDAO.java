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
    import java.util.ArrayList;
    import java.util.List;
    import model.Question;
    import util.DBUtil;

    /**
     *
     * @author User
     */
    public class QuestionDAO {

        private final String SAVE_QUIZ = "INSERT INTO question (quizId, questionText) VALUES (?, ?)";
        private final String GET_CURRENT_QUESTION_COUNT = "SELECT COUNT(id) AS questionCount FROM question WHERE quizId = ?";
        private final String GET_QUESTIONS_BY_QUIZ_ID = "SELECT * FROM question WHERE quizId = ?";

        public int saveQuestion(int quizId, String questionText) {
            int generatedQuestionId = -1;
            Connection connection = null;
            PreparedStatement stmt = null;
            ResultSet generatedKeys = null;

            try {
                connection = DBUtil.getConnection();
                connection.setAutoCommit(false);  // Start transaction

                stmt = connection.prepareStatement(SAVE_QUIZ, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, quizId);
                stmt.setString(2, questionText);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    generatedKeys = stmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        generatedQuestionId = generatedKeys.getInt(1);
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
            return generatedQuestionId;
        }

        public int getQuestionCountForQuiz(int quizId) {
            int currentQuesCount = 0;
            Connection connection = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                connection = DBUtil.getConnection();
                stmt = connection.prepareStatement(GET_CURRENT_QUESTION_COUNT);
                stmt.setInt(1, quizId);

                rs = stmt.executeQuery();

                if (rs.next()) {
                    currentQuesCount = rs.getInt("questionCount");
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
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            return currentQuesCount;
        }

        public List<Question> getQuestionsByQuiz(int quizId) {
            List<Question> questions = new ArrayList<>();

            Connection connection = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                connection = DBUtil.getConnection();
                stmt = connection.prepareStatement(GET_QUESTIONS_BY_QUIZ_ID);

                stmt.setInt(1, quizId);
                
                rs = stmt.executeQuery();

                while(rs.next()){
                    Question ques = new Question();
                    ques.setId(rs.getInt("id"));
                    ques.setQuizId(quizId);
                    ques.setQuestionText(rs.getString("questionText"));
                    questions.add(ques);
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

            return questions;
        }
    }
