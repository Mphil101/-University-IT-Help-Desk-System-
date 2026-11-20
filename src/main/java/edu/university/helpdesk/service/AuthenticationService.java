package edu.university.helpdesk.service;

import edu.university.helpdesk.model.User;

public class AuthenticationService {

    public User login(String username, String password) {

        if (username == null || password == null) {
            return null;
        }

        if (username.equals("student")
                && password.equals("student123")) {

            return new User(1, "student", "Student");
        }

        if (username.equals("technician")
                && password.equals("tech123")) {

            return new User(2, "technician", "IT Staff");
        }

        return null;
    }
}