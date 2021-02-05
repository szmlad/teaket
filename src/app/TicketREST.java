package app;

import db.Data;
import model.Ticket;
import spark.Request;
import spark.Response;
import util.Func;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TicketREST {
    private static Data data;

    public static void dataSource(Data d) {
        data = d;
    }

    public static Object getTickets(Request req, Response res) {
        res.type("application/json");

        String username = req.queryParams("username");

        Map<String, Ticket> allTickets = data.tickets.active()
                .filter(Func.equality(Ticket::getBuyer, username))
                .collect(Collectors.toMap(Ticket::getId, Function.identity()));
        return data.tickets.toJson(allTickets);
    }

    public static Object getTicket(Request req, Response res) {
        res.type("application/json");

        String id = req.params("id");

        return data.tickets.singleToJson(data.tickets.getActive(id));
    }

    public static Object deleteTicket(Request req, Response res) throws IOException {
        res.type("application/json");

        String id = req.params("id");

        Ticket toDelete = data.tickets.getActive(id);
        data.tickets.delete(id);
        data.tickets.toJson();
        return data.tickets.singleToJson(toDelete);
    }

    public static Object newTicket(Request req, Response res) throws IOException {
        res.type("application/json");

        String json = req.body();
        Ticket t = data.tickets.newFromJson(json);

        int status = validate(t);
        res.status(200);
        if (status != 200) return data.tickets.singleToJson(null);

        data.tickets.put(t);
        data.tickets.toJson();
        return data.tickets.singleToJson(t);
    }

    public static Object changeTicket(Request req, Response res) {
        return null;
    }

    private static int validate(Ticket t) {
        if (data.tickets.getActive(t.getId()) != null) return 409;
        if (data.customers.getActive(t.getBuyer()) == null) return 400;
        if (data.manifestations.getActive(t.getManifestationId()) == null) return 400;
        return 200;
    }
}
