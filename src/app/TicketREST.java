package app;

import db.Data;
import model.Ticket;
import spark.Request;
import spark.Response;

import java.util.function.Function;
import java.util.stream.Collectors;

public class TicketREST {
    private static Data data;

    public static void dataSource(Data d) {
        data = d;
    }

    public static Object getTickets(Request req, Response res) {
        res.type("application/json");

        return data.tickets.toJson(
                data.tickets.active()
                        .collect(Collectors.toMap(Ticket::getId, Function.identity()))
        );
    }

    public static Object getTicket(Request req, Response res) {
        res.type("application/json");

        String id = req.params("id");

        return data.tickets.singleToJson(data.tickets.getActive(id));
    }

    public static Object deleteTicket(Request req, Response res) {
        res.type("application/json");

        String id = req.params("id");

        Ticket toDelete = data.tickets.getActive(id);
        data.tickets.delete(id);
        return data.tickets.singleToJson(toDelete);
    }

    public static Object newTicket(Request req, Response res) {
        res.type("application/json");

        String json = req.body();
        Ticket t = data.tickets.newFromJson(json);

        if (data.tickets.getActive(t.getId()) != null) {
            return data.tickets.singleToJson(null);
        }

        data.tickets.put(t);
        return data.tickets.singleToJson(t);
    }

    public static Object changeTicket(Request req, Response res) {
        return null;
    }
}
