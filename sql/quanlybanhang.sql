-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS quanlybanhang CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Sử dụng cơ sở dữ liệu
USE quanlybanhang;

-- Xóa các bảng nếu đã tồn tại để tránh lỗi
DROP TABLE IF EXISTS InvoiceItems;
DROP TABLE IF EXISTS Invoices;
DROP TABLE IF EXISTS Products;
DROP TABLE IF EXISTS Employees;
DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Users;

-- Tạo bảng Users (Người dùng)
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(100) NOT NULL,
    FullName VARCHAR(100) NOT NULL,
    Role VARCHAR(20) NOT NULL,
    LastLogin DATETIME,
    Status TINYINT DEFAULT 1,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo bảng Employees (Nhân viên)
CREATE TABLE Employees (
    EmployeeID INT AUTO_INCREMENT PRIMARY KEY,
    EmployeeName VARCHAR(100) NOT NULL,
    StartDate DATE NOT NULL,
    Phone VARCHAR(20),
    Position VARCHAR(50),
    Salary DECIMAL(12, 2) DEFAULT 0,
    Gender VARCHAR(10),
    Address VARCHAR(200),
    Email VARCHAR(100),
    UserID INT,
    Status TINYINT DEFAULT 1,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE SET NULL
);

-- Tạo bảng Customers (Khách hàng)
CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerName VARCHAR(100) NOT NULL,
    Phone VARCHAR(20),
    Address VARCHAR(200),
    Email VARCHAR(100),
    Status TINYINT DEFAULT 1,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo bảng Products (Sản phẩm)
CREATE TABLE Products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(100) NOT NULL,
    ProductType VARCHAR(50),
    Price DECIMAL(12, 2) NOT NULL DEFAULT 0,
    Quantity INT NOT NULL DEFAULT 0,
    Description TEXT,
    Status TINYINT DEFAULT 1,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tạo bảng Invoices (Hóa đơn)
CREATE TABLE Invoices (
    InvoiceID INT AUTO_INCREMENT PRIMARY KEY,
    InvoiceCode VARCHAR(20) NOT NULL UNIQUE,
    InvoiceDate DATETIME NOT NULL,
    EmployeeID INT,
    CustomerID INT,
    TotalAmount DECIMAL(12, 2) NOT NULL DEFAULT 0,
    Discount DECIMAL(12, 2) DEFAULT 0,
    FinalAmount DECIMAL(12, 2) NOT NULL DEFAULT 0,
    Status TINYINT DEFAULT 1,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID) ON DELETE SET NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID) ON DELETE SET NULL
);

-- Tạo bảng InvoiceItems (Chi tiết hóa đơn)
CREATE TABLE InvoiceItems (
    InvoiceItemID INT AUTO_INCREMENT PRIMARY KEY,
    InvoiceID INT NOT NULL,
    ProductID INT,
    Quantity INT NOT NULL DEFAULT 0,
    UnitPrice DECIMAL(12, 2) NOT NULL DEFAULT 0,
    TotalPrice DECIMAL(12, 2) NOT NULL DEFAULT 0,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (InvoiceID) REFERENCES Invoices(InvoiceID) ON DELETE CASCADE,
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID) ON DELETE SET NULL
);

-- Thêm dữ liệu mẫu vào bảng Users
INSERT INTO Users (Username, Password, FullName, Role) VALUES
('admin', '123456', 'Quản trị viên', 'ADMIN'),
('nhanvien1', '123456', 'Nhân viên 1', 'EMPLOYEE'),
('nhanvien2', '123456', 'Nhân viên 2', 'EMPLOYEE');

-- Thêm dữ liệu mẫu vào bảng Employees
INSERT INTO Employees (EmployeeName, StartDate, Phone, Position, Salary, Gender, Address, Email, UserID) VALUES
('Nguyễn Văn A', '2022-01-01', '0901234567', 'Quản lý', 15000000, 'Nam', 'Hà Nội', 'nguyenvana@example.com', 1),
('Trần Thị B', '2022-02-15', '0912345678', 'Nhân viên bán hàng', 8000000, 'Nữ', 'Hồ Chí Minh', 'tranthib@example.com', 2),
('Lê Văn C', '2022-03-10', '0923456789', 'Nhân viên bán hàng', 8000000, 'Nam', 'Đà Nẵng', 'levanc@example.com', 3);

