package edu.university.helpdesk.service;

import edu.university.helpdesk.model.Ticket;
import java.util.List;

public class TicketService {

    public Ticket createTicket(
            int studentId,
            String title,
            String description,
            String category,
            String priority) {

        // Ticket creation logic will be implemented later.
        throw new UnsupportedOperationException(
                "Ticket creation not implemented yet"
        );
    }

    public List<Ticket> getTicketsByStudent(int studentId) {
        // Ticket retrieval logic will be implemented later.
        throw new UnsupportedOperationException(
                "Ticket retrieval not implemented yet"
        );
    }
}