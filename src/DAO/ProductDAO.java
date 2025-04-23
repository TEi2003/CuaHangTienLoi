package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.DatabaseConnection;
import Entity.Product;

public class ProductDAO {
    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("ProductID"));
                product.setProductName(rs.getString("ProductName"));
                product.setProductType(rs.getString("ProductType"));
                product.setPrice(rs.getDouble("Price"));
                product.setQuantity(rs.getInt("Quantity"));
                
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách sản phẩm: " + e.getMessage());
        }
        
        return products;
    }
    
    public Product getProductById(int productId) {
        Product product = null;
        String query = "SELECT * FROM Products WHERE ProductID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, productId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setProductType(rs.getString("ProductType"));
                    product.setPrice(rs.getDouble("Price"));
                    product.setQuantity(rs.getInt("Quantity"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin sản phẩm: " + e.getMessage());
        }
        
        return product;
    }
    
    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE ProductName LIKE ? OR ProductType LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setProductType(rs.getString("ProductType"));
                    product.setPrice(rs.getDouble("Price"));
                    product.setQuantity(rs.getInt("Quantity"));
                    
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
        }
        
        return products;
    }
    
    public boolean addProduct(Product product) {
        String query = "INSERT INTO Products (ProductName, ProductType, Price, Quantity) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getProductType());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        product.setProductId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean updateProduct(Product product) {
        String query = "UPDATE Products SET ProductName = ?, ProductType = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getProductType());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setInt(5, product.getProductId());
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM Products WHERE ProductID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, productId);
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
        
        return false;
    }
    
    public boolean updateProductQuantity(int productId, int quantity) {
        String query = "UPDATE Products SET Quantity = Quantity - ? WHERE ProductID = ? AND Quantity >= ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, quantity);
            
            int affectedRows = pstmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật số lượng sản phẩm: " + e.getMessage());
        }
        
        return false;
    }
}