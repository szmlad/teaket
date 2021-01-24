package app;

import db.Data;
import model.Customer;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CustomerREST {
    private static Data data;

    public static void dataSource(Data d) {
        data = d;
    }

    public static Object getCustomers(Request req, Response res) {
        res.type("application/json");

        Map<String, Customer> allCustomers = data.customers.active()
                .collect(Collectors.toMap(Customer::getUsername, Function.identity()));
        return data.customers.toJson(allCustomers);
    }

    public static Object getCustomer(Request req, Response res) {
        res.type("application/json");

        String username = req.params("username");

        return data.customers.singleToJson(data.customers.getActive(username));
    }

    public static Object deleteCustomer(Request req, Response res) throws IOException {
        res.type("application/json");

        String username = req.params("username");

        Customer toDelete = data.customers.getActive(username);
        data.customers.delete(username);
        data.customers.toJson();
        return data.customers.singleToJson(toDelete);
    }

    public static Object newCustomer(Request req, Response res) throws IOException {
        res.type("application/json");

        String json = req.body();
        Customer c = data.customers.newFromJson(json);

        if (data.getActiveUser(c.getUsername()) != null) {
            return data.customers.singleToJson(null);
        }

        if (!c.getBirthDate().isBefore(LocalDate.now())) {
            return data.customers.singleToJson(null);
        }

        data.customers.put(c);
        data.customers.toJson();
        return data.customers.singleToJson(c);
    }

    public static Object changeCustomer(Request req, Response res) {
        return null;
    }
}
