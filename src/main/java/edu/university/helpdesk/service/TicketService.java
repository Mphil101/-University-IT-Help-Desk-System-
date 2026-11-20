package edu.university.helpdesk.service;

import edu.university.helpdesk.model.Ticket;
import edu.university.helpdesk.repository.TicketRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TicketService {

    private final TicketRepository ticketRepository =
            new TicketRepository();

    // Create a new IT support ticket
    public Ticket createTicket(
            int studentId,
            String title,
            String description,
            String category,
            String priority) {

        if (studentId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid student ID");
        }

        if (title == null || title.isBlank()
                || description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "Title and description are required");
        }

        Ticket ticket = new Ticket(
                0,
                studentId,
                title.trim(),
                description.trim(),
                category,
                priority,
                "Open"
        );

        try {
            int ticketId = ticketRepository.saveTicket(ticket);

            return new Ticket(
                    ticketId,
                    studentId,
                    title.trim(),
                    description.trim(),
                    category,
                    priority,
                    "Open"
            );

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to create ticket", e);
        }
    }

    // Retrieve tickets belonging to a student
    public List<Ticket> getTicketsByStudent(int studentId) {

        try {
            return ticketRepository.getAllTickets()
                    .stream()
                    .filter(ticket ->
                            ticket.getStudentId() == studentId)
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to retrieve tickets", e);
        }
    }

    // Retrieve all tickets for IT staff
    public List<Ticket> getAllTickets() {

        try {
            return ticketRepository.getAllTickets();

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to retrieve tickets", e);
        }
    }

    // Update the status of an existing ticket
    public boolean updateTicketStatus(int ticketId, String status) {

        if (!"Open".equals(status)
                && !"In Progress".equals(status)
                && !"Resolved".equals(status)) {
            throw new IllegalArgumentException(
                    "Invalid ticket status");
        }

        try {
            return ticketRepository.updateTicketStatus(
                    ticketId, status);

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Failed to update ticket status", e);
        }
    }
}
