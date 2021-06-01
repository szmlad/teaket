package db;

import model.*;

import java.util.function.Function;
import java.util.stream.Stream;

public class DataStore {
    public DB<Admin> admins            = new DB<>("data/admins.json");
    public DB<Comment> comments        = new DB<>("data/comments.json");
    public DB<Customer> customers      = new DB<>("data/customers.json");
    public DB<Event> events            = new DB<>("data/events.json");
    public DB<Salesperson> salespeople = new DB<>("data/salespeople.json");
    public DB<Ticket> tickets          = new DB<>("data/tickets.json");

    public DataStore() { }

    public void init() {
        admins.deserializeAll(Admin.class);
        comments.deserializeAll(Comment.class);
        customers.deserializeAll(Customer.class);
        events.deserializeAll(Event.class);
        salespeople.deserializeAll(Salesperson.class);
        tickets.deserializeAll(Ticket.class);
    }

    public void commit() {
        admins.serializeAll(Admin.class);
        comments.serializeAll(Comment.class);
        customers.serializeAll(Customer.class);
        events.serializeAll(Event.class);
        salespeople.serializeAll(Salesperson.class);
        tickets.serializeAll(Ticket.class);
    }

    public Stream<UserCredentials> allUsers() {
        return Stream.of(
                admins.all().map(Admin::credentials),
                customers.all().map(Customer::credentials),
                salespeople.all().map(Salesperson::credentials))
                .flatMap(Function.identity());
    }

    public Stream<UserCredentials> allActiveUsers() {
        return Stream.of(
                admins.allActive().map(Admin::credentials),
                customers.allActive().map(Customer::credentials),
                salespeople.allActive().map(Salesperson::credentials))
                .flatMap(Function.identity());
    }

    public UserCredentials getUser(String username) {
        Admin ad = admins.get(username);
        if (ad != null) return ad.credentials();

        Customer cs = customers.get(username);
        if (cs != null) return cs.credentials();

        Salesperson sp = salespeople.get(username);
        if (sp != null) return sp.credentials();

        return null;
    }

    public UserCredentials getActiveUser(String username) {
        Admin ad = admins.getActive(username);
        if (ad != null) return ad.credentials();

        Customer cs = customers.getActive(username);
        if (cs != null) return cs.credentials();

        Salesperson sp = salespeople.getActive(username);
        if (sp != null) return sp.credentials();

        return null;
    }
}
