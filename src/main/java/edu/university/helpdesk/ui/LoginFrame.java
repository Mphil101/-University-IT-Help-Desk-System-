package edu.university.helpdesk.ui;

import edu.university.helpdesk.model.User;
import edu.university.helpdesk.service.AuthenticationService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final AuthenticationService authService =
            new AuthenticationService();

    public LoginFrame() {
        setTitle("University IT Help Desk - Login");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        panel.add(new JLabel(""));
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            User user = authService.login(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(
                        this, "Login successful!"
                );

                if ("IT Staff".equalsIgnoreCase(user.getRole())) {
                    new TechnicianDashboardFrame(user).setVisible(true);
                } else if ("Student".equalsIgnoreCase(user.getRole())) {
                    new DashboardFrame(user).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Unsupported user role.",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        add(panel);
    }
}