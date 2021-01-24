package app;

import db.Data;
import model.Admin;
import spark.Request;
import spark.Response;
import util.Func;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AdminREST {
    private static Data data;

    public static void dataSource(Data d) {
        data = d;
    }

    public static Object getAdmins(Request req, Response res) {
        res.type("application/json");

        return data.admins.toJson(
                data.admins.active()
                        .collect(Collectors.toMap(Admin::getUsername, Function.identity()))
        );
    }

    public static Object getAdmin(Request req, Response res) {
        res.type("application/json");

        String username = req.params("username");

        return data.admins.singleToJson(
                data.admins.active()
                        .filter(Func.equality(Admin::getUsername, username))
                        .findFirst()
                        .orElse(null)
        );
    }

    public static Object deleteAdmin(Request req, Response res) {
        res.type("application/json");

        String username = req.params("username");

        Admin toDelete = data.admins.active()
                .filter(Func.equality(Admin::getUsername, username))
                .findFirst()
                .orElse(null);
        data.admins.delete(username);
        return data.admins.singleToJson(toDelete);
    }

    public static Object newAdmin(Request req, Response res) {
        res.type("application/json");

        String json = req.body();
        Admin a = data.admins.newFromJson(json);

        if (data.getUser(a.getUsername()) != null) {
            return data.admins.singleToJson(null);
        }

        if (!a.getBirthDate().isBefore(LocalDate.now())) {
            return data.admins.singleToJson(null);
        }

        data.admins.put(a);
        return data.admins.singleToJson(a);
    }

    public static Object changeAdmin(Request req, Response res) {
        return null;
    }
}
