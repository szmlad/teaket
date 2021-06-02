package model;

import java.util.ArrayList;
import java.util.List;

public class Salesperson extends User {
    private List<String> events = new ArrayList<>();

    public Salesperson() {
        super();
        this.type = UserType.SALESPERSON;
    }

    public Salesperson(String username, String password, String firstName,
                       String lastName, Gender gender, long birthdate,
                       List<String> events) {
        super(username, password, firstName,
                lastName, gender, birthdate, UserType.SALESPERSON);
        this.events = events;
    }

    @Override
    public String toString() {
        return String.format(
                "Salesperson { username: %s, password: %s, firstName: %s, " +
                "lastName: %s, gender: %s, birthdate: %s, events: [%s] }",
                username, password, firstName, lastName, gender,
                birthdate, String.join(", ", events));
    }
}