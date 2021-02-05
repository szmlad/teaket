package app;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import db.Data;
import model.Admin;
import model.Customer;
import model.Salesperson;
import model.User;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;

public class Auth {
    private static Data data;

    public static void dataStore(Data d) {
        data = d;
    }

    public static Object login(Request req, Response res) {
        Session sesh = req.session(true);

        String json = req.body();
        HashMap<String, String> loginData = new Gson().fromJson(json, new TypeToken<HashMap<String, String>>() {}.getType());

        String username = loginData.get("username");
        String password = loginData.get("password");

        Customer c = data.customers.getActive(username);
        Salesperson s = data.salespeople.getActive(username);
        Admin a = data.admins.getActive(username);
        if (c != null && c.getPassword().equals(password)) {
            sesh.attribute("user", c);
            return data.customers.withType(c);
        } else if (s != null && s.getPassword().equals(password)) {
            sesh.attribute("user", s);
            return data.salespeople.withType(s);
        } else if (a != null && a.getPassword().equals(password)) {
            sesh.attribute("user", a);
            return data.admins.withType(a);
        }

        res.status(400);
        return data.customers.singleToJson(null);
    }

    public static Object get(Request req, Response res) {
        Session sesh = req.session();
        User u = sesh.attribute("user");

        if (u == null) {
            res.status(400);
            return data.customers.singleToJson(null);
        }

        if (u.isCustomer()) return data.customers.withType((Customer) u);
        else if (u.isSalesperson()) return data.salespeople.withType((Salesperson) u);
        else return data.admins.withType((Admin) u);
    }

    public static Object logout(Request req, Response res) {
        Session sesh = req.session();
        sesh.invalidate();
        return data.customers.singleToJson(null);
    }
}
