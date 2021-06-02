package model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private List<String> tickets  = new ArrayList<>();
    private CustomerStatus status = CustomerStatus.defaultStatus;
    private int points            = 0;

    public Customer() {
        super();
        this.type = UserType.CUSTOMER;
    }

    public Customer(String username, String password, String firstName,
                    String lastName, Gender gender, long birthdate,
                    List<String> tickets, CustomerStatus status, int points) {
        super(username, password, firstName,
                lastName, gender, birthdate, UserType.CUSTOMER);
        this.tickets = tickets;
        this.status  = status;
        this.points  = points;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer { username: %s, password: %s, firstName: %s, " +
                "lastName: %s, gender: %s, birthdate: %s, tickets: [%s], " +
                "status: %s, points: %s }",
                username, password, firstName, lastName, gender,
                birthdate, String.join(", ", tickets), status, points);
    }
}