-- Thêm dữ liệu mẫu vào bảng Customers
INSERT INTO Customers (CustomerName, Phone, Address, Email) VALUES
('Khách lẻ', 'N/A', 'N/A', 'N/A'),
('Công ty TNHH ABC', '0987654321', 'Hà Nội', 'contact@abc.com'),
('Nguyễn Văn X', '0976543210', 'Hồ Chí Minh', 'nguyenvanx@example.com'),
('Trần Thị Y', '0965432109', 'Đà Nẵng', 'tranthiy@example.com');

-- Thêm dữ liệu mẫu vào bảng Products
INSERT INTO Products (ProductName, ProductType, Price, Quantity, Description) VALUES
('Cà phê đen', 'Đồ uống', 20000, 100, 'Cà phê đen nguyên chất'),
('Cà phê sữa', 'Đồ uống', 25000, 100, 'Cà phê sữa đặc'),
('Trà đào', 'Đồ uống', 30000, 50, 'Trà đào cam sả'),
('Bánh mì thịt', 'Đồ ăn', 25000, 30, 'Bánh mì kẹp thịt'),
('Bánh mì trứng', 'Đồ ăn', 20000, 30, 'Bánh mì kẹp trứng'),
('Bánh mì chả', 'Đồ ăn', 22000, 30, 'Bánh mì kẹp chả'),
('Cốc sứ', 'Đồ gia dụng', 50000, 20, 'Cốc sứ cao cấp'),
('Ly thủy tinh', 'Đồ gia dụng', 40000, 30, 'Ly thủy tinh trong suốt'),
('Đĩa sứ', 'Đồ gia dụng', 60000, 15, 'Đĩa sứ cao cấp'),
('Khăn giấy', 'Khác', 15000, 50, 'Khăn giấy cao cấp');

-- Thêm dữ liệu mẫu vào bảng Invoices
INSERT INTO Invoices (InvoiceCode, InvoiceDate, EmployeeID, CustomerID, TotalAmount, Discount, FinalAmount) VALUES
('HD20230501001', '2023-05-01 10:30:00', 2, 1, 70000, 0, 70000),
('HD20230502001', '2023-05-02 14:45:00', 2, 3, 95000, 5000, 90000),
('HD20230503001', '2023-05-03 16:20:00', 3, 1, 110000, 0, 110000);

-- Thêm dữ liệu mẫu vào bảng InvoiceItems
INSERT INTO InvoiceItems (InvoiceID, ProductID, Quantity, UnitPrice, TotalPrice) VALUES
(1, 1, 2, 20000, 40000),
(1, 4, 1, 25000, 25000),
(1, 10, 1, 15000, 15000),
(2, 2, 2, 25000, 50000),
(2, 3, 1, 30000, 30000),
(2, 5, 1, 20000, 20000),
(3, 7, 1, 50000, 50000),
(3, 8, 1, 40000, 40000),
(3, 10, 2, 15000, 30000);

-- Tạo trigger để cập nhật tổng tiền hóa đơn khi thêm/sửa/xóa chi tiết hóa đơn
DELIMITER //
CREATE TRIGGER update_invoice_total_after_insert
AFTER INSERT ON InvoiceItems
FOR EACH ROW
BEGIN
    UPDATE Invoices
    SET TotalAmount = (SELECT SUM(TotalPrice) FROM InvoiceItems WHERE InvoiceID = NEW.InvoiceID),
        FinalAmount = (SELECT SUM(TotalPrice) FROM InvoiceItems WHERE InvoiceID = NEW.InvoiceID) - Discount
    WHERE InvoiceID = NEW.InvoiceID;
END //

CREATE TRIGGER update_invoice_total_after_update
AFTER UPDATE ON InvoiceItems
FOR EACH ROW
BEGIN
    UPDATE Invoices
    SET TotalAmount = (SELECT SUM(TotalPrice) FROM InvoiceItems WHERE InvoiceID = NEW.InvoiceID),
        FinalAmount = (SELECT SUM(TotalPrice) FROM InvoiceItems WHERE InvoiceID = NEW.InvoiceID) - Discount
    WHERE InvoiceID = NEW.InvoiceID;
END //

CREATE TRIGGER update_invoice_total_after_delete
AFTER DELETE ON InvoiceItems
FOR EACH ROW
BEGIN
    UPDATE Invoices
    SET TotalAmount = (SELECT COALESCE(SUM(TotalPrice), 0) FROM InvoiceItems WHERE InvoiceID = OLD.InvoiceID),
        FinalAmount = (SELECT COALESCE(SUM(TotalPrice), 0) FROM InvoiceItems WHERE InvoiceID = OLD.InvoiceID) - Discount
    WHERE InvoiceID = OLD.InvoiceID;
