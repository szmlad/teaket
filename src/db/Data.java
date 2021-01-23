package db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Data {
    public AdminDataStore admins;
    public CommentDataStore comments;
    public CustomerDataStore customers;
    public SalespersonDataStore salespeople;
    public TicketDataStore tickets;

    public Data() {
        admins = new AdminDataStore("data/admins.json");
        comments = new CommentDataStore("data/comments.json");
        customers = new CustomerDataStore("data/customers.json");
        salespeople = new SalespersonDataStore("data/salespeople.json");
        tickets = new TicketDataStore("data/tickets.json");
    }

    public void read() throws IOException {
        Path dataPath = Paths.get("data");
        if (!Files.exists(dataPath)) Files.createDirectory(dataPath);
        admins.fromJson();
        comments.fromJson();
        customers.fromJson();
        salespeople.fromJson();
        tickets.fromJson();
    }

    public void write() throws IOException {
        admins.toJson();
        comments.toJson();
        customers.toJson();
        salespeople.toJson();
        tickets.toJson();
    }
}
