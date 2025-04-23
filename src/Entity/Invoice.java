package Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice {
    private int invoiceId;
    private String invoiceCode;
    private Date invoiceDate;
    private int employeeId;
    private int customerId;
    private double totalAmount;
    private double discount;
    private double finalAmount;
    private List<InvoiceItem> items;
    
    public Invoice() {
        this.items = new ArrayList<>();
    }
    
    public int getInvoiceId() {
        return invoiceId;
    }
    
    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    public String getInvoiceCode() {
        return invoiceCode;
    }
    
    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }
    
    public Date getInvoiceDate() {
        return invoiceDate;
    }
    
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public double getDiscount() {
        return discount;
    }
    
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    public double getFinalAmount() {
        return finalAmount;
    }
    
    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }
    
    public List<InvoiceItem> getItems() {
        return items;
    }
    
    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }
    
    public void addItem(InvoiceItem item) {
        this.items.add(item);
    }
}