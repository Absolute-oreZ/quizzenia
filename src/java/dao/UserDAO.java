package dao;

import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO {

    private final String CREATE_USER = "INSERT INTO users (id, name, email, password, role, classId) VALUES (?, ?, ?, ?, ?, ?)";
    private final String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private final String GET_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    private final String GET_STUDENTS_BY_CLASS = "SELECT * FROM users WHERE role = 'Student' AND classId = ?";
    private final String GET_STUDENT_NAME_BY_ID = "SELECT name FROM users WHERE id = ?";
    private final String UPDATE_PASSWORD = "UPDATE users SET password = ? WHERE id = ?";
    private final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";

    public boolean createUser(User user) {
        try (Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(CREATE_USER)) {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.setInt(6, user.getClassId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserById(String id) {
        User user = new User();
        try (Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(GET_USER_BY_ID)) {
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setClass_id(rs.getInt("classId"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        User user = new User();
        try (Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(GET_USER_BY_EMAIL_AND_PASSWORD)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setClass_id(rs.getInt("classId"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getStudentsByClass(int classId) {
        List<User> students = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(GET_STUDENTS_BY_CLASS)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User student = new User();
                student.setId(rs.getString("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setPassword(rs.getString("password"));
                student.setClass_id(rs.getInt("classId"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean deleteUserById(String id) {
        try (Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(DELETE_USER_BY_ID)) {
            stmt.setString(1, id);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getStudentNameById(String studentId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String studentName = null;

        try {
            connection = DBUtil.getConnection();
            stmt = connection.prepareStatement(GET_STUDENT_NAME_BY_ID);
            stmt.setString(1, studentId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                studentName = rs.getString("name");
            }
            
            System.out.println(studentName);
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

        return studentName;
    }
    
    public boolean updateUserPassword(String userId, String newPassword) {
    try (Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE_PASSWORD)) {
        stmt.setString(1, newPassword);
        stmt.setString(2, userId);
        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
