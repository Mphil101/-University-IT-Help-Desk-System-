package edu.university.helpdesk;

import edu.university.helpdesk.model.Ticket;
import edu.university.helpdesk.model.User;
import edu.university.helpdesk.repository.DatabaseManager;
import edu.university.helpdesk.service.AuthenticationService;
import edu.university.helpdesk.service.TicketService;

public class BackendTest {

    public static void main(String[] args) {

        DatabaseManager.initializeDatabase();

        AuthenticationService auth = new AuthenticationService();
        TicketService ticketService = new TicketService();

        // Test 1: Student login
        User student = auth.login("student", "student123");

        if (student == null) {
            throw new IllegalStateException("Student login failed");
        }

        System.out.println("Login successful: "
                + student.getUsername());

        // Test 2: Create ticket
        Ticket ticket = ticketService.createTicket(
                student.getId(),
                "WiFi Connection Issue",
                "Unable to connect to campus WiFi",
                "Network",
                "High"
        );

        System.out.println("Ticket created with ID: "
                + ticket.getId());

        // Test 3: Retrieve student tickets
        System.out.println("Student tickets:");

        for (Ticket t : ticketService.getTicketsByStudent(
                student.getId())) {

            System.out.println(
                    t.getId() + " - " + t.getTitle()
                            + " - " + t.getStatus()
            );
        }

        // Test 4: Update ticket status
        boolean updated = ticketService.updateTicketStatus(
                ticket.getId(), "In Progress");

        if (!updated) {
            throw new IllegalStateException("Status update failed");
        }

        // Test 5: Verify updated status
        Ticket savedTicket = ticketService.getTicketsByStudent(
                        student.getId()
                ).stream()
                .filter(t -> t.getId() == ticket.getId())
                .findFirst()
                .orElseThrow();

        if (!"In Progress".equals(savedTicket.getStatus())) {
            throw new IllegalStateException(
                    "Updated status was not saved");
        }

        System.out.println("Status updated successfully: "
                + savedTicket.getStatus());

        System.out.println("All backend tests passed!");
    }
}