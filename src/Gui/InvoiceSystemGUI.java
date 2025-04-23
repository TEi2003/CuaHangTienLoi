package Gui;

import javax.swing.*;
import java.awt.*;

public class InvoiceSystemGUI extends JFrame {
    private JTextField invoiceIdField;
    private JTextField dateField;
    private JLabel invoiceCodeLabel;
    private JLabel invoiceDateLabel;
    private JLabel totalAmountLabel;
    private JTable invoiceTable;
    private JTable invoiceDetailTable;

    public InvoiceSystemGUI() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Quản lý hóa đơn");
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
        searchPanel.add(new JLabel("Mã hóa đơn:"));
        invoiceIdField = new JTextField(10);
        searchPanel.add(invoiceIdField);
        searchPanel.add(new JLabel("Ngày:"));
        dateField = new JTextField(10);
        searchPanel.add(dateField);
        JButton searchButton = new JButton("Tìm");
        searchPanel.add(searchButton);

        // Invoice info panel
        JPanel invoiceInfoPanel = new JPanel(new GridLayout(3, 2));
        invoiceInfoPanel.add(new JLabel("Mã hóa đơn:"));
        invoiceCodeLabel = new JLabel("");
        invoiceInfoPanel.add(invoiceCodeLabel);
        invoiceInfoPanel.add(new JLabel("Ngày lập:"));
        invoiceDateLabel = new JLabel("");
        invoiceInfoPanel.add(invoiceDateLabel);
        invoiceInfoPanel.add(new JLabel("Tổng tiền:"));
        totalAmountLabel = new JLabel("");
        invoiceInfoPanel.add(totalAmountLabel);

        // Invoice table
        invoiceTable = new JTable(new Object[][]{}, new String[]{"Mã hóa đơn", "Ngày lập", "Tổng tiền"});
        JScrollPane invoiceScrollPane = new JScrollPane(invoiceTable);

        // Invoice detail table
        invoiceDetailTable = new JTable(new Object[][]{}, new String[]{"Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"});
        JScrollPane detailScrollPane = new JScrollPane(invoiceDetailTable);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(invoiceScrollPane, BorderLayout.CENTER);
        centerPanel.add(invoiceInfoPanel, BorderLayout.SOUTH);

        // Right panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JLabel("Chi tiết hóa đơn"), BorderLayout.NORTH);
        rightPanel.add(detailScrollPane, BorderLayout.CENTER);
        JButton printButton = new JButton("In hóa đơn");
        rightPanel.add(printButton, BorderLayout.SOUTH);

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, centerPanel, rightPanel);
        splitPane.setDividerLocation(450);
        add(splitPane, BorderLayout.CENTER);
    }
}