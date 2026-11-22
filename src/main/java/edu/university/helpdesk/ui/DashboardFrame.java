
package edu.university.helpdesk.ui;

import edu.university.helpdesk.model.User;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private final User currentUser;

    public DashboardFrame(User user) {
        this.currentUser = user;

        setTitle("University IT Help Desk - Dashboard");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        );

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + currentUser.getUsername() + "!",
                SwingConstants.CENTER
        );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));

        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(
                new GridLayout(3, 1, 10, 15)
        );

        JButton createTicketButton =
                new JButton("Create Ticket");

        JButton viewTicketsButton =
                new JButton("View Tickets");

        JButton logoutButton =
                new JButton("Logout");

        buttonPanel.add(createTicketButton);
        buttonPanel.add(viewTicketsButton);
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        createTicketButton.addActionListener(e -> {
            new CreateTicketFrame(currentUser).setVisible(true);
            dispose();
        });

        viewTicketsButton.addActionListener(e -> {
            new ViewTicketsFrame(currentUser).setVisible(true);
            dispose();
        });

        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        add(panel);
    }
}