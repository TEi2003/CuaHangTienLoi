package Gui;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {
    public MainGUI() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setTitle("Hệ thống quản lý bán hàng");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu systemMenu = new JMenu("Hệ thống");
        JMenu managementMenu = new JMenu("Quản lý");
        JMenu reportMenu = new JMenu("Báo cáo");
        JMenu helpMenu = new JMenu("Trợ giúp");

        systemMenu.add(new JMenuItem("Đổi mật khẩu"));
        systemMenu.add(new JMenuItem("Đăng xuất"));
        systemMenu.add(new JMenuItem("Thoát"));

        managementMenu.add(new JMenuItem("Quản lý người dùng"));
        managementMenu.add(new JMenuItem("Quản lý nhân viên"));
        managementMenu.add(new JMenuItem("Quản lý sản phẩm"));
        managementMenu.add(new JMenuItem("Quản lý khách hàng"));
        managementMenu.add(new JMenuItem("Hóa đơn"));
        managementMenu.add(new JMenuItem("Thanh toán"));

        reportMenu.add(new JMenuItem("Báo cáo doanh thu"));
        helpMenu.add(new JMenuItem("Hướng dẫn sử dụng"));

        menuBar.add(systemMenu);
        menuBar.add(managementMenu);
        menuBar.add(reportMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // Toolbar
        JToolBar toolBar = new JToolBar();
        JButton productButton = new JButton("Sản phẩm");
        JButton employeeButton = new JButton("Nhân viên");
        JButton paymentButton = new JButton("Thanh toán");
        JButton invoiceButton = new JButton("Hóa đơn");
        JButton logoutButton = new JButton("Đăng xuất");
        toolBar.add(productButton);
        toolBar.add(employeeButton);
        toolBar.add(paymentButton);
        toolBar.add(invoiceButton);
        toolBar.add(logoutButton);
        add(toolBar, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Chào mừng đến với hệ thống quản lý bán hàng", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        centerPanel.add(welcomeLabel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Status bar
        JPanel statusBar = new JPanel(new BorderLayout());
        JLabel userLabel = new JLabel("Người dùng: ");
        JLabel timeLabel = new JLabel("Thời gian: ");
        statusBar.add(userLabel, BorderLayout.WEST);
        statusBar.add(timeLabel, BorderLayout.EAST);
        add(statusBar, BorderLayout.SOUTH);
    }
}