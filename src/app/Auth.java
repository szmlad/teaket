package app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import db.DataStore;
import model.*;
import spark.Request;
import spark.Response;
import spark.Session;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Auth {
    private static DataStore ds;
    private static final Gson g = new Gson();
    private static final Type t = TypeToken.getParameterized(
            HashMap.class, String.class, String.class).getType();

    public static void dataSource(DataStore store) {
        ds = store;
    }

    public static Object get(Request req, Response res) {
        Session sess = req.session();
        User user = sess.attribute("user");

        if (user == null) {
            return g.toJson(null);
        }

        return ds.users.serialize(user);
    }

    public static Object login(Request req, Response res) {
        Session sess = req.session(true);

        String json = req.body();
        Map<String, String> loginData = g.fromJson(json, t);

        String username = loginData.get("username");
        String password = loginData.get("password");

        User user = ds.users.getActive(username);
        if (user == null) {
            res.status(400);
            return g.toJson(null);
        }

        if (user.password().equals(password)) {
            sess.attribute("user", user);
            return ds.users.serialize(user);
        }

        res.status(400);
        return g.toJson(null);
    }

    public static Object logout(Request req, Response res) {
        Session sess = req.session();
        sess.invalidate();
        return g.toJson(null);
    }
}
