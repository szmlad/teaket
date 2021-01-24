package db;

import model.User;
import util.Func;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Stream;

public class Data {
    public AdminDataStore admins;
    public CommentDataStore comments;
    public CustomerDataStore customers;
    public ManifestationDataStore manifestations;
    public SalespersonDataStore salespeople;
    public TicketDataStore tickets;

    public Data() {
        admins = new AdminDataStore("data/admins.json");
        comments = new CommentDataStore("data/comments.json");
        customers = new CustomerDataStore("data/customers.json");
        manifestations = new ManifestationDataStore("data/manifestations.json");
        salespeople = new SalespersonDataStore("data/salespeople.json");
        tickets = new TicketDataStore("data/tickets.json");
    }

    public void read() throws IOException {
        Path dataPath = Paths.get("data");
        if (!Files.exists(dataPath)) Files.createDirectory(dataPath);
        admins.fromJson();
        comments.fromJson();
        customers.fromJson();
        manifestations.fromJson();
        salespeople.fromJson();
        tickets.fromJson();
    }

    public void write() throws IOException {
        admins.toJson();
        comments.toJson();
        customers.toJson();
        manifestations.toJson();
        salespeople.toJson();
        tickets.toJson();
    }

    public Stream<User> users() {
        return Stream.of(admins.values(), customers.values(), salespeople.values()).flatMap(Function.identity());
    }

    public Stream<User> activeUsers() {
        return this.users().filter(Func.not(User::getDeleted));
    }

    public User getUser(String username) {
        User u = admins.get(username);
        if (u == null) u = customers.get(username);
        if (u == null) u = salespeople.get(username);
        return u;
    }

    public User getActiveUser(String username) {
        User u = admins.getActive(username);
        if (u == null) u = customers.getActive(username);
        if (u == null) u = salespeople.getActive(username);
        return u;
    }

    public void deleteUser(String username) {
        User u = getUser(username);
        if (u == null) return;
        u.setDeleted(true);
    }
}
