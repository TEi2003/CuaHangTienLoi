package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.DatabaseConnection;
import Entity.Customer;

public class CustomerDAO {
    
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customers";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("CustomerID"));
                customer.setCustomerName(rs.getString("CustomerName"));
                customer.setPhone(rs.getString("Phone"));
                customer.setAddress(rs.getString("Address"));
                customer.setEmail(rs.getString("Email"));
                
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách khách hàng: " + e.getMessage());
        }
        
        return customers;
    }
    
    public Customer getCustomerById(int customerId) {
        Customer customer = null;
        String query = "SELECT * FROM Customers WHERE CustomerID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, customerId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setCustomerId(rs.getInt("CustomerID"));
                    customer.setCustomerName(rs.getString("CustomerName"));
                    customer.setPhone(rs.getString("Phone"));
                    customer.setAddress(rs.getString("Address"));
                    customer.setEmail(rs.getString("Email"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin khách hàng: " + e.getMessage());
        }
        
        return customer;
    }
    
    public List<Customer> searchCustomersByName(String keyword) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customers WHERE CustomerName LIKE ? OR Phone LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerId(rs.getInt("CustomerID"));
                    customer.setCustomerName(rs.getString("CustomerName"));
                    customer.setPhone(rs.getString("Phone"));
                    customer.setAddress(rs.getString("Address"));
                    customer.setEmail(rs.getString("Email"));
                    
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm khách hàng: " + e.getMessage());
        }
        
        return customers;
    }
    
    public boolean addCustomer(Customer customer) {
        String query = "INSERT INTO Customers (CustomerName, Phone, Address, Email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, customer.getEmail());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setCustomerId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm khách hàng: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean updateCustomer(Customer customer) {
        String query = "UPDATE Customers SET CustomerName = ?, Phone = ?, Address = ?, Email = ? WHERE CustomerID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, customer.getEmail());
            pstmt.setInt(5, customer.getCustomerId());
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật khách hàng: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean deleteCustomer(int customerId) {
        String query = "DELETE FROM Customers WHERE CustomerID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, customerId);
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa khách hàng: " + e.getMessage());
        }
        
        return false;
    }
    
    public Customer getDefaultCustomer() {
        // Trả về khách hàng mặc định (khách lẻ) hoặc tạo mới nếu chưa có
        Customer customer = getCustomerById(1);
        
        if (customer == null) {
            customer = new Customer();
            customer.setCustomerName("Khách lẻ");
            customer.setPhone("N/A");
            customer.setAddress("N/A");
            customer.setEmail("N/A");
            
            addCustomer(customer);
        }
        
        return customer;
    }
}