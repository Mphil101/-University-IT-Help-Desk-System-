package edu.university.helpdesk.model;

public class Ticket {
    private int id;
    private int studentId;
    private String title;
    private String description;
    private String category;
    private String priority;
    private String status;

    public Ticket(int id, int studentId, String title,
                  String description, String category,
                  String priority, String status) {
        this.id = id;
        this.studentId = studentId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }
}