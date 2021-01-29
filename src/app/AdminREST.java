package app;

import db.Data;
import model.Admin;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AdminREST {
    private static Data data;

    public static void dataSource(Data d) {
        data = d;
    }

    public static Object getAdmins(Request req, Response res) {
        res.type("application/json");

        Map<String, Admin> allAdmins = data.admins.active()
                .collect(Collectors.toMap(Admin::getUsername, Function.identity()));
        return data.admins.toJson(allAdmins);
    }

    public static Object getAdmin(Request req, Response res) {
        res.type("application/json");

        String username = req.params("username");

        return data.admins.singleToJson(data.admins.getActive(username));
    }

    public static Object deleteAdmin(Request req, Response res) throws IOException {
        res.type("application/json");

        String username = req.params("username");

        Admin toDelete = data.admins.getActive(username);
        data.admins.delete(username);
        data.admins.toJson();
        return data.admins.singleToJson(toDelete);
    }

    public static Object newAdmin(Request req, Response res) throws IOException {
        res.type("application/json");

        String json = req.body();
        Admin a = data.admins.newFromJson(json);

        int status = validate(a);
        res.status(status);
        if (status != 200) return data.admins.singleToJson(null);

        data.admins.put(a);
        data.admins.toJson();
        return data.admins.singleToJson(a);
    }

    public static Object changeAdmin(Request req, Response res) {
        return null;
    }

    private static int validate(Admin a) {
        if (data.getActiveUser(a.getUsername()) != null) return 409;
        if (a.getUsername().isEmpty()) return 400;
        if (a.getPassword().isEmpty()) return 400;
        if (a.getPassword().length() < Admin.MIN_PASSWORD_LENGTH) return 400;
        if (!a.getBirthDate().isBefore(LocalDate.now())) return 400;
        return 200;
    }
}
