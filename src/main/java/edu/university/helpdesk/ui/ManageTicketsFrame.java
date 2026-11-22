package edu.university.helpdesk.ui;

import edu.university.helpdesk.model.Ticket;
import edu.university.helpdesk.model.User;
import edu.university.helpdesk.service.TicketService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageTicketsFrame extends JFrame {

    private final User currentUser;
    private final TicketService ticketService = new TicketService();
    private final DefaultTableModel tableModel;

    public ManageTicketsFrame(User user) {
        this.currentUser = user;

        setTitle("University IT Help Desk - Manage Tickets");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 15));
        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel heading = new JLabel(
                "Manage Student Tickets", SwingConstants.CENTER
        );
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(heading, BorderLayout.NORTH);

        String[] columns = {
                "Ticket ID", "Student ID", "Title",
                "Category", "Priority", "Status"
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

        panel.add(new JScrollPane(ticketTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JComboBox<String> statusBox = new JComboBox<>(
                new String[]{"Open", "In Progress", "Resolved"}
        );

        JButton updateButton = new JButton("Update Status");
        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back to Dashboard");

        buttonPanel.add(new JLabel("New Status:"));
        buttonPanel.add(statusBox);
        buttonPanel.add(updateButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            int selectedRow = ticketTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this, "Please select a ticket first."
                );
                return;
            }

            int modelRow = ticketTable.convertRowIndexToModel(
                    selectedRow
            );

            int ticketId = (int) tableModel.getValueAt(
                    modelRow, 0
            );

            String newStatus = (String) statusBox.getSelectedItem();

            try {
                boolean updated = ticketService.updateTicketStatus(
                        ticketId, newStatus
                );

                if (updated) {
                    JOptionPane.showMessageDialog(
                            this, "Ticket status updated successfully!"
                    );
                    loadTickets();
                } else {
                    JOptionPane.showMessageDialog(
                            this, "Ticket could not be found."
                    );
                }
            } catch (IllegalArgumentException |
                     IllegalStateException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Update Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        refreshButton.addActionListener(e -> loadTickets());

        backButton.addActionListener(e -> {
            new TechnicianDashboardFrame(currentUser).setVisible(true);
            dispose();
        });

        add(panel);
        loadTickets();
    }

    private void loadTickets() {
        try {
            tableModel.setRowCount(0);

            List<Ticket> tickets = ticketService.getAllTickets();

            for (Ticket ticket : tickets) {
                tableModel.addRow(new Object[]{
                        ticket.getId(),
                        ticket.getStudentId(),
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
