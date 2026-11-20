package edu.university.helpdesk.ui;

import javax.swing.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("University IT Help Desk - Login");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel(
                "University IT Help Desk - Login",
                SwingConstants.CENTER
        );

        add(label);
    }
}