package quanlybanhang;

import javax.swing.*;
import Gui.LoginGUI;
import java.sql.Connection;
import java.sql.SQLException;

    public class Main {
        public static void main(String[] args) {
            // Thiết lập look and feel của hệ thống
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Lỗi thiết lập look and feel: " + e.getMessage());
            }

            // Hiển thị màn hình splash
            JFrame splashFrame = new JFrame("Đang khởi động...");
            JPanel panel = new JPanel();
            panel.add(new JLabel("Đang kết nối đến cơ sở dữ liệu..."));
            splashFrame.add(panel);
            splashFrame.setSize(300, 100);
            splashFrame.setLocationRelativeTo(null);
            splashFrame.setUndecorated(true);
            splashFrame.setVisible(true);

            // Kiểm tra kết nối cơ sở dữ liệu
            SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    try (Connection conn = DatabaseConnection.getConnection()) {
                        return conn != null && !conn.isClosed();
                    } catch (SQLException e) {
                        System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
                        return false;
                    }
                }

                @Override
                protected void done() {
                    try {
                        boolean connected = get();
                        splashFrame.dispose(); // Đóng splash khi hoàn thành
                        if (connected) {
                            // Hiển thị màn hình đăng nhập
                            SwingUtilities.invokeLater(() -> new LoginGUI());
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Không thể kết nối đến cơ sở dữ liệu.\nVui lòng kiểm tra cấu hình MySQL (URL, username, password).",
                                    "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
                            System.exit(1);
                        }
                    } catch (Exception e) {
                        System.err.println("Lỗi trong quá trình kiểm tra kết nối: " + e.getMessage());
                        JOptionPane.showMessageDialog(null,
                                "Đã xảy ra lỗi không xác định khi kết nối cơ sở dữ liệu.",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                }
            };

            worker.execute();
        }
    }