package Entity;

import java.util.Date;

public class User {
    private int userId;
    private String username;
    private String fullName;
    private String role;
    private Date lastLogin;
    private int status;
    
    public User() {
    }
    
    public User(int userId, String username, String fullName, String role, Date lastLogin, int status) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.role = role;
        this.lastLogin = lastLogin;
        this.status = status;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public Date getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return fullName;
    }
}