package db;

import model.*;

public class DataStore {
    public DB<Comment> comments = new DB<>("data/comments.json");
    public DB<Event> events     = new DB<>("data/events.json");
    public DB<Ticket> tickets   = new DB<>("data/tickets.json");
    public UserDB users         = new UserDB("data/users.json");

    public DataStore() { }

    public void init() {
        comments.deserializeAll(Comment.class);
        events.deserializeAll(Event.class);
        tickets.deserializeAll(Ticket.class);
        users.deserializeAll(User.class);
    }

    public void commit() {
        comments.serializeAll(Comment.class);
        events.serializeAll(Event.class);
        tickets.serializeAll(Ticket.class);
        users.serializeAll(User.class);
    }
}
