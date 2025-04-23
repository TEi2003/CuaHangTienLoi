package Gui;

import javax.swing.*;
import java.awt.*;

public class EmployeeManagementGUI extends JFrame {
    private JTextField employeeIdField;
    private JTextField employeeNameField;
    private JTextField birthDateField;
    private JTextField phoneField;
    private JTextField positionField;
    private JTextField salaryField;
    private JComboBox<String> genderComboBox;
    private JTable employeeTable;

    public EmployeeManagementGUI() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Quản lý nhân viên");
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
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.add(new JLabel("Mã nhân viên:"));
        employeeIdField = new JTextField();
        formPanel.add(employeeIdField);
        formPanel.add(new JLabel("Tên nhân viên:"));
        employeeNameField = new JTextField();
        formPanel.add(employeeNameField);
        formPanel.add(new JLabel("Ngày sinh:"));
        birthDateField = new JTextField();
        formPanel.add(birthDateField);
        formPanel.add(new JLabel("Số điện thoại:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Chức vụ:"));
        positionField = new JTextField();
        formPanel.add(positionField);
        formPanel.add(new JLabel("Mức lương:"));
        salaryField = new JTextField();
        formPanel.add(salaryField);
        formPanel.add(new JLabel("Giới tính:"));
        genderComboBox = new JComboBox<>(new String[]{"Nam", "Nữ"});
        formPanel.add(genderComboBox);

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
        employeeTable = new JTable(new Object[][]{}, new String[]{"Mã NV", "Tên NV", "Ngày sinh", "Điện thoại", "Chức vụ", "Lương", "Giới tính"});
        JScrollPane scrollPane = new JScrollPane(employeeTable);

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