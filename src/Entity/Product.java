package Entity;

public class Product {
    private int productId;
    private String productName;
    private String productType;
    private double price;
    private int quantity;
    
    public Product() {
    }
    
    public Product(int productId, String productName, String productType, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.price = price;
        this.quantity = quantity;
    }
    
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getProductType() {
        return productType;
    }
    
    public void setProductType(String productType) {
        this.productType = productType;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return productName;
    }
}