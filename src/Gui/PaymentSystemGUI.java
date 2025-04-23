package Gui;

import javax.swing.*;
import java.awt.*;

public class PaymentSystemGUI extends JFrame {
    private JTextField searchField;
    private JTextField quantityField;
    private JTable productTable;
    private JTable invoiceTable;
    private JLabel invoiceCodeLabel;
    private JLabel invoiceDateLabel;
    private JLabel totalAmountLabel;

    public PaymentSystemGUI() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Thanh toán");
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

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Tìm sản phẩm:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Số lượng:"));
        quantityField = new JTextField(5);
        searchPanel.add(quantityField);
        JButton addButton = new JButton("Thêm");
        searchPanel.add(addButton);

        // Product table
        productTable = new JTable(new Object[][]{}, new String[]{"Mã SP", "Tên SP", "Số lượng", "Loại SP", "Giá"});
        JScrollPane productScrollPane = new JScrollPane(productTable);

        // Left panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        leftPanel.add(productScrollPane, BorderLayout.CENTER);

        // Invoice header panel
        JPanel invoiceHeaderPanel = new JPanel(new GridLayout(3, 2));
        invoiceHeaderPanel.add(new JLabel("Mã hóa đơn:"));
        invoiceCodeLabel = new JLabel("");
        invoiceHeaderPanel.add(invoiceCodeLabel);
        invoiceHeaderPanel.add(new JLabel("Ngày lập:"));
        invoiceDateLabel = new JLabel("");
        invoiceHeaderPanel.add(invoiceDateLabel);
        invoiceHeaderPanel.add(new JLabel("Tổng tiền:"));
        totalAmountLabel = new JLabel("");
        invoiceHeaderPanel.add(totalAmountLabel);

        // Invoice table
        invoiceTable = new JTable(new Object[][]{}, new String[]{"Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"});
        JScrollPane invoiceScrollPane = new JScrollPane(invoiceTable);

        // Button panel
        JPanel invoiceButtonPanel = new JPanel(new FlowLayout());
        JButton exportButton = new JButton("Xuất");
        JButton deleteButton = new JButton("Xóa");
        invoiceButtonPanel.add(exportButton);
        invoiceButtonPanel.add(deleteButton);

        // Right panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(invoiceHeaderPanel, BorderLayout.NORTH);
        rightPanel.add(invoiceScrollPane, BorderLayout.CENTER);
        rightPanel.add(invoiceButtonPanel, BorderLayout.SOUTH);

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(450);
        add(splitPane, BorderLayout.CENTER);
    }
}