package app;

import db.Data;
import model.Salesperson;
import spark.Request;
import spark.Response;
import util.Func;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SalespersonREST {
    private static Data data;

    public static void dataSource(Data d) {
        data = d;
    }

    public static Object getSalespeople(Request req, Response res) {
        res.type("application/json");

        return data.salespeople.toJson(
                data.salespeople.active()
                        .collect(Collectors.toMap(Salesperson::getUsername, Function.identity()))
        );
    }

    public static Object getSalesperson(Request req, Response res) {
        res.type("application/json");

        String username = req.params("username");

        return data.salespeople.singleToJson(
                data.salespeople.active()
                        .filter(Func.equality(Salesperson::getUsername, username))
                        .findFirst()
                        .orElse(null)
        );
    }

    public static Object deleteSalesperson(Request req, Response res) {
        res.type("application/json");

        String username = req.params("username");

        Salesperson toDelete = data.salespeople.active()
                .filter(Func.equality(Salesperson::getUsername, username))
                .findFirst()
                .orElse(null);
        data.salespeople.delete(username);
        return data.salespeople.singleToJson(toDelete);
    }

    public static Object newSalesperson(Request req, Response res) {
        res.type("application/json");

        String json = req.body();
        Salesperson s = data.salespeople.newFromJson(json);

        if (data.getUser(s.getUsername()) != null) {
            return data.salespeople.singleToJson(null);
        }

        if (!s.getBirthDate().isBefore(LocalDate.now())) {
            return data.salespeople.singleToJson(null);
        }

        data.salespeople.put(s);
        return data.salespeople.singleToJson(s);
    }

    public static Object changeSalesperson(Request req, Response res) {
        return null;
    }
}
