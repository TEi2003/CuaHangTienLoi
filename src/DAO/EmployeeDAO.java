package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.DatabaseConnection;
import Entity.Employee;

public class EmployeeDAO {
    
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employees";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getInt("EmployeeID"));
                employee.setEmployeeName(rs.getString("EmployeeName"));
                employee.setStartDate(rs.getDate("StartDate"));
                employee.setPhone(rs.getString("Phone"));
                employee.setPosition(rs.getString("Position"));
                employee.setSalary(rs.getDouble("Salary"));
                employee.setGender(rs.getString("Gender"));
                
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
        }
        
        return employees;
    }
    
    public Employee getEmployeeById(int employeeId) {
        Employee employee = null;
        String query = "SELECT * FROM Employees WHERE EmployeeID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, employeeId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    employee = new Employee();
                    employee.setEmployeeId(rs.getInt("EmployeeID"));
                    employee.setEmployeeName(rs.getString("EmployeeName"));
                    employee.setStartDate(rs.getDate("StartDate"));
                    employee.setPhone(rs.getString("Phone"));
                    employee.setPosition(rs.getString("Position"));
                    employee.setSalary(rs.getDouble("Salary"));
                    employee.setGender(rs.getString("Gender"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin nhân viên: " + e.getMessage());
        }
        
        return employee;
    }
    
    public List<Employee> searchEmployeesByName(String keyword) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employees WHERE EmployeeName LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setEmployeeId(rs.getInt("EmployeeID"));
                    employee.setEmployeeName(rs.getString("EmployeeName"));
                    employee.setStartDate(rs.getDate("StartDate"));
                    employee.setPhone(rs.getString("Phone"));
                    employee.setPosition(rs.getString("Position"));
                    employee.setSalary(rs.getDouble("Salary"));
                    employee.setGender(rs.getString("Gender"));
                    
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        }
        
        return employees;
    }
    
    public boolean addEmployee(Employee employee) {
        String query = "INSERT INTO Employees (EmployeeName, StartDate, Phone, Position, Salary, Gender) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, employee.getEmployeeName());
            pstmt.setDate(2, new java.sql.Date(employee.getStartDate().getTime()));
            pstmt.setString(3, employee.getPhone());
            pstmt.setString(4, employee.getPosition());
            pstmt.setDouble(5, employee.getSalary());
            pstmt.setString(6, employee.getGender());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setEmployeeId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhân viên: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean updateEmployee(Employee employee) {
        String query = "UPDATE Employees SET EmployeeName = ?, StartDate = ?, Phone = ?, Position = ?, Salary = ?, Gender = ? WHERE EmployeeID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, employee.getEmployeeName());
            pstmt.setDate(2, new java.sql.Date(employee.getStartDate().getTime()));
            pstmt.setString(3, employee.getPhone());
            pstmt.setString(4, employee.getPosition());
            pstmt.setDouble(5, employee.getSalary());
            pstmt.setString(6, employee.getGender());
            pstmt.setInt(7, employee.getEmployeeId());
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật nhân viên: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean deleteEmployee(int employeeId) {
        String query = "DELETE FROM Employees WHERE EmployeeID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, employeeId);
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhân viên: " + e.getMessage());
        }
        
        return false;
    }
}