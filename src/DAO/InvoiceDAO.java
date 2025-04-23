package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ConnectDB.DatabaseConnection;
import Entity.Invoice;
import Entity.InvoiceItem;

public class InvoiceDAO {
    
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM Invoices ORDER BY InvoiceDate DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setInvoiceId(rs.getInt("InvoiceID"));
                invoice.setInvoiceCode(rs.getString("InvoiceCode"));
                invoice.setInvoiceDate(rs.getTimestamp("InvoiceDate"));
                invoice.setEmployeeId(rs.getInt("EmployeeID"));
                invoice.setCustomerId(rs.getInt("CustomerID"));
                invoice.setTotalAmount(rs.getDouble("TotalAmount"));
                invoice.setDiscount(rs.getDouble("Discount"));
                invoice.setFinalAmount(rs.getDouble("FinalAmount"));
                
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        }
        
        return invoices;
    }
    
    public Invoice getInvoiceById(int invoiceId) {
        Invoice invoice = null;
        String query = "SELECT * FROM Invoices WHERE InvoiceID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, invoiceId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    invoice = new Invoice();
                    invoice.setInvoiceId(rs.getInt("InvoiceID"));
                    invoice.setInvoiceCode(rs.getString("InvoiceCode"));
                    invoice.setInvoiceDate(rs.getTimestamp("InvoiceDate"));
                    invoice.setEmployeeId(rs.getInt("EmployeeID"));
                    invoice.setCustomerId(rs.getInt("CustomerID"));
                    invoice.setTotalAmount(rs.getDouble("TotalAmount"));
                    invoice.setDiscount(rs.getDouble("Discount"));
                    invoice.setFinalAmount(rs.getDouble("FinalAmount"));
                    
                    // Load invoice items
                    invoice.setItems(getInvoiceItems(invoice.getInvoiceId()));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin hóa đơn: " + e.getMessage());
        }
        
        return invoice;
    }
    
    public List<Invoice> searchInvoicesByCode(String keyword) {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM Invoices WHERE InvoiceCode LIKE ? ORDER BY InvoiceDate DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, "%" + keyword + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setInvoiceId(rs.getInt("InvoiceID"));
                    invoice.setInvoiceCode(rs.getString("InvoiceCode"));
                    invoice.setInvoiceDate(rs.getTimestamp("InvoiceDate"));
                    invoice.setEmployeeId(rs.getInt("EmployeeID"));
                    invoice.setCustomerId(rs.getInt("CustomerID"));
                    invoice.setTotalAmount(rs.getDouble("TotalAmount"));
                    invoice.setDiscount(rs.getDouble("Discount"));
                    invoice.setFinalAmount(rs.getDouble("FinalAmount"));
                    
                    invoices.add(invoice);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm hóa đơn: " + e.getMessage());
        }
        
        return invoices;
    }
    
    public List<Invoice> searchInvoicesByDate(Date date) {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM Invoices WHERE DATE(InvoiceDate) = DATE(?) ORDER BY InvoiceDate DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setDate(1, new java.sql.Date(date.getTime()));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setInvoiceId(rs.getInt("InvoiceID"));
                    invoice.setInvoiceCode(rs.getString("InvoiceCode"));
                    invoice.setInvoiceDate(rs.getTimestamp("InvoiceDate"));
                    invoice.setEmployeeId(rs.getInt("EmployeeID"));
                    invoice.setCustomerId(rs.getInt("CustomerID"));
                    invoice.setTotalAmount(rs.getDouble("TotalAmount"));
                    invoice.setDiscount(rs.getDouble("Discount"));
                    invoice.setFinalAmount(rs.getDouble("FinalAmount"));
                    
                    invoices.add(invoice);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm hóa đơn theo ngày: " + e.getMessage());
        }
        
        return invoices;
    }
    
    public int createInvoice(Invoice invoice) {
        String query = "INSERT INTO Invoices (InvoiceCode, InvoiceDate, EmployeeID, CustomerID, TotalAmount, Discount, FinalAmount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, invoice.getInvoiceCode());
            pstmt.setTimestamp(2, new java.sql.Timestamp(invoice.getInvoiceDate().getTime()));
            pstmt.setInt(3, invoice.getEmployeeId());
            pstmt.setInt(4, invoice.getCustomerId());
            pstmt.setDouble(5, invoice.getTotalAmount());
            pstmt.setDouble(6, invoice.getDiscount());
            pstmt.setDouble(7, invoice.getFinalAmount());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int invoiceId = generatedKeys.getInt(1);
                        invoice.setInvoiceId(invoiceId);
                        
                        // Add invoice items
                        addInvoiceItems(invoice);
                        
                        // Update product quantities
                        updateProductQuantities(invoice);
                        
                        return invoiceId;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo hóa đơn: " + e.getMessage());
        }
        
        return -1;
    }
    
    private void addInvoiceItems(Invoice invoice) {
        String query = "INSERT INTO InvoiceItems (InvoiceID, ProductID, Quantity, UnitPrice, TotalPrice) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            for (InvoiceItem item : invoice.getItems()) {
                pstmt.setInt(1, invoice.getInvoiceId());
                pstmt.setInt(2, item.getProductId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.setDouble(4, item.getUnitPrice());
                pstmt.setDouble(5, item.getTotalPrice());
                
                pstmt.addBatch();
            }
            
            pstmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm chi tiết hóa đơn: " + e.getMessage());
        }
    }
    
    private void updateProductQuantities(Invoice invoice) {
        String query = "UPDATE Products SET Quantity = Quantity - ? WHERE ProductID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            for (InvoiceItem item : invoice.getItems()) {
                pstmt.setInt(1, item.getQuantity());
                pstmt.setInt(2, item.getProductId());
                
                pstmt.addBatch();
            }
            
            pstmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật số lượng sản phẩm: " + e.getMessage());
        }
    }
    
    public List<InvoiceItem> getInvoiceItems(int invoiceId) {
        List<InvoiceItem> items = new ArrayList<>();
        String query = "SELECT i.*, p.ProductName FROM InvoiceItems i JOIN Products p ON i.ProductID = p.ProductID WHERE i.InvoiceID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, invoiceId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    InvoiceItem item = new InvoiceItem();
                    item.setInvoiceItemId(rs.getInt("InvoiceItemID"));
                    item.setInvoiceId(rs.getInt("InvoiceID"));
                    item.setProductId(rs.getInt("ProductID"));
                    item.setQuantity(rs.getInt("Quantity"));
                    item.setUnitPrice(rs.getDouble("UnitPrice"));
                    item.setTotalPrice(rs.getDouble("TotalPrice"));
                    item.setProductName(rs.getString("ProductName"));
                    
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy chi tiết hóa đơn: " + e.getMessage());
        }
        
        return items;
    }
    
    public boolean deleteInvoice(int invoiceId) {
        // First delete invoice items
        String deleteItemsQuery = "DELETE FROM InvoiceItems WHERE InvoiceID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteItemsQuery)) {
            
            pstmt.setInt(1, invoiceId);
            pstmt.executeUpdate();
            
            // Then delete invoice
            String deleteInvoiceQuery = "DELETE FROM Invoices WHERE InvoiceID = ?";
            
            try (PreparedStatement pstmt2 = conn.prepareStatement(deleteInvoiceQuery)) {
                pstmt2.setInt(1, invoiceId);
                
                int affectedRows = pstmt2.executeUpdate();
                
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa hóa đơn: " + e.getMessage());
        }
        
        return false;
    }
}