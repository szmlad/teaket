package app;

import db.Data;
import model.Manifestation;
import spark.Request;
import spark.Response;

import java.util.function.Function;
import java.util.stream.Collectors;

public class ManifestationREST {
    private static Data data;

    public static void dataSource(Data d) {
        data = d;
    }

    public static Object getManifestations(Request req, Response res) {
        res.type("application/json");

        return data.manifestations.toJson(
                data.manifestations.active()
                        .collect(Collectors.toMap(Manifestation::getId, Function.identity()))
        );
    }

    public static Object getManifestation(Request req, Response res) {
        res.type("application/json");

        String id = req.params("id");

        return data.manifestations.singleToJson(data.manifestations.getActive(id));
    }

    public static Object deleteManifestation(Request req, Response res) {
        res.type("application/json");

        String id = req.params("id");

        Manifestation toDelete = data.manifestations.getActive(id);
        data.manifestations.delete(id);
        return data.manifestations.singleToJson(toDelete);
    }

    public static Object newManifestation(Request req, Response res) {
        res.type("application/json");

        String json = req.body();
        Manifestation m = data.manifestations.newFromJson(json);

        if (data.manifestations.getActive(m.getId()) != null) {
            return data.manifestations.singleToJson(null);
        }

        data.manifestations.put(m);
        return data.manifestations.singleToJson(m);
    }

    public static Object changeManifestation(Request req, Response res) {
        return null;
    }
}
