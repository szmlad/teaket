package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private List<String> tickets;
    private CustomerStatus status;
    private int points;

    public Customer() {
        super();
        this.tickets = new ArrayList<>();
        this.status = CustomerStatus.getDefault();
        this.points = 0;
    }

    public Customer(String username, String password, String firstName, String lastName, Gender gender, LocalDate birthDate, List<String> tickets, CustomerStatus status, int points) {
        super(username, password, firstName, lastName, gender, birthDate);
        this.tickets = tickets;
        this.status = status;
        this.points = points;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

    public CustomerStatus getCustomerStatus() {
        return status;
    }

    public void setCustomerStatus(CustomerStatus status) {
        this.status = status;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