END //
DELIMITER ;

-- Tạo view để hiển thị thông tin hóa đơn chi tiết
CREATE VIEW InvoiceDetailsView AS
SELECT 
    i.InvoiceID,
    i.InvoiceCode,
    i.InvoiceDate,
    e.EmployeeName,
    c.CustomerName,
    c.Phone AS CustomerPhone,
    p.ProductName,
    ii.Quantity,
    ii.UnitPrice,
    ii.TotalPrice,
    i.TotalAmount,
    i.Discount,
    i.FinalAmount
FROM 
    Invoices i
JOIN 
    Employees e ON i.EmployeeID = e.EmployeeID
JOIN 
    Customers c ON i.CustomerID = c.CustomerID
JOIN 
    InvoiceItems ii ON i.InvoiceID = ii.InvoiceID
JOIN 
    Products p ON ii.ProductID = p.ProductID;

-- Tạo view để hiển thị thông tin sản phẩm bán chạy
CREATE VIEW BestSellingProductsView AS
SELECT 
    p.ProductID,
    p.ProductName,
    p.ProductType,
    p.Price,
    SUM(ii.Quantity) AS TotalSold,
    SUM(ii.TotalPrice) AS TotalRevenue
FROM 
    Products p
JOIN 
    InvoiceItems ii ON p.ProductID = ii.ProductID
JOIN 
    Invoices i ON ii.InvoiceID = i.InvoiceID
GROUP BY 
    p.ProductID, p.ProductName, p.ProductType, p.Price
ORDER BY 
    TotalSold DESC;

-- Tạo view để hiển thị doanh thu theo ngày
CREATE VIEW DailyRevenueView AS
SELECT 
    DATE(i.InvoiceDate) AS Date,
    COUNT(DISTINCT i.InvoiceID) AS TotalInvoices,
    SUM(i.FinalAmount) AS TotalRevenue
FROM 
    Invoices i
GROUP BY 
    DATE(i.InvoiceDate)
ORDER BY 
    Date DESC;

-- Tạo stored procedure để lấy doanh thu theo khoảng thời gian
DELIMITER //
CREATE PROCEDURE GetRevenueByDateRange(IN startDate DATE, IN endDate DATE)
BEGIN
    SELECT 
        DATE(i.InvoiceDate) AS Date,
        COUNT(DISTINCT i.InvoiceID) AS TotalInvoices,
        SUM(i.FinalAmount) AS TotalRevenue
    FROM 
        Invoices i
    WHERE 
        DATE(i.InvoiceDate) BETWEEN startDate AND endDate
    GROUP BY 
        DATE(i.InvoiceDate)
    ORDER BY 
        Date;
END //
DELIMITER ;

-- Tạo stored procedure để lấy doanh thu theo tháng
DELIMITER //
CREATE PROCEDURE GetRevenueByMonth(IN year INT)
BEGIN
    SELECT 
        MONTH(i.InvoiceDate) AS Month,
        COUNT(DISTINCT i.InvoiceID) AS TotalInvoices,
        SUM(i.FinalAmount) AS TotalRevenue
    FROM 
        Invoices i
    WHERE 
        YEAR(i.InvoiceDate) = year
    GROUP BY 
        MONTH(i.InvoiceDate)
    ORDER BY 
        Month;
END //
DELIMITER ;

-- Tạo stored procedure để lấy chi tiết hóa đơn
DELIMITER //
CREATE PROCEDURE GetInvoiceDetails(IN invoiceId INT)
BEGIN
    SELECT 
        i.InvoiceID,
        i.InvoiceCode,
        i.InvoiceDate,
        e.EmployeeName,
        c.CustomerName,
        c.Phone AS CustomerPhone,
        p.ProductName,
        ii.Quantity,
        ii.UnitPrice,
        ii.TotalPrice,
        i.TotalAmount,
        i.Discount,
        i.FinalAmount
    FROM 
        Invoices i
    JOIN 
        Employees e ON i.EmployeeID = e.EmployeeID
    JOIN 
        Customers c ON i.CustomerID = c.CustomerID
    JOIN 
        InvoiceItems ii ON i.InvoiceID = ii.InvoiceID
    JOIN 
        Products p ON ii.ProductID = p.ProductID
    WHERE 
        i.InvoiceID = invoiceId;
END //
DELIMITER ;