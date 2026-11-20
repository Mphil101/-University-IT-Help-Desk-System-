package edu.university.helpdesk.repository;

import edu.university.helpdesk.model.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository {

    // Save a new support ticket
    public int saveTicket(Ticket ticket) throws SQLException {
        String sql = """
                INSERT INTO tickets
                (student_id, title, description, category, priority, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ticket.getStudentId());
            stmt.setString(2, ticket.getTitle());
            stmt.setString(3, ticket.getDescription());
            stmt.setString(4, ticket.getCategory());
            stmt.setString(5, ticket.getPriority());
            stmt.setString(6, ticket.getStatus());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
            throw new SQLException("Ticket ID was not generated.");
        }
    }

    // Retrieve all support tickets
    public List<Ticket> getAllTickets() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();

        String sql = "SELECT * FROM tickets ORDER BY id DESC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tickets.add(mapTicket(rs));
            }
        }

        return tickets;
    }

    // Find a ticket by its ID
    public Ticket getTicketById(int id) throws SQLException {
        String sql = "SELECT * FROM tickets WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapTicket(rs);
                }
            }
        }

        return null;
    }

    // Update a ticket's status
    public boolean updateTicketStatus(int id, String status)
            throws SQLException {

        String sql = "UPDATE tickets SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;
        }
    }

    // Convert a database row into a Ticket object
    private Ticket mapTicket(ResultSet rs) throws SQLException {
        return new Ticket(
                rs.getInt("id"),
                rs.getInt("student_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getString("priority"),
                rs.getString("status")
        );
    }
}
