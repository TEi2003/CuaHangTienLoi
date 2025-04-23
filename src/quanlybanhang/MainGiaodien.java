package quanlybanhang;

import javax.swing.*; 
import Gui.EmployeeManagementGUI;
import Gui.InvoiceSystemGUI;
import Gui.LoginGUI;
import Gui.MainGUI;
import Gui.PaymentSystemGUI;
import Gui.ProductManagementGUI;

public class MainGiaodien {
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
        panel.add(new JLabel("Đang tải giao diện..."));
        splashFrame.add(panel);
        splashFrame.setSize(300, 100);
        splashFrame.setLocationRelativeTo(null);
        splashFrame.setUndecorated(true);
        splashFrame.setVisible(true);

        // Đóng splash và hiển thị LoginGUI sau 1 giây
        new Thread(() -> {
            try {
                Thread.sleep(1000); // Hiển thị splash trong 1 giây
                SwingUtilities.invokeLater(() -> {
                    splashFrame.dispose();
                    new LoginGUI();
                });
            } catch (InterruptedException e) {
                System.err.println("Lỗi khi hiển thị splash: " + e.getMessage());
            }
        }).start();
    }
}