package Gui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginGUI() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Đăng nhập");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(23, 107, 135));
        JLabel headerLabel = new JLabel("Đăng nhập hệ thống");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.add(new JLabel("Tên đăng nhập:"));
        usernameField = new JTextField(20);
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Mật khẩu:"));
        passwordField = new JPasswordField(20);
        inputPanel.add(passwordField);
        add(inputPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("Đăng nhập");
        JButton exitButton = new JButton("Thoát");
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện đăng nhập
        loginButton.addActionListener(e -> login());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        try (Connection conn = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=quanlybanhang;encrypt=false", 
                "sa", 
                "your_password")) {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM Users WHERE Username = ? AND Password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("Role");
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", 
                                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                if ("ADMIN".equals(role)) {
                    new MainGUI();
                } else {
                    new PaymentSystemGUI();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng", 
                                            "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu: " + e.getMessage(), 
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}