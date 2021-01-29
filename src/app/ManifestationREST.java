package app;

import db.Data;
import model.Manifestation;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ManifestationREST {
    private static Data data;

    public static void dataSource(Data d) {
        data = d;
    }

    public static Object getManifestations(Request req, Response res) {
        res.type("application/json");

        Map<String, Manifestation> allManifestations = data.manifestations.active()
                .collect(Collectors.toMap(Manifestation::getId, Function.identity()));
        return data.manifestations.toJson(allManifestations);
    }

    public static Object getManifestation(Request req, Response res) {
        res.type("application/json");

        String id = req.params("id");

        return data.manifestations.singleToJson(data.manifestations.getActive(id));
    }

    public static Object deleteManifestation(Request req, Response res) throws IOException {
        res.type("application/json");

        String id = req.params("id");

        Manifestation toDelete = data.manifestations.getActive(id);
        data.manifestations.delete(id);
        data.manifestations.toJson();
        return data.manifestations.singleToJson(toDelete);
    }

    public static Object newManifestation(Request req, Response res) throws IOException {
        res.type("application/json");

        String json = req.body();
        Manifestation m = data.manifestations.newFromJson(json);

        int status = validate(m);
        res.status(status);
        if (status != 200) return data.manifestations.singleToJson(null);

        data.manifestations.put(m);
        data.manifestations.toJson();
        return data.manifestations.singleToJson(m);
    }

    public static Object changeManifestation(Request req, Response res) {
        return null;
    }

    private static int validate(Manifestation m) {
        if (data.manifestations.getActive(m.getId()) != null) return 409;
        if (m.getSeatCount() <= 0) return 400;
        if (m.getTicketPrice() < 0) return 400;
        if (m.getName().isEmpty()) return 400;
        return 200;
    }
}
