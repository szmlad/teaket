package model;

import java.util.ArrayList;
import java.util.List;

public class Customer implements DBObject {
    private UserCredentials credentials = new UserCredentials();
    private boolean deleted             = false;

    private List<String> tickets  = new ArrayList<>();
    private CustomerStatus status = CustomerStatus.defaultStatus;
    private int points            = 0;

    public Customer() { }

    public Customer(UserCredentials credentials, List<String> tickets,
                    CustomerStatus status, int points) {
        this.credentials = credentials;
        this.tickets     = tickets;
        this.status      = status;
        this.points      = points;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof Customer)) return false;
        Customer cs = (Customer) that;
        return credentials.equals(cs.credentials);
    }

    @Override
    public int hashCode() {
        return credentials.hashCode();
    }

    @Override
    public String toString() {
        return String.format(
                "Customer { cred: %s, deleted: %s, " +
                "tickets: [%s], status: %s, points: %d }",
                credentials, deleted, String.join(", ", tickets),
                status, points);
    }

    public String id() { return credentials.username(); }
    public void setId(String id) { credentials.setUsername(id); }

    public boolean deleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }

    public UserCredentials credentials() { return credentials; }
    public void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

    public List<String> tickets() { return tickets; }
    public void setTickets(List<String> tickets) { this.tickets = tickets; }

    public CustomerStatus status() { return status; }
    public void setStatus(CustomerStatus status) { this.status = status; }

    public int points() { return points; }
    public void setPoints(int points) { this.points = points; }
}
