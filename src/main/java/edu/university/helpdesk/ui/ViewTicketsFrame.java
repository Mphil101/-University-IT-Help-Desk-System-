package edu.university.helpdesk.ui;

import edu.university.helpdesk.model.Ticket;
import edu.university.helpdesk.model.User;
import edu.university.helpdesk.service.TicketService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewTicketsFrame extends JFrame {

    private final User currentUser;
    private final TicketService ticketService = new TicketService();
    private final DefaultTableModel tableModel;

    public ViewTicketsFrame(User user) {
        this.currentUser = user;

        setTitle("University IT Help Desk - My Tickets");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel heading = new JLabel(
                "My Support Tickets",
                SwingConstants.CENTER
        );
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(heading, BorderLayout.NORTH);

        String[] columns = {
                "Ticket ID",
                "Title",
                "Category",
                "Priority",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable ticketTable = new JTable(tableModel);
        ticketTable.setRowHeight(25);
        ticketTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        panel.add(
                new JScrollPane(ticketTable),
                BorderLayout.CENTER
        );

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton refreshButton = new JButton("Refresh Tickets");
        JButton backButton = new JButton("Back to Dashboard");

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadTickets());

        backButton.addActionListener(e -> {
            new DashboardFrame(currentUser).setVisible(true);
            dispose();
        });

        add(panel);

        loadTickets();
    }

    private void loadTickets() {
        try {
            tableModel.setRowCount(0);

            List<Ticket> tickets =
                    ticketService.getTicketsByStudent(
                            currentUser.getId()
                    );

            for (Ticket ticket : tickets) {
                tableModel.addRow(new Object[]{
                        ticket.getId(),
                        ticket.getTitle(),
                        ticket.getCategory(),
                        ticket.getPriority(),
                        ticket.getStatus()
                });
            }

        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error Loading Tickets",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
