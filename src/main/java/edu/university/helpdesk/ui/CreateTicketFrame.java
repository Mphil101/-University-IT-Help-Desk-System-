package edu.university.helpdesk.ui;

import edu.university.helpdesk.model.Ticket;
import edu.university.helpdesk.model.User;
import edu.university.helpdesk.service.TicketService;

import javax.swing.*;
import java.awt.*;

public class CreateTicketFrame extends JFrame {

    private final User currentUser;
    private final TicketService ticketService = new TicketService();

    public CreateTicketFrame(User user) {
        this.currentUser = user;

        setTitle("University IT Help Desk - Create Ticket");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        JLabel heading = new JLabel(
                "Submit an IT Support Ticket",
                SwingConstants.CENTER
        );
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 12));

        JTextField titleField = new JTextField();

        JComboBox<String> categoryBox = new JComboBox<>(
                new String[]{"Hardware", "Software", "Network", "Other"}
        );

        JComboBox<String> priorityBox = new JComboBox<>(
                new String[]{"Low", "Medium", "High"}
        );

        JTextArea descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        form.add(new JLabel("Ticket Title:"));
        form.add(titleField);

        form.add(new JLabel("Category:"));
        form.add(categoryBox);

        form.add(new JLabel("Priority:"));
        form.add(priorityBox);

        form.add(new JLabel("Description:"));
        form.add(new JScrollPane(descriptionArea));

        panel.add(form, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton submitButton = new JButton("Submit Ticket");
        JButton backButton = new JButton("Back to Dashboard");

        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> {
            try {
                String title = titleField.getText().trim();
                String description = descriptionArea.getText().trim();

                String category =
                        (String) categoryBox.getSelectedItem();

                String priority =
                        (String) priorityBox.getSelectedItem();

                Ticket ticket = ticketService.createTicket(
                        currentUser.getId(),
                        title,
                        description,
                        category,
                        priority
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket submitted successfully!\n" +
                                "Ticket ID: " + ticket.getId()
                );

                new DashboardFrame(currentUser).setVisible(true);
                dispose();

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        backButton.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        });

        add(panel);
    }
}
