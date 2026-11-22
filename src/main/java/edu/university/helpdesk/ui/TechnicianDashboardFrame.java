
package edu.university.helpdesk.ui;

import edu.university.helpdesk.model.User;
import javax.swing.*;
import java.awt.*;

public class TechnicianDashboardFrame extends JFrame {

    private final User currentUser;

    public TechnicianDashboardFrame(User user) {
        this.currentUser = user;

        setTitle("University IT Help Desk - Technician");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 15));
        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        );

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + currentUser.getUsername() + "!",
                SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton viewTicketsButton = new JButton("Manage Tickets");
        JButton logoutButton = new JButton("Logout");

        panel.add(welcomeLabel);
        panel.add(viewTicketsButton);
        panel.add(logoutButton);

        viewTicketsButton.addActionListener(e -> {
            new ManageTicketsFrame(currentUser).setVisible(true);
            dispose();
        });

        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        add(panel);
    }
}
