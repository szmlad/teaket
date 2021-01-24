package app;

import db.Data;
import model.Salesperson;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SalespersonREST {
    private static Data data;

    public static void dataSource(Data d) {
        data = d;
    }

    public static Object getSalespeople(Request req, Response res) {
        res.type("application/json");

        Map<String, Salesperson> allSalespeople = data.salespeople.active()
                .collect(Collectors.toMap(Salesperson::getUsername, Function.identity()));
        return data.salespeople.toJson(allSalespeople);
    }

    public static Object getSalesperson(Request req, Response res) {
        res.type("application/json");

        String username = req.params("username");

        return data.salespeople.singleToJson(data.salespeople.getActive(username));
    }

    public static Object deleteSalesperson(Request req, Response res) throws IOException {
        res.type("application/json");

        String username = req.params("username");

        Salesperson toDelete = data.salespeople.getActive(username);
        data.salespeople.delete(username);
        data.salespeople.toJson();
        return data.salespeople.singleToJson(toDelete);
    }

    public static Object newSalesperson(Request req, Response res) throws IOException {
        res.type("application/json");

        String json = req.body();
        Salesperson s = data.salespeople.newFromJson(json);

        if (data.getActiveUser(s.getUsername()) != null) {
            return data.salespeople.singleToJson(null);
        }

        if (!s.getBirthDate().isBefore(LocalDate.now())) {
            return data.salespeople.singleToJson(null);
        }

        data.salespeople.put(s);
        data.salespeople.toJson();
        return data.salespeople.singleToJson(s);
    }

    public static Object changeSalesperson(Request req, Response res) {
        return null;
    }
}
