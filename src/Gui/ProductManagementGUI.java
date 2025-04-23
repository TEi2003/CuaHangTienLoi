package Gui;

import javax.swing.*;
import java.awt.*;

public class ProductManagementGUI extends JFrame {
    private JTextField productIdField;
    private JTextField productNameField;
    private JTextField priceField;
    private JTextField quantityField;
    private JComboBox<String> productTypeComboBox;
    private JTable productTable;

    public ProductManagementGUI() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Quản lý sản phẩm");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Navigation panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(new Color(23, 107, 135));
        JButton staffButton = new JButton("Quản lý nhân viên");
        JButton productButton = new JButton("Quản lý sản phẩm");
        JButton paymentButton = new JButton("Thanh toán");
        JButton invoiceButton = new JButton("Hóa đơn");
        JButton logoutButton = new JButton("Đăng xuất");
        navigationPanel.add(staffButton);
        navigationPanel.add(productButton);
        navigationPanel.add(paymentButton);
        navigationPanel.add(invoiceButton);
        navigationPanel.add(logoutButton);
        add(navigationPanel, BorderLayout.NORTH);

        // Main panel with BoxLayout (vertical)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.add(new JLabel("Mã sản phẩm:"));
        productIdField = new JTextField();
        formPanel.add(productIdField);
        formPanel.add(new JLabel("Tên sản phẩm:"));
        productNameField = new JTextField();
        formPanel.add(productNameField);
        formPanel.add(new JLabel("Giá:"));
        priceField = new JTextField();
        formPanel.add(priceField);
        formPanel.add(new JLabel("Số lượng:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Loại sản phẩm:"));
        productTypeComboBox = new JComboBox<>(new String[]{"Đồ ăn", "Nước uống"});
        formPanel.add(productTypeComboBox);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Thêm");
        JButton updateButton = new JButton("Cập nhật");
        JButton deleteButton = new JButton("Xóa");
        JButton searchButton = new JButton("Tìm kiếm");
        JButton printButton = new JButton("In danh sách");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(printButton);

        // Table panel
        productTable = new JTable(new Object[][]{}, new String[]{"Mã SP", "Tên SP", "Số lượng", "Loại SP", "Giá"});
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Add components to main panel
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(10)); // Spacer
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10)); // Spacer
        mainPanel.add(scrollPane);

        // Add main panel to frame
        add(mainPanel, BorderLayout.CENTER);
    }
}