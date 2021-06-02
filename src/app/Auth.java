package app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import db.DataStore;
import model.Admin;
import model.Customer;
import model.Salesperson;
import model.UserCredentials;
import spark.Request;
import spark.Response;
import spark.Session;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Auth {
    private static DataStore ds;
    private static Gson g = new Gson();
    private static Type t = TypeToken.getParameterized(
            HashMap.class, String.class, String.class).getType();

    public static void dataSource(DataStore store) {
        ds = store;
    }

    public static Object get(Request req, Response res) {
        Session sess = req.session();
        Object user = sess.attribute("user");

        if (user == null) {
            return g.toJson(null);
        }

        if (user instanceof Admin) {
            return ds.admins.serialize((Admin) user);
        } else if (user instanceof Customer) {
            return ds.customers.serialize((Customer) user);
        } else if (user instanceof Salesperson) {
            return ds.salespeople.serialize((Salesperson) user);
        } else {
            res.status(400);
            return g.toJson(null);
        }
    }

    public static Object login(Request req, Response res) {
        Session sess = req.session(true);

        String json = req.body();
        Map<String, String> loginData = g.fromJson(json, t);

        String username = loginData.get("username");
        String password = loginData.get("password");

        UserCredentials cred = ds.getActiveUser(username);
        if (cred == null) {
            res.status(401);
            return g.toJson(null);
        }

        if (cred.password().equals(password)) {
            Admin ad = ds.admins.getActive(username);
            if (ad != null) {
                sess.attribute("user", ad);
                return ds.admins.serialize(ad);
            }

            Customer cs = ds.customers.getActive(username);
            if (cs != null) {
                sess.attribute("user", cs);
                return ds.customers.serialize(cs);
            }

            Salesperson sp = ds.salespeople.getActive(username);
            if (sp != null) {
                sess.attribute("user", sp);
                return ds.salespeople.serialize(sp);
            }
        }

        res.status(400);
        return g.toJson(null);
    }

    public static Object logout(Request req, Response res) {
        Session sess = req.session();
        sess.invalidate();
        System.out.println("Successfully logged out!");
        return g.toJson(null);
    }
}
