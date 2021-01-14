package model;

import java.time.LocalDate;
import java.util.List;

public class Customer extends User {
    private List<String> tickets;
    private CustomerStatus status;
    private int points;

    public Customer() {
        super();
    }

    public Customer(boolean deleted, String username, String password, String firstName, String lastName, Gender gender, LocalDate birthDate, List<String> tickets, CustomerStatus status, int points) {
        super(deleted, username, password, firstName, lastName, gender, birthDate);
        this.tickets = tickets;
        this.status = status;
        this.points = points;
    }
}
