package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ConnectDB.DatabaseConnection;
import Entity.User;

public class UserDAO {
    
    public User checkLogin(String username, String password) {
        User user = null;
        String query = "SELECT * FROM Users WHERE Username = ? AND Password = ? AND Status = 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setFullName(rs.getString("FullName"));
                    user.setRole(rs.getString("Role"));
                    user.setLastLogin(rs.getTimestamp("LastLogin"));
                    user.setStatus(rs.getInt("Status"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra đăng nhập: " + e.getMessage());
        }
        
        return user;
    }
    
    public boolean updateLastLogin(int userId) {
        String query = "UPDATE Users SET LastLogin = ? WHERE UserID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setTimestamp(1, new Timestamp(new Date().getTime()));
            pstmt.setInt(2, userId);
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thời gian đăng nhập: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        // Kiểm tra mật khẩu cũ
        String checkQuery = "SELECT * FROM Users WHERE UserID = ? AND Password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            
            checkStmt.setInt(1, userId);
            checkStmt.setString(2, oldPassword);
            
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Mật khẩu cũ đúng, cập nhật mật khẩu mới
                    String updateQuery = "UPDATE Users SET Password = ? WHERE UserID = ?";
                    
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, newPassword);
                        updateStmt.setInt(2, userId);
                        
                        int affectedRows = updateStmt.executeUpdate();
                        
                        return affectedRows > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đổi mật khẩu: " + e.getMessage());
        }
        
        return false;
    }
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setFullName(rs.getString("FullName"));
                user.setRole(rs.getString("Role"));
                user.setLastLogin(rs.getTimestamp("LastLogin"));
                user.setStatus(rs.getInt("Status"));
                
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách người dùng: " + e.getMessage());
        }
        
        return users;
    }
    
    public User getUserById(int userId) {
        User user = null;
        String query = "SELECT * FROM Users WHERE UserID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setFullName(rs.getString("FullName"));
                    user.setRole(rs.getString("Role"));
                    user.setLastLogin(rs.getTimestamp("LastLogin"));
                    user.setStatus(rs.getInt("Status"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin người dùng: " + e.getMessage());
        }
        
        return user;
    }
    
    public boolean addUser(User user, String password) {
        String query = "INSERT INTO Users (Username, Password, FullName, Role, Status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, password);
            pstmt.setString(3, user.getFullName());
            pstmt.setString(4, user.getRole());
            pstmt.setInt(5, user.getStatus());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm người dùng: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean updateUser(User user) {
        String query = "UPDATE Users SET Username = ?, FullName = ?, Role = ?, Status = ? WHERE UserID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getFullName());
            pstmt.setString(3, user.getRole());
            pstmt.setInt(4, user.getStatus());
            pstmt.setInt(5, user.getUserId());
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật người dùng: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE UserID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa người dùng: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean resetPassword(int userId, String newPassword) {
        String query = "UPDATE Users SET Password = ? WHERE UserID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi đặt lại mật khẩu: " + e.getMessage());
        }
        
        return false;
    }
